package com.xiaohe66.common.util;

import java.util.regex.Pattern;

/**
 *
 * html相关操作
 *
 * @author xh
 * @time 18-03-21 021
 */
public class HtmlUtils {

    private static final String REG_EX_SCRIPT="<script[^>]*?>[\\s\\S]*?<\\/script>";
    private static final String REG_EX_STYLE="<style[^>]*?>[\\s\\S]*?<\\/style>";
    private final static String REG_EX_HTML="<[^>]+>";

    private static final Pattern SCRIPT_PATTERN;
    private static final Pattern STYLE_PATTERN;
    private static final Pattern HTML_PATTERN;

    static {
        SCRIPT_PATTERN = Pattern.compile(REG_EX_SCRIPT,Pattern.CASE_INSENSITIVE);
        STYLE_PATTERN = Pattern.compile(REG_EX_STYLE,Pattern.CASE_INSENSITIVE);
        HTML_PATTERN = Pattern.compile(REG_EX_HTML,Pattern.CASE_INSENSITIVE);
    }

    private HtmlUtils(){}

    /**
     * html标签清除
     * @param html  原html字符串
     * @return  清除html标签后的字符串
     */
    public static String delHtmlTag(String html){
        if(html == null){
            return null;
        }
        html = SCRIPT_PATTERN.matcher(html).replaceAll("");
        html = STYLE_PATTERN.matcher(html).replaceAll("");
        html = HTML_PATTERN.matcher(html).replaceAll("");

        return html.trim();
    }

    public static String delScriptTag(String html){
        return SCRIPT_PATTERN.matcher(html).replaceAll("").trim();
    }


    /**
     * 返回包含html标签的文本摘要，先清楚html标签，再返回摘要
     * @param text      原文本
     * @param length    摘要的长度
     * @return  删除html标签后，再进行摘要处理的字符串
     */
    public static String digest(String text,int length){
        if (text == null) {
            return "";
        }
        String result = delHtmlTag(text);
        if(result.length() > length){
            return result.substring(0,length)+"......";
        }
        return result;
    }


}
