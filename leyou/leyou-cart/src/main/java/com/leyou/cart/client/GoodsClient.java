package com.leyou.cart.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author 要子康
 * @description GoodsClient
 * @since 2020/7/26 10:53
 */
@FeignClient(value = "item-service")
public interface GoodsClient extends GoodsApi {
}