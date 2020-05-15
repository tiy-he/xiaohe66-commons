package com.xiaohe66.common.table.sqlbuilder;

import com.xiaohe66.common.table.entity.TableField;

import java.util.List;

/**
 * @author xiaohe
 * @time 2020.04.09 14:37
 */
public interface SqlBuilder {

    /**
     * 根据配置生成sql
     *
     * @param tableName  表名
     * @param fieldList  字段列表
     * @param insertType 插入类型
     * @return sql
     */
    String buildInsertSql(String tableName, List<TableField> fieldList, InsertType insertType);

    /**
     * 根据配置生成查询sql
     *
     * @param tableName 表名
     * @param fieldList 字段列表
     * @param where     where 条件
     * @return sql
     */
    String buildSelectSql(String tableName, List<TableField> fieldList, String where);

    /**
     * 根据配置生成更新sql
     *
     * @param tableName 表名
     * @param fieldList 字段列表
     * @return sql
     */
    String buildUpdateSql(String tableName, List<TableField> fieldList);

    /**
     * 检查是否需要更新的sql
     *
     * @param tableName 表名
     * @param fieldList 字段列表
     * @return sql
     */
    String checkExistSql(String tableName, List<TableField> fieldList);

    enum InsertType {
        /**
         * 存在则忽略、存在则更新、存在则更新（用查询方式判断是否存在）
         */
        EXIST_IGNORE,
        EXIST_UPDATE,
        EXIST_UPDATE_SELECT
    }

}
