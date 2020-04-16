package com.xiaohe66.common.table.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaohe
 * @time 2020.04.13 12:06
 */
public class ReaderContext {

    @Getter
    private File file;

    @Getter
    private List<TableField> fieldList;

    /**
     * 单次读取数量。小于0时，一次读取全表数据。大于0时，一次读取该值指定数据
     *
     * <P>当需要读取大表时，该值控制每次读取的行数量。每当读取满该值表示的数量后，就会激活consumer回调方法。
     */
    @Getter
    private int readQtyOnce;

    @Getter
    private Object otherParam;

    @Getter
    @Setter
    private List<Object> tableTitleList;

    @Getter
    @Setter
    private List<Object> configTitleList;

    /**
     * 用于读取器临时缓存数据
     */
    private Map<String, Object> cache;

    public ReaderContext(File file,
                         List<TableField> fieldList,
                         int readQtyOnce,
                         Object otherParam) {

        this.file = file;
        this.fieldList = fieldList;
        this.readQtyOnce = readQtyOnce;
        this.otherParam = otherParam;
    }


    public void cache(String key, Object value) {
        if (cache == null) {
            cache = new HashMap<>();
        }
        cache.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T cache(String key) {
        if (cache != null) {
            return (T) cache.get(key);
        }
        return null;
    }
}
