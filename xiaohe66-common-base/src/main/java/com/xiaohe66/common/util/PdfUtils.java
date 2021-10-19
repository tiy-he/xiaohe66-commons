package com.xiaohe66.common.util;

import com.itextpdf.html2pdf.HtmlConverter;

import java.io.OutputStream;

/**
 * @author xiaohe
 * @since 2021.10.19 10:12
 */
public class PdfUtils {

    private PdfUtils() {
    }

    public static void convertToPdf(String html, OutputStream outputStream) {
        HtmlConverter.convertToPdf(html, outputStream);
    }
}
