package com.xiaohe66.common.table.reader;

import com.xiaohe66.common.table.entity.ReaderContext;

import java.util.List;

/**
 * @author xiaohe
 * @time 2020.04.08 18:25
 */
public class OrderedExcelReader extends AbstractExcelReader {

    @Override
    protected List<List<Object>> convertDbData(ReaderContext context, List<List<Object>> sourceDataList) {
        return sourceDataList;
    }

}
