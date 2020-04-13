package com.xiaohe66.common.table.parser;

import com.xiaohe66.common.table.entity.ParserContext;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel 2007 大表解析器
 * <p>
 * todo : 需要优化本类
 *
 * <P>本类关键代码取自 https://www.cnblogs.com/cxhfuujust/p/11125094.html
 *
 * @author xiaohe
 * @time 2020.04.13 09:53
 */
public class BigExcelParser extends DefaultHandler implements TableParser {

    enum CellDataType {
        /**
         * 单元格中的数据可能的数据类型
         */
        BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER, DATE, NULL
    }

    /**
     * 共享字符串表
     */
    private SharedStringsTable sharedStringsTable;

    /**
     * 上一次的索引值
     */
    private String lastIndexOrValue;

    /**
     * 工作表索引
     */
    private int sheetIndex = 0;

    /**
     * 一行内cell集合
     */
    private List<Object> cellList = new ArrayList<>();

    /**
     * 判断整行是否为空行的标记
     */
    private boolean flag = false;

    /**
     * 当前列
     */
    private int curCol = 0;

    /**
     * T元素标识
     */
    private boolean isTElement;

    /**
     * 单元格数据类型，默认为字符串类型
     */
    private CellDataType nextDataType = CellDataType.SSTINDEX;

    private final DataFormatter formatter = new DataFormatter();

    /**
     * 单元格日期格式的索引
     */
    private short formatIndex;

    /**
     * 日期格式字符串
     */
    private String formatString;

    /**
     * 定义前一个元素和当前元素的位置，用来计算其中空的单元格数量，如A6和A8等
     */
    private String preRef = null;
    private String ref = null;

    /**
     * 定义该文档一行最大的单元格数，用来补全一行最后可能缺失的单元格
     */
    private String maxRef = null;

    /**
     * 单元格
     */
    private StylesTable stylesTable;

    private File file;

    private TableParseCallback callback;

    private ParserContext context = new ParserContext();

    @Override
    public void parse(File file, TableParseCallback callback) {
        this.file = file;
        this.callback = callback;

        try {
            process();
        } catch (Exception e) {
            throw new TableParserException(e);
        }
    }

    /**
     * 遍历工作簿中所有的电子表格
     * 并缓存在mySheetList中
     */
    private void process() {
        OPCPackage pkg = null;
        try {
            pkg = OPCPackage.open(file);
            XSSFReader xssfReader = new XSSFReader(pkg);
            stylesTable = xssfReader.getStylesTable();

            XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
            this.sharedStringsTable = xssfReader.getSharedStringsTable();
            parser.setContentHandler(this);

            XSSFReader.SheetIterator sheets = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
            while (sheets.hasNext()) {
                sheetIndex++;

                //sheets.next()和sheets.getSheetName()不能换位置，否则sheetName报错
                try (InputStream sheet = sheets.next()) {
                    context.setSheetName(sheets.getSheetName());
                    InputSource sheetSource = new InputSource(sheet);

                    //解析excel的每条记录，在这个过程中startElement()、characters()、endElement()这三个函数会依次执行
                    parser.parse(sheetSource);

                    // 回调 end方法
                    callback.onEnd(context);
                }
            }
        } catch (IOException | SAXException | OpenXML4JException e) {
            throw new TableParserException(e);
        }
    }


    /**
     * 第一个执行
     */
    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        //c => 单元格
        if ("c".equals(name)) {
            //前一个单元格的位置
            if (preRef == null) {
                preRef = attributes.getValue("r");
            } else {
                preRef = ref;
            }

            //当前单元格的位置
            ref = attributes.getValue("r");
            //设定单元格类型
            this.setNextDataType(attributes);
        }

        isTElement = "t".equals(name);

