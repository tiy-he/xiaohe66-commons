package com.xiaohe66.common.table.db;

import com.xiaohe66.common.table.entity.TableConfig;
import com.xiaohe66.common.table.entity.TableField;
import com.xiaohe66.common.table.sqlbuilder.SqlBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaohe
 * @time 2020.05.13 15:08
 */
public abstract class AbstractDbHandler implements DbHandler {

    @Override
    public long importHandle(TableConfig config, List<List<Object>> param) {

        SqlBuilder sqlBuilder = config.getSqlBuilder();
        String sql;
        switch (config.getInsertType()) {
            case EXIST_IGNORE:
                sql = sqlBuilder.buildInsertSql(config.getTableName(), config.getFieldList(), SqlBuilder.InsertType.EXIST_IGNORE);
                return save(sql, param);

            case EXIST_UPDATE:
                sql = sqlBuilder.buildInsertSql(config.getTableName(), config.getFieldList(), SqlBuilder.InsertType.EXIST_UPDATE);
                return save(sql, param);

            case EXIST_UPDATE_SELECT:
                return doUpdate(config, param);

            default:
                throw new IllegalStateException("unknown insert type");
        }
    }

    private long doUpdate(TableConfig config, List<List<Object>> param) {
        SqlBuilder sqlBuilder = config.getSqlBuilder();

        // todo : 同一个表多次调用保存方法，造成多次创建sql
        String saveSql = sqlBuilder.buildInsertSql(config.getTableName(), config.getFieldList(), SqlBuilder.InsertType.EXIST_IGNORE);
        String updateSql = sqlBuilder.buildUpdateSql(config.getTableName(), config.getFieldList());
        String checkSql = sqlBuilder.checkExistSql(config.getTableName(), config.getFieldList());

        List<TableField> fieldList = config.getFieldList();

        List<Integer> updateFieldIndex = new ArrayList<>();
        List<Integer> selectFieldIndex = new ArrayList<>();

        // todo : 同一个表多次调用保存方法，造成多次创建sql
        int index = 0;
        for (TableField field : fieldList) {
            if (field.getFieldName() != null) {
                if (field.isUnique()) {
                    selectFieldIndex.add(index);
                } else {
                    updateFieldIndex.add(index);
                }

                index++;
            }
        }

        long result = 0;
        for (List<Object> item : param) {
            boolean isExist = checkExist(checkSql,
                    fetchSelectParam(item, selectFieldIndex));

            if (isExist) {
                result += update(updateSql,
                        fetchUpdateParam(item, updateFieldIndex, selectFieldIndex));

            } else {
                result += saveOne(saveSql, item);
            }
        }

        return result;
    }

    private List<Object> fetchSelectParam(List<Object> item, List<Integer> indexList) {
        List<Object> result = new ArrayList<>(indexList.size());

        for (Integer index : indexList) {
            result.add(item.get(index));
        }
        return result;
    }

    private List<Object> fetchUpdateParam(List<Object> item, List<Integer> updateIndexList, List<Integer> selectIndexList) {
        List<Object> result = new ArrayList<>(updateIndexList.size() + selectIndexList.size());
        for (Integer index : updateIndexList) {
            result.add(item.get(index));
        }
        for (Integer index : selectIndexList) {
            result.add(item.get(index));
        }
        return result;
    }

}
