package com.leyou.auth.client;

import com.leyou.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author 要子康
 * @description UserClient
 * @since 2020/7/23 22:24
 */
@FeignClient(value = "user-service")
public interface UserClient extends UserApi {
}
