package com.xiaohe66.common.net.xh;

import lombok.Data;

import java.util.List;

/**
 * @author xiaohe
 * @time 17-10-31 031
 */
@Data
public class Page<T> {

    private int pages;
    private int total;
    private int size;
    private int current;
    private boolean optimizeCountSql;
    private boolean isSearchCount;
    private List<T> records;

}
