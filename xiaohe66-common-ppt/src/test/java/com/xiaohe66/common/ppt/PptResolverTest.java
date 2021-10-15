package com.xiaohe66.common.ppt;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;

public class PptResolverTest {

    @Test
    public void test1() throws JsonProcessingException {
        String json = "[[{\"title\":[\"主标题\"],\"subtitle\":[\"副标题\"]}],[{\"item_name\":[\"主题1\"],\"image_type_1\":[\"D:\\\\xiaohe66\\\\1.png\",\"D:\\\\xiaohe66\\\\2.png\"],\"image_type_2\":[\"D:\\\\xiaohe66\\\\3.png\",\"D:\\\\xiaohe66\\\\4.png\"]},{\"item_name\":[\"主题2\"],\"image_type_1\":[\"D:\\\\xiaohe66\\\\4.png\",\"D:\\\\xiaohe66\\\\3.png\"],\"image_type_2\":[\"D:\\\\xiaohe66\\\\2.png\",\"D:\\\\xiaohe66\\\\1.png\"]}]]";

        System.out.println(PptResolver.parsePptData(json));

    }
}