package com.leyou.item.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 要子康
 * @description CategoryApi
 * @since 2020/7/10 10:56
 */
@RequestMapping("category")
public interface CategoryApi {

    @GetMapping("names")
    List<String> queryNamesByIds(@RequestParam("ids") List<Long> ids);
}