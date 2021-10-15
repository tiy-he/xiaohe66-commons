package com.xiaohe66.common.table;

/**
 * @author xiaohe
 * @time 2020.04.09 11:30
 */
public interface TableFieldVerifier {

    /**
     * 判断值是否正确
     *
     * @param value 值
     * @return true:正确，false:错误
     */
    boolean verify(Object value);

}
