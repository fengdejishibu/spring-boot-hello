package com.example.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器类
 * 处理浏览器的 HTTP 请求
 */
@RestController
public class HelloController {

    /**
     * 处理 GET 请求
     * 当浏览器访问 /hello 时，返回问候文本
     */
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello Spring Boot";
    }
}
