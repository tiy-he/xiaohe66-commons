package com.xiaohe66.commons.web;

import com.xiaohe66.common.dto.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author xiaohe
 * @since 2022.03.11 12:02
 */
@RestController
public class BootStarterTestController {

    @GetMapping("/test/{id}/list")
    public R<Long> get(@PathVariable Long id) {
        return R.ok(id);
    }

    @GetMapping("/test")
    public R<String> get(@RequestParam Long n,
                       @RequestHeader String s) {

        return R.ok(s + "_" + n);
    }

    @PostMapping("/test")
    public R<String> get(@RequestBody String body) {

        return R.ok(body);
    }

}
