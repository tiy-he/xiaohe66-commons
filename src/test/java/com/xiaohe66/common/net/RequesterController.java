package com.xiaohe66.common.net;

import com.xiaohe66.common.net.xh.Page;
import com.xiaohe66.common.net.xh.Result;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * @author xiaohe
 * @time 2020.07.22 11:44
 */
@RestController
@RequestMapping("/test2")
public class RequesterController {


    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id) {
        Map<String, String> map = Collections.singletonMap("msg", "pageData");
        return ok(map);
    }

    @PostMapping
    public Result post() {
        return ok(1);
    }

    @PutMapping
    public Result put() {
        return ok(true);
    }

    @DeleteMapping("/{id}")
    public Result del(@PathVariable Integer id) {
        return ok(true);
    }

    @GetMapping
    public Result page(@RequestHeader("pageNo") int pageNo,
                       @RequestHeader("pageSize") int pageSize) {

        Page<Object> objectPage = new Page<>();

        Map<String, String> map = Collections.singletonMap("msg", "pageData");

        objectPage.setRecords(Collections.singletonList(map));

        return ok(objectPage);
    }


    private Result<Object> ok(Object data) {
        Result<Object> result = new Result<>();
        result.setCode(100);
        result.setMsg("this a msg");
        result.setData(data);

        return result;
    }

}
