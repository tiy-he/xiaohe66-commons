package com.xiaohe66.common.table.entity;

import com.xiaohe66.common.table.DbHandler;
import com.xiaohe66.common.table.reader.TableImportReader;
import com.xiaohe66.common.table.sqlbuilder.SqlBuilder;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * @author xiaohe
 * @time 2020.04.09 11:33
 */
@Getter
@Builder
public class TableConfig {

    private String tableName;
    private List<TableField> fieldList;
    private TableImportReader reader;
    private int readQtyOnce;
    private DbHandler dbHandler;
    private SqlBuilder sqlBuilder;
    private SqlBuilder.InsertType insertType;

}
