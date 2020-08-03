package com.leyou.client;

import com.leyou.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author 要子康
 * @description SpecificationClient
 * @since 2020/7/10 11:00
 */
@FeignClient(value = "item-service")
public interface SpecificationClient extends SpecificationApi {
}
