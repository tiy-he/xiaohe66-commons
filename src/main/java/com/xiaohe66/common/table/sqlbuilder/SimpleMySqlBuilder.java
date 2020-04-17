package com.xiaohe66.common.table.sqlbuilder;

import com.xiaohe66.common.table.entity.TableField;
import com.xiaohe66.common.table.ex.TableImportSqlBuilderException;

import java.util.List;

/**
 * @author xiaohe
 * @time 2020.04.08 18:13
 */
public class SimpleMySqlBuilder implements SqlBuilder {

    private static SimpleMySqlBuilder simpleMySqlBuilder = new SimpleMySqlBuilder();

    protected SimpleMySqlBuilder() {
    }

    public static SimpleMySqlBuilder getInstance() {
        return simpleMySqlBuilder;
    }


    @Override
    public String buildInsertSql(String tableName, List<TableField> fieldList, InsertType insertType) {

        StringBuilder fieldBuilder = new StringBuilder();
        StringBuilder valuesBuilder = new StringBuilder();

        for (TableField field : fieldList) {
            String fieldName = field.getFieldName();
            if (fieldName != null) {
                fieldBuilder.append(',').append(fieldName);
                valuesBuilder.append(",?");
            }
        }

        if (fieldBuilder.length() == 0 || valuesBuilder.length() == 0) {
            throw new TableImportSqlBuilderException("导入字段不够");
        }

        if (insertType == InsertType.EXIST_IGNORE) {

            return String.format("insert ignore into %s (%s) values (%s)",
                    tableName,
                    fieldBuilder.substring(1),
                    valuesBuilder.substring(1));
        } else {

            throw new UnsupportedOperationException("暂未实现存在即更新");
        }
    }

    @Override
    public String buildSelectSql(String tableName, List<TableField> fieldList, String where) {

        StringBuilder needShowFields = new StringBuilder();

        for (TableField field : fieldList) {
            if (field.isShow()) {
                needShowFields.append(',').append(field.getFieldName());
            }
        }

        return String.format("select %s from %s where 1=1 %s",
                needShowFields.substring(1),
                tableName,
                where);
    }
}
