package com.xiaohe66.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * @author xiaohe
 * @since 2022.01.01 14:59
 */
public class XmlUtils {

    private static final XmlMapper xmlMapper = new XmlMapper();

    static {

    }

    protected XmlUtils() {

    }

    public static String toString(Object obj) throws JsonProcessingException {
        return xmlMapper.writeValueAsString(obj);
    }

}
