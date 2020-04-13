package com.xiaohe66.common.table;

import java.util.List;

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
     */
    void save(String sql, List<List<Object>> param);

}
