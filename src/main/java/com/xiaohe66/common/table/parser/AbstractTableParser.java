package com.xiaohe66.common.table.parser;

/**
 * @author xiaohe
 * @time 2020.04.13 17:08
 */
public abstract class AbstractTableParser implements TableParser {

    protected Object convertIntPossible(double value) {
        long cellValueLong = (long) value;

        if ((double) cellValueLong == value) {
            return cellValueLong;

        } else {
            return value;
        }
    }
}
