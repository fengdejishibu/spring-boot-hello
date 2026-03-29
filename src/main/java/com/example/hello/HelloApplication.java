package com.example.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 启动类
 * 包含 main 方法，用于启动整个应用程序
 */
@SpringBootApplication
public class HelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class, args);
        System.out.println("========================================");
        System.out.println("  Spring Boot 项目启动成功！");
        System.out.println("  访问地址: http://localhost:8080/hello");
        System.out.println("========================================");
    }
}
