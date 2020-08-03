package com.leyou.cart.service;

import com.leyou.auth.entity.UserInfo;
import com.leyou.cart.client.GoodsClient;
import com.leyou.cart.interceptor.LoginInterceptor;
import com.leyou.cart.pojo.Cart;
import com.leyou.common.utils.JsonUtils;
import com.leyou.item.pojo.Sku;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 要子康
 * @description CartService
 * @since 2020/7/26 10:37
 */
@Service
public class CartService {

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "leyou:cart:uid";

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    public void addCart(Cart cart){

        //获取登陆用户
        UserInfo user = LoginInterceptor.getLoginUser();
        //redis的key
        String key = KEY_PREFIX + user.getId();
        //获取hash对象
        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);
        //查询是否存在
        Long skuId = cart.getSkuId();
        Integer num = cart.getNum();
        Boolean bool = hashOps.hasKey(skuId.toString());
        if (bool){
            //存在，获取购物车数据
            String json = hashOps.get(skuId.toString()).toString();
            cart = JsonUtils.parse(json, Cart.class);
            //修改购物车数据
            cart.setNum(cart.getNum() + num);
        }else {
            //不存在，新增购物车数据
            cart.setUserId(user.getId());
            //其它商品信息，需要查询商品服务
            Sku sku = this.goodsClient.querySkuBySkuId(skuId);
            cart.setImage(StringUtils.isBlank(sku.getImages()) ? "" :
                    StringUtils.split(sku.getImages(), ",")[0]);
            cart.setPrice(sku.getPrice());
            cart.setTitle(sku.getTitle());
            cart.setOwnSpec(sku.getOwnSpec());
        }
        //将购物车数据写入redis
        hashOps.put(cart.getSkuId().toString(), JsonUtils.serialize(cart));
    }

    public List<Cart> queryCartList(){

        //获取登陆用户
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        //判断是否存在购物车
        String key = KEY_PREFIX + userInfo.getId();
        if (!this.redisTemplate.hasKey(key)){
            //不存在直接返回
            return null;
        }
        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);
        List<Object> carts = hashOps.values();
        //判断是否有数据
        if (CollectionUtils.isEmpty(carts)){
            return null;
        }
        //查询购物车数据
        return carts.stream().map(o -> JsonUtils.parse(o.toString(),
                Cart.class)).collect(Collectors.toList());
    }

    public void updateCarts(Cart cart){
        //获取登陆信息
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        String key = KEY_PREFIX + userInfo.getId();
        //获取hash操作对象
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(key);
        //获取购物车信息
        String cartJson = hashOperations.get(cart.getSkuId().toString()).toString();
        Cart cartUpdate = JsonUtils.parse(cartJson, Cart.class);
        //更新数量
        cartUpdate.setNum(cart.getNum());
        //写入购物车
        hashOperations.put(cart.getSkuId().toString(), JsonUtils.serialize(cartUpdate));
    }

    public void deleteCart(String skuId){
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        String key = KEY_PREFIX + userInfo.getId();
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(key);
        hashOperations.delete(skuId);
    }
}
