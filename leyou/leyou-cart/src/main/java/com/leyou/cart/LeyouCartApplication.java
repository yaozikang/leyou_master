package com.leyou.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 要子康
 * @description LeyouCartApplication
 * @since 2020/7/24 14:21
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LeyouCartApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeyouCartApplication.class);
    }
}
