package com.xiaohe66.common.ppt.template;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaohe
 * @time 2021.06.11 16:37
 */
@AllArgsConstructor
public abstract class PptTemplateItem {

    @Getter
    protected final String name;

    /**
     * 返回类型
     *
     * @return 返回类型
     */
    public abstract Type getType();

    public String getReplaceName() {
        return "${" + name + "}";
    }

    public enum Type {
        /**
         * 类型
         */
        IMG, TEXT
    }
}
