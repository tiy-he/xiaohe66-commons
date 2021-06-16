package com.xiaohe66.common.ppt;

import com.xiaohe66.common.ppt.template.PptTemplate;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PptCreatorTest {

    String json = "[[{\"title\":[\"主标题\"],\"subtitle\":[\"副标题\"]}],[{\"item_name\":[\"主题1\"],\"image_type_1\":[\"file:///D:\\\\xiaohe66\\\\1.png\",\"file:///D:\\\\xiaohe66\\\\2.png\"],\"image_type_2\":[\"file:///D:\\\\xiaohe66\\\\3.png\",\"file:///D:\\\\xiaohe66\\\\4.png\"],\"test\":[\"666\"]},{\"item_name\":[\"主题2\"],\"image_type_1\":[\"file:///D:\\\\xiaohe66\\\\4.png\",\"file:///D:\\\\xiaohe66\\\\3.png\"],\"image_type_2\":[\"file:///D:\\\\xiaohe66\\\\2.png\",\"file:///D:\\\\xiaohe66\\\\1.png\"],\"test\":[\"777\"]}]]";

    File file = new File("D:\\xiaohe66\\template.pptx");
    File outFile = new File("D:\\xiaohe66\\gen.pptx");
    File outFile2 = new File("D:\\xiaohe66\\gen2.pptx");

    @Test
    public void test1() throws IOException, InterruptedException {

        try (PptTemplate pptTemplate = new PptTemplate(file);
             OutputStream outputStream = new FileOutputStream(outFile);
             OutputStream outputStream2 = new FileOutputStream(outFile2)) {

            PptCreator pptCreator = new PptCreator(pptTemplate);

            pptCreator.generateToOutputStream(outputStream, json);
            pptCreator.generateToOutputStream(outputStream2, json);
        }

    }
}