        //置空
        lastIndexOrValue = "";
    }

    /**
     * 第二个执行
     * 得到单元格对应的索引值或是内容值
     * 如果单元格类型是字符串、INLINESTR、数字、日期，lastIndex则是索引值
     * 如果单元格类型是布尔值、错误、公式，lastIndex则是内容值
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        lastIndexOrValue += new String(ch, start, length);
    }

    /**
     * 第三个执行
     */
    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {

        //t元素也包含字符串
        if (isTElement) {
            //这个程序没经过
            //将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
            String value = lastIndexOrValue.trim();
            cellList.add(curCol, value);
            curCol++;
            isTElement = false;
            //如果里面某个单元格含有值，则标识该行不为空行
            if (!"".equals(value)) {
                flag = true;
            }
        } else if ("v".equals(name)) {
            //v => 单元格的值，如果单元格是字符串，则v标签的值为该字符串在SST中的索引
            Object value = this.getDataValue(lastIndexOrValue);
            //补全单元格之间的空单元格
            if (!ref.equals(preRef)) {
                countNullCellAndComple(ref, preRef);
            }
            cellList.add(curCol, value);
            curCol++;
            //如果里面某个单元格含有值，则标识该行不为空行
            if (value != null && !"".equals(value)) {
                flag = true;
            }
        } else {
            //如果标签名称为row，这说明已到行尾，调用optRows()方法
            if ("row".equals(name)) {
                //默认第一行为表头，以该行单元格数目为最大数目
                if (context.getCurrentRowIndex() == 0) {
                    maxRef = ref;
                }
                //补全一行尾部可能缺失的单元格
                if (maxRef != null) {
                    countNullCellAndComple(maxRef, ref);
                }

                //该行不为空行则发送
                if (flag) {
                    if (context.getCurrentRowIndex() == 0) {
                        callback.onNextTitle(context, new ArrayList<>(cellList));
                    } else {
                        callback.onNextRow(context, new ArrayList<>(cellList));
                    }
                    context.setTotalRows(context.getTotalRows() + 1);
                }

                cellList.clear();
                context.setCurrentRowIndex(context.getCurrentRowIndex() + 1);
                curCol = 0;
                preRef = null;
                ref = null;
                flag = false;
            }
        }
    }

    /**
     * 处理数据类型
     *
     * @param attributes Attributes
     */
    private void setNextDataType(Attributes attributes) {
        //cellType为空，则表示该单元格类型为数字
        nextDataType = CellDataType.NUMBER;
        formatIndex = -1;
        formatString = null;
        String cellType = attributes.getValue("t");
        String cellStyleStr = attributes.getValue("s");

        if ("b".equals(cellType)) {
            nextDataType = CellDataType.BOOL;
        } else if ("e".equals(cellType)) {
            nextDataType = CellDataType.ERROR;
        } else if ("inlineStr".equals(cellType)) {
            nextDataType = CellDataType.INLINESTR;
        } else if ("s".equals(cellType)) {
            nextDataType = CellDataType.SSTINDEX;
        } else if ("str".equals(cellType)) {
            nextDataType = CellDataType.FORMULA;
        }

        //处理日期
        if (cellStyleStr != null) {
            int styleIndex = Integer.parseInt(cellStyleStr);
            XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
            formatIndex = style.getDataFormat();
            formatString = style.getDataFormatString();

            if (formatString.contains("m/d/yy")) {
                nextDataType = CellDataType.DATE;
                formatString = "yyyy-MM-dd hh:mm:ss";
            }
        }
    }

    /**
     * 对解析出来的数据进行类型处理
     *
     * @param value 单元格的值，
     *              value代表解析：BOOL的为0或1， ERROR的为内容值，FORMULA的为内容值，INLINESTR的为索引值需转换为内容值，
     *              SSTINDEX的为索引值需转换为内容值， NUMBER为内容值，DATE为内容值
     * @return 值
     */
    private Object getDataValue(String value) {
        String result;
        switch (nextDataType) {
            // 这几个的顺序不能随便交换，交换了很可能会导致数据错误
            case BOOL:
                char first = value.charAt(0);
                return first == '0' ? "FALSE" : "TRUE";
            case ERROR:
                return "\"ERROR:" + value + '"';
            //公式
            case FORMULA:
                return '"' + value + '"';
            case INLINESTR:
                XSSFRichTextString rtsi = new XSSFRichTextString(value);
                return rtsi.toString();
            //字符串
            case SSTINDEX:
                try {
                    int idx = Integer.parseInt(value);
                    //根据idx索引值获取内容值
                    RichTextString rtss = sharedStringsTable.getItemAt(idx);
                    return rtss.toString().trim();
                } catch (NumberFormatException ex) {
                    return value;
                }
            case NUMBER:
                if (formatString != null) {
                    // 处理精度丢失问题
                    return formatter.formatRawCellContents(Double.parseDouble(value), formatIndex, formatString).trim();
                } else {
                    // todo : 如果原本就是一个长小数，则会自动省略掉后面的。所以应考虑更安全的方案
                    double valueDouble = Math.round(Double.parseDouble(value) * 10000000) / 10000000.0;
                    return convertIntPossible(valueDouble);
                }
            case DATE:
                result = formatter.formatRawCellContents(Double.parseDouble(value), formatIndex, formatString);
                // 对日期字符串作特殊处理，去掉T
                return result.replace("T", " ");
            default:
                return null;
        }
    }

    private void countNullCellAndComple(String ref, String preRef) {
        //excel2007最大行数是1048576，最大列数是16384，最后一列列名是XFD
        String xfd = ref.replaceAll("\\d+", "");
        String xfd_1 = preRef.replaceAll("\\d+", "");

        xfd = fillChar(xfd);
        xfd_1 = fillChar(xfd_1);

        char[] letter = xfd.toCharArray();
        char[] letter_1 = xfd_1.toCharArray();
        int res = (letter[0] - letter_1[0]) * 26 * 26 + (letter[1] - letter_1[1]) * 26 + (letter[2] - letter_1[2]);

        // 补全空白
        for (int i = 0; i < res - 1; i++) {
            cellList.add(curCol++, null);
        }
    }

    private String fillChar(String str) {
        int length = str.length();
        if (length < 3) {
            StringBuilder strBuilder = new StringBuilder(str);
            for (int i = 0; i < (3 - length); i++) {
                strBuilder.insert(0, '@');
            }
            str = strBuilder.toString();
        }
        return str;
    }

    protected Object convertIntPossible(double value) {
        long cellValueLong = (long) value;

        if ((double) cellValueLong == value) {
            return cellValueLong;

        } else {
            return value;
        }
    }
}
