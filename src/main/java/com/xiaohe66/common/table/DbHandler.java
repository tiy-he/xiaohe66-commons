package com.xiaohe66.common.table;

import java.util.List;
import java.util.Map;

/**
 * @author xiaohe
 * @time 2020.04.09 11:31
 */
public interface DbHandler {

    /**
     * 保存
     *
     * @param sql   sql
     * @param param 值
     * @return 影响行数
     */
    long save(String sql, List<List<Object>> param);

    /**
     * 查询
     *
     * @param sql   查询sql
     * @param param 查询参数
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> query(String sql, Object... param);

}
