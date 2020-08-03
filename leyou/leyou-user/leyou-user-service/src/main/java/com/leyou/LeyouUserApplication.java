package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;


/**
 * @author 要子康
 * @description LeyouUserApplication
 * @since 2020/7/18 14:34
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.leyou.mapper")
public class LeyouUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeyouUserApplication.class, args);
    }
}
