package com.xiaohe66.common.table.parser;

import com.xiaohe66.common.table.entity.ParserContext;
import com.xiaohe66.common.table.entity.ReaderContext;
import com.xiaohe66.common.table.entity.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author xiaohe
 * @time 2020.04.13 15:11
 */
public abstract class AbstractTableParserCallback implements TableParseCallback {

    protected List<List<Object>> sourceDataList;
    protected ReaderContext readerContext;
    protected Consumer<Table> consumer;
    protected int readQtyOnce;

    public AbstractTableParserCallback(ReaderContext context,
                                        Consumer<Table> consumer) {

        this.readerContext = context;
        this.consumer = consumer;
        readQtyOnce = context.getReadQtyOnce();
        sourceDataList = new ArrayList<>(readQtyOnce > 0 ? readQtyOnce : 10);
    }

    @Override
    public void onNextTitle(ParserContext context, List<Object> data) {
        readerContext.setTableTitleList(data);
    }
}
