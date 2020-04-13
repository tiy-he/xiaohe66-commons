package com.xiaohe66.common.table.sqlbuilder;

import com.xiaohe66.common.table.entity.TableField;

import java.util.List;

/**
 * @author xiaohe
 * @time 2020.04.09 14:37
 */
public interface SqlBuilder {

    /**
     * 生成根据配置生成sql
     *
     * @param tableName  表名
     * @param fieldList  字段列表
     * @param insertType 插入类型
     * @return sql
     */
    String buildInsertSql(String tableName, List<TableField> fieldList, InsertType insertType);

    enum InsertType {
        /**
         * 存在则忽略、存在则更新
         */
        EXIST_IGNORE,
        EXIST_UPDATE
    }

}
