package com.lin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 应用启动类
 * @author lkmc2
 * @date 2019-09-13 13:47
 */
@MapperScan("com.lin.dao")
@SpringBootApplication
public class SpringBootNettyDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootNettyDemoApplication.class, args);
    }

}
