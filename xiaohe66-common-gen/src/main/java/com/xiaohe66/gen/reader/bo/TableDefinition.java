package com.xiaohe66.gen.reader.bo;

import lombok.Data;

import java.util.List;

/**
 * @author xiaohe
 * @since 2022.02.21 17:14
 */
@Data
public class TableDefinition {

    private String tableName;
    private List<TableField> fields;

}
