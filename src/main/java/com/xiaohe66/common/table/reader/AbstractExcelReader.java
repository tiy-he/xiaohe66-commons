package com.xiaohe66.common.table.reader;

import com.xiaohe66.common.table.entity.ParserContext;
import com.xiaohe66.common.table.entity.ReaderContext;
import com.xiaohe66.common.table.entity.Table;
import com.xiaohe66.common.table.parser.AbstractTableParserCallback;
import com.xiaohe66.common.table.parser.BigExcelParser;
import com.xiaohe66.common.table.parser.DefaultExcelParser;
import com.xiaohe66.common.table.parser.TableParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author xiaohe
 * @time 2020.04.09 11:59
 */
public abstract class AbstractExcelReader implements TableImportReader {

    /**
     * 读取用于插入数据库的数据
     *
     * @param context        读取器上下文
     * @param sourceDataList Excel表中的源数据
     * @return List<List < Object>> 用于插入数据库的数据，排序好的数据
     */
    protected abstract List<List<Object>> convertDbData(ReaderContext context, List<List<Object>> sourceDataList);

    @Override
    public void read(ReaderContext context, Consumer<Table> consumer) throws IOException {


        if (context.getReadQtyOnce() > 0) {
            TableParser tableParser = new BigExcelParser();
            tableParser.parse(context.getFile(), new BigExcelParserCallback(context, consumer));
        } else {

            TableParser tableParser = new DefaultExcelParser();
            tableParser.parse(context.getFile(), new DefaultTableParserCallback(context, consumer));
        }

    }

    private class DefaultTableParserCallback extends AbstractTableParserCallback {

        private DefaultTableParserCallback(ReaderContext context,
                                           Consumer<Table> consumer) {
            super(context, consumer);
        }

        @Override
        public void onNextRow(ParserContext parserContext, List<Object> data) {
            sourceDataList.add(data);
        }

        @Override
        public void onEnd(ParserContext parserContext) {
            List<List<Object>> dataList = convertDbData(readerContext, sourceDataList);
            Table table = new Table(parserContext.getSheetName(), this.sourceDataList, dataList);
            consumer.accept(table);
        }
    }

    private class BigExcelParserCallback extends AbstractTableParserCallback {

        private BigExcelParserCallback(ReaderContext context, Consumer<Table> consumer) {
            super(context, consumer);
        }

        @Override
        public void onNextRow(ParserContext parserContext, List<Object> data) {

            sourceDataList.add(data);

            if (sourceDataList.size() >= readerContext.getReadQtyOnce()) {
                List<List<Object>> dataList = convertDbData(readerContext, sourceDataList);
                Table table = new Table(parserContext.getSheetName(), this.sourceDataList, dataList);
                consumer.accept(table);
                sourceDataList = new ArrayList<>();
            }
        }

        @Override
        public void onEnd(ParserContext parserContext) {
            if (!sourceDataList.isEmpty()) {
                List<List<Object>> dataList = convertDbData(readerContext, sourceDataList);
                Table table = new Table(parserContext.getSheetName(), this.sourceDataList, dataList);
                consumer.accept(table);
            }
        }
    }
}
