package com.xiaohe66.common.table.reader;

import com.xiaohe66.common.table.entity.ReaderContext;
import com.xiaohe66.common.table.entity.TableField;
import com.xiaohe66.common.table.ex.TableImportExcelFormatException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xiaohe
 * @time 2020.04.09 11:57
 */
@Slf4j
public class TableTitleExcelReader extends AbstractExcelReader {

    private static TableTitleExcelReader tableTitleReader = new TableTitleExcelReader();

    protected TableTitleExcelReader() {
    }

    public static TableTitleExcelReader getInstance() {
        return tableTitleReader;
    }

    @Override
    public List<List<Object>> convertDbData(ReaderContext context,
                                            List<List<Object>> sourceDataList) {

        int[] columnArr = checkAndGetIndex(context);

        if (log.isDebugEnabled()) {
            log.debug("表头 : {}", context.getTableTitleList());
            log.debug("读取到的下标 : {}", Arrays.toString(columnArr));
        }

        int rowQty = sourceDataList.size();
        List<List<Object>> dataList = new ArrayList<>(rowQty);

        for (List<Object> row : sourceDataList) {

            List<Object> data = doConvertDbData(row, columnArr, context.getFieldList());

            dataList.add(data);
        }

        return dataList;
    }

    protected List<Object> doConvertDbData(List<Object> row, int[] columnArr, List<TableField> fieldList) {

        List<Object> data = new ArrayList<>(columnArr.length);
        for (int i = 0; i < columnArr.length; i++) {

            TableField field = fieldList.get(i);
            if (field.getFieldName() == null) {
                continue;
            }

            int column = columnArr[i];

            Object value = null;
            if (column >= 0) {
                value = row.get(column);

            }

            if (value == null) {
                if (field.getDefVal() != null) {
                    value = field.getDefVal();

                } else if (field.getValueCreator() != null) {
                    try {
                        value = field.getValueCreator().apply(row, columnArr);
                    } catch (Exception e) {
                        log.error("数据生成器调用时发生异常, msg : {}", e.getMessage());
                        log.debug("数据生成器调用时发生异常, msg : {}", e.getMessage(), e);
                    }
                }
            }

            data.add(value);
        }
        return data;
    }

    protected int[] checkAndGetIndex(ReaderContext context) {


        int[] indexArr = context.cache("indexArr");
        if (indexArr != null) {
            return indexArr;
        }

        List<TableField> fieldList = context.getFieldList();
        List<Object> tableTitleList = context.getTableTitleList();

        indexArr = new int[fieldList.size()];
        context.cache("indexArr", indexArr);

        List<String> lockTitleList = new ArrayList<>();

        for (int i = 0, n = fieldList.size(); i < n; i++) {
            TableField field = fieldList.get(i);

            String title = field.getTableTitle();

            if (title == null) {
                indexArr[i] = -1;
                continue;
            }

            int index = indexOf(tableTitleList, title);

            if (index >= 0) {
                indexArr[i] = index;

            } else {

                // 用 -1标记未找到的表头
                indexArr[i] = -1;

                // 必填项缺失，记录缺失的表头
                if (field.isRequire()) {
                    lockTitleList.add(title);
                }
            }
        }

        if (!lockTitleList.isEmpty()) {
            String msg = lockTitleList.toString();
            log.error("表格格式错误，缺少表头 : {}", msg);
            throw new TableImportExcelFormatException(msg);
        }

        return indexArr;
    }

    private int indexOf(List<Object> tableTitleList, String title) {

        for (int i = 0; i < tableTitleList.size(); i++) {

            Object tableTitle = tableTitleList.get(i);

            if (title.equals(tableTitle.toString())) {

                return i;
            }
        }
        return -1;
    }
}
