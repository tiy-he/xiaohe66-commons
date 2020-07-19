package com.xiaohe66.common.util.json;

import com.xiaohe66.common.net.xh.Result;
import com.xiaohe66.common.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author xiaohe
 * @time 2020.01.13 11:18
 */
@Slf4j
public class JsonUtilsTest {


    @Test
    public void testFormatObj() {

        Result<JsonUtilsTestObj> result = new Result<>();
        result.setCode(0);
        result.setMsg("msg");

        result.setData(new JsonUtilsTestObj("测试名称"));
        String json = JsonUtils.toString(result);

        log.info("format json : {}", json);
        Result<JsonUtilsTestObj> formatResult = JsonUtils.formatResult(json, JsonUtilsTestObj.class);
        log.info("format结果 : {}", formatResult);

        assertEquals(result, formatResult);
    }

    @Test
    public void testFormatObj2() {

        Result<Object> result = new Result<>();
        result.setCode(0);
        result.setMsg("msg");
        result.setData(new JsonUtilsTestObj("测试名称"));

        String json = JsonUtils.toString(result);

        log.info("format json : {}", json);
        Result<String> formatResult = JsonUtils.formatResult(json, String.class);
        log.info("format结果 : {}", formatResult);

        result.setData("{\"name\":\"测试名称\"}");

        assertEquals(result, formatResult);

    }

    @Test
    public void testFormatArray() {

        Result<List<JsonUtilsTestObj>> result = new Result<>();
        result.setCode(0);
        result.setMsg("msg");

        List<JsonUtilsTestObj> list = Collections.singletonList(new JsonUtilsTestObj("测试数组"));
        result.setData(list);

        String json = JsonUtils.toString(result);

        log.info("format json : {}", json);
        Result<List<JsonUtilsTestObj>> formatResult = JsonUtils.formatResultList(json, JsonUtilsTestObj.class);
        log.info("format结果 : {}", formatResult);

        assertEquals(result, formatResult);
    }
}
