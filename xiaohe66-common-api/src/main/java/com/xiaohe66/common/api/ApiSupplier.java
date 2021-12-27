package com.xiaohe66.common.api;

/**
 * @author xiaohe
 * @since 2021.12.27 15:42
 */
@FunctionalInterface
public interface ApiSupplier<T> {

    T get() throws ApiException;
}
