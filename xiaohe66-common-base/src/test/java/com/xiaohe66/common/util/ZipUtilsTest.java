package com.xiaohe66.common.util;

import java.io.IOException;

public class ZipUtilsTest {


    public static void main(String[] args) throws IOException {

        String inPath = "D:/xiaohe66/zip-test/files";
        String outPath = "D:/xiaohe66/zip-test/target.zip";

        String outPath2 = "D:/xiaohe66/zip-test/newFiles";

        //ZipUtils.zip(inPath, outPath);

        ZipUtils.unZip(outPath, outPath2);

    }

}