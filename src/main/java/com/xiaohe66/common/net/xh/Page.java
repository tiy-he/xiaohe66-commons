package com.xiaohe66.common.net.xh;

import java.util.List;

public class Page<T> {

    private int pages;
    private int total;
    private int size;
    private int current;
    private boolean optimizeCountSql;
    private boolean isSearchCount;
    private List<T> records;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public boolean isOptimizeCountSql() {
        return optimizeCountSql;
    }

    public void setOptimizeCountSql(boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
    }

    public boolean isIsSearchCount() {
        return isSearchCount;
    }

    public void setIsSearchCount(boolean isSearchCount) {
        this.isSearchCount = isSearchCount;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "{" + "\"pages\":\"" + pages + "\""
                + ",\"total\":\"" + total + "\""
                + ",\"size\":\"" + size + "\""
                + ",\"current\":\"" + current + "\""
                + ",\"optimizeCountSql\":\"" + optimizeCountSql + "\""
                + ",\"isSearchCount\":\"" + isSearchCount + "\""
                + ",\"records\":" + records
                + "}";
    }
}
