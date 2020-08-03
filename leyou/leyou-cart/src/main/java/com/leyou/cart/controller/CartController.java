package com.leyou.cart.controller;

import com.leyou.cart.pojo.Cart;
import com.leyou.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 要子康
 * @description CartController
 * @since 2020/7/26 10:35
 */
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 添加购物车
     *
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart){
        if (StringUtils.isEmpty(cart)){
            return ResponseEntity.ok().build();
        }
        this.cartService.addCart(cart);
        return ResponseEntity.ok().build();
    }

    /**
     * 查询购物车列表
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Cart>> queryCartList(){
        List<Cart> carts = this.cartService.queryCartList();
        if (carts == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(carts);
    }

    /**
     * 修改购物车列表
     * @param cart
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateNum(@RequestBody Cart cart){
        this.cartService.updateCarts(cart);
        return ResponseEntity.noContent().build();
    }

    /**
     * 删除购物车列表
     * @param skuId
     * @return
     */
    @DeleteMapping("{skuId}")
    public ResponseEntity<Void> deleteCart(@PathVariable("skuId") String skuId) {
        this.cartService.deleteCart(skuId);
        return ResponseEntity.ok().build();
    }
}
