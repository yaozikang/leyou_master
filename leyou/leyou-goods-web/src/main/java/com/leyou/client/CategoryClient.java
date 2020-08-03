package com.leyou.client;

import com.leyou.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author 要子康
 * @description CategoryClient
 * @since 2020/7/10 10:59
 */
@FeignClient(value = "item-service")
public interface CategoryClient extends CategoryApi {
}
