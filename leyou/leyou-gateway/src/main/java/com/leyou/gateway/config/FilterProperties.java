package com.leyou.gateway.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author 要子康
 * @description FilterProperties
 * @since 2020/7/24 10:48
 */
@ConfigurationProperties(prefix = "leyou.filter")
public class FilterProperties {

    private List<String> allowPaths;

    public List<String> getAllowPaths() {
        return allowPaths;
    }

    public void setAllowPaths(List<String> allowPaths) {
        this.allowPaths = allowPaths;
    }
}
