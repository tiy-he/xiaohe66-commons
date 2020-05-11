package com.xiaohe66.common.table.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xiaohe
 * @time 2020.04.13 14:39
 */
@Getter
@Setter
public class ParserContext {

    private int currentRowIndex;

    private int totalRows;

    private String sheetName;

    private int sheetIndex;

}
