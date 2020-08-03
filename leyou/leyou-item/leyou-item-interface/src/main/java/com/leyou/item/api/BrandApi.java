package com.leyou.item.api;

import com.leyou.item.pojo.Brand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 要子康
 * @description BrandApi
 * @since 2020/7/10 10:52
 */
@RequestMapping("brand")
public interface BrandApi {

    @GetMapping("{id}")
    Brand queryBrandById(@PathVariable("id") Long id);
}
