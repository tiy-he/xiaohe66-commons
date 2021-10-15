package com.xiaohe66.common.table.sqlbuilder;

import com.xiaohe66.common.table.entity.TableField;
import com.xiaohe66.common.table.ex.TableImportSqlBuilderException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaohe
 * @time 2020.04.08 18:13
 */
public class SimpleMySqlBuilder implements SqlBuilder {

    private static SimpleMySqlBuilder simpleMySqlBuilder = new SimpleMySqlBuilder();

    private Map<String, String> cache = new ConcurrentHashMap<>();

    protected SimpleMySqlBuilder() {
    }

    public static SimpleMySqlBuilder getInstance() {
        return simpleMySqlBuilder;
    }


    @Override
    public String buildInsertSql(String tableName, List<TableField> fieldList, InsertType insertType) {

        String sql = cache.get(tableName);

        if (sql != null) {
            return sql;
        }

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
            throw new TableImportSqlBuilderException("导入字段至少需要1个");
        }

        if (insertType == InsertType.EXIST_IGNORE) {

            sql = String.format("insert ignore into %s (%s) values (%s)",
                    tableName,
                    fieldBuilder.substring(1),
                    valuesBuilder.substring(1));
        } else {


            StringBuilder updateField = new StringBuilder("");
            for (TableField field : fieldList) {

                String fieldName = field.getFieldName();
                boolean isRequireUpdate = StringUtils.isNotEmpty(fieldName) && field.isUpdate();
                if (isRequireUpdate) {
                    updateField.append(',').append(fieldName).append("=values(").append(fieldName).append(')');
                }
            }

            sql = updateField.length() > 0 ?
                    String.format("insert into %s (%s) values (%s) ON DUPLICATE KEY UPDATE %s",
                            tableName,
                            fieldBuilder.substring(1),
                            valuesBuilder.substring(1),
                            updateField.substring(1))
                    :
                    String.format("insert ignore into %s (%s) values (%s)",
                            tableName,
                            fieldBuilder.substring(1),
                            valuesBuilder.substring(1));
        }

        cache.put(tableName, sql);

        return sql;
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

    @Override
    public String buildUpdateSql(String tableName, List<TableField> fieldList) {

        StringBuilder needUpdateFields = new StringBuilder();
        StringBuilder needSelectFields = new StringBuilder();

        for (TableField field : fieldList) {
            if (field.getFieldName() != null) {
                if (field.isUnique()) {
                    needSelectFields.append(" and ").append(field.getFieldName()).append("=?");
                } else {
                    needUpdateFields.append(',').append(field.getFieldName()).append("=?");
                }
            }
        }

        if (needSelectFields.length() <= 4) {
            throw new IllegalStateException("配置错误，导入类型为存在即更新，但没有判断存在的字段");
        }

        return String.format("update %s set %s where %s",
                tableName,
                needUpdateFields.substring(1),
                needSelectFields.substring(4));
    }

    @Override
    public String checkExistSql(String tableName, List<TableField> fieldList) {

        StringBuilder needSelectFields = new StringBuilder();

        for (TableField field : fieldList) {
            if (field.getFieldName() != null && field.isUnique()) {
                needSelectFields.append(" and ").append(field.getFieldName()).append("=?");
            }
        }

        if (needSelectFields.length() <= 4) {
            throw new IllegalStateException("配置错误，导入类型为存在即更新，但没有判断存在的字段");
        }

        return String.format("select count(1) from %s where %s",
                tableName,
                needSelectFields.substring(4));
    }
}
