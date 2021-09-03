package com.xiaohe66.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaohe
 * @since 2021.09.03 11:01
 */
public class CsvUtils {

    private CsvUtils() {

    }


    /**
     * 读取csv 内容
     * todo : 未经过测试
     */
    public List<List<String>> readCsv(InputStream inputStream, Charset charset) throws IOException {

        try (InputStreamReader reader = new InputStreamReader(inputStream, charset);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            List<List<String>> list = new ArrayList<>();

            String line;


            while ((line = bufferedReader.readLine()) != null) {

                String[] row = line.split(",");

                List<String> rowList = new ArrayList<>(row.length);
                for (String str : row) {
                    rowList.add(getTramAndRemoveQuotationMark(str));
                }

                list.add(rowList);

            }

            return list;

        }
    }

    private static String getTramAndRemoveQuotationMark(String string) {
        return string.replace("\"", "").trim();
    }


}
