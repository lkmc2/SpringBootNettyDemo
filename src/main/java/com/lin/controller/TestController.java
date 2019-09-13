package com.lin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 * @author lkmc2
 * @date 2019/9/13 13:51
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Test";
    }

}
