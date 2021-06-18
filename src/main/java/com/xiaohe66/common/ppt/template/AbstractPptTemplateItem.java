package com.xiaohe66.common.ppt.template;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author xiaohe
 * @time 2021.06.11 16:37
 */
@AllArgsConstructor
public abstract class AbstractPptTemplateItem implements Comparable<AbstractPptTemplateItem> {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AbstractPptTemplateItem that = (AbstractPptTemplateItem) o;

        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public int compareTo(AbstractPptTemplateItem o) {
        if (o == null) {
            return -1;
        }
        return name.compareTo(o.name);
    }

    public enum Type {
        /**
         * 类型
         */
        IMG, TEXT
    }
}
