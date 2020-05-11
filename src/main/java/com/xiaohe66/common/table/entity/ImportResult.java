package com.xiaohe66.common.table.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xiaohe
 * @time 2020.05.09 10:20
 */
@Getter
@Setter
public class ImportResult {

    private boolean isSuccess;

    private String sql;

    private long excelQty;

    private long executeSqlQty;

    private long successQty;

    private String errorReason;
}
