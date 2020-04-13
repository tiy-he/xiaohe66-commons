package com.xiaohe66.common.table.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author xiaohe
 * @time 2020.04.08 17:59
 */
@Getter
@AllArgsConstructor
public class Table {

    private String name;

    /**
     * 源数据（excel表中按顺序直接读取的数据，不包括表头）
     */
    private List<List<Object>> sourceDataList;

    /**
     * 排序好的数据（一般情况下，可直接使用这份数据用于插入数据库）
     *
     * <p>考虑到字段可能会额外添加，因为没有用 List<Object[]>
     */
    private List<List<Object>> dataList;

}
