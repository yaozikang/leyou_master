package com.leyou.cart.interceptor;

import com.leyou.cart.config.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 要子康
 * @description MvcConfig
 * @since 2020/7/26 09:53
 */
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private JwtProperties jwtProperties;

    @Bean
    public LoginInterceptor loginInterceptor(){

        return new LoginInterceptor(jwtProperties);

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**");
    }
}
