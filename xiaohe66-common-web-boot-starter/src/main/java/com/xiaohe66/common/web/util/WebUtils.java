package com.xiaohe66.common.web.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author xiaohe
 * @since 2022.01.01 15:20
 */
public class WebUtils {

    protected WebUtils() {

    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static HttpServletResponse getResponse() {
        return getAttributes().getResponse();
    }

    public static HttpServletRequest getRequest() {
        return getAttributes().getRequest();
    }

    protected static ServletRequestAttributes getAttributes() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes());
    }


}
