package com.xiaohe66.common.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author xiaohe
 * @since 2021.11.30 14:43
 */
@ToString
@EqualsAndHashCode
@Getter
public class Paging {

    private final long before;
    private final int size;

    public Paging(Long before, Integer size) {

        this.before = getLogicalBefore(before);
        this.size = getLogicalSize(size);
    }

    protected long getLogicalBefore(Long before) {
        return before == null || before < 0 ? 0 : before;
    }

    protected int getLogicalSize(Integer size) {
        if (size == null) {
            return 10;

        } else if (size < 5) {
            return 5;

        } else if (size > 50) {
            return 50;

        } else {
            return size;
        }
    }

    public String toLimit() {
        return "limit " + before + "," + size;
    }
}
