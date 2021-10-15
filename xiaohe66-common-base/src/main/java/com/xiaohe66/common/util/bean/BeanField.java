package com.xiaohe66.common.util.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Field;

/**
 * @author xiaohe
 * @time 2020.07.20 10:47
 */
@Getter
@AllArgsConstructor
public class BeanField {

    private Field field;
    private String name;

}
