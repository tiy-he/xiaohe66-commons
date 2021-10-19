package com.xiaohe66.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PdfUtilsTest {

    public static void main(String[] args) throws IOException {

        String html = "<h1>Hello itext</h1>\n" +
                "<table>\n" +
                "<tr>\n" +
                "\t<td>11</td>\n" +
                "\t<td>12</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "\t<td>21</td>\n" +
                "\t<td>22</td>\n" +
                "</tr>\n" +
                "<table>";
        File file = new File("C:\\Users\\Xiao He\\Desktop\\test.pdf");
        try (OutputStream outputStream = new FileOutputStream(file)) {

            PdfUtils.convertToPdf(html, outputStream);
        }
    }

}