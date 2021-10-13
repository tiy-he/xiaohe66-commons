package com.xiaohe66.common.net;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

/**
 * @author xiaohe
 * @since 2021.09.24 17:41
 */
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Page<T> {

    private long pages;
    private long total;
    private long size;
    private long current;
    private List<T> records;

    public static <T> Page<T> emptyPage(int pageNo, int pageSize) {

        PageBuilder<T> builder = Page.builder();

        return builder
                .pages(0)
                .total(0)
                .current(pageNo)
                .size(pageSize)
                .records(Collections.emptyList())
                .build();
    }

}
