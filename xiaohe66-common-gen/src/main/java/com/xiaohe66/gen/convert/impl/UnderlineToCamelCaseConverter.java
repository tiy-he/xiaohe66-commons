package com.xiaohe66.gen.convert.impl;

import com.xiaohe66.gen.convert.FieldNameConverter;

/**
 * @author xiaohe
 * @since 2022.02.22 16:11
 */
public class UnderlineToCamelCaseConverter implements FieldNameConverter {

    @Override
    public String convert(String origin) {

        String[] arr = origin.split("_");
        if (arr.length == 1) {
            return origin;
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (String str : arr) {

            stringBuilder.append(Character.toUpperCase(str.charAt(0)));
            if (str.length() > 1) {
                stringBuilder.append(str.substring(1));
            }
        }

        char firstChar = Character.toLowerCase(stringBuilder.charAt(0));
        stringBuilder.setCharAt(0, firstChar);

        return stringBuilder.toString();
    }
}
