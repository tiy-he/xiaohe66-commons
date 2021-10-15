package com.xiaohe66.common.table.db;

import com.xiaohe66.common.table.entity.TableConfig;

import java.util.List;
import java.util.Map;

/**
 * @author xiaohe
 * @time 2020.04.09 11:31
 */
public interface DbHandler {

    /**
     * 导入处理
     *
     * @param config 表格配置
     * @param param  数据
     * @return 影响行数
     */
    long importHandle(TableConfig config, List<List<Object>> param);

    /**
     * 保存
     *
     * @param sql   sql
     * @param param 值
     * @return 影响行数
     */
    long save(String sql, List<List<Object>> param);


    /**
     * update 方法
     *
     * @param sql   update sql
     * @param param update param
     * @return 影响行数
     */
    default long saveOne(String sql, List<Object> param) {
        throw new UnsupportedOperationException("不支持本操作，请重写本方法");
    }

    /**
     * update 方法
     *
     * @param sql   update sql
     * @param param update param
     * @return 影响行数
     */
    default long update(String sql, List<Object> param) {
        throw new UnsupportedOperationException("不支持本操作，请重写本方法");
    }

    /**
     * 检查是否重复
     *
     * @param sql   检查是否重复sql
     * @param param 参数
     * @return 返回true表示存在重复，返回false表示不存在重复
     */
    default boolean checkExist(String sql, List<Object> param) {
        return false;
    }

    /**
     * 查询
     *
     * @param sql   查询sql
     * @param param 查询参数
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> query(String sql, Object... param);

}
