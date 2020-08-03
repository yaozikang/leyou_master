package com.leyou.controller;

import com.leyou.item.pojo.SpuDetail;
import com.leyou.service.GoodsHtmlService;
import com.leyou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author 要子康
 * @description GoodsController
 * @since 2020/7/14 18:13
 */
@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsHtmlService goodsHtmlService;

    /**
     * 跳转到商品详情页
     * @param model
     * @param id
     * @return
     */
    @GetMapping("item/{id}.html")
    public String toItemPage(@PathVariable("id") Long id, Model model){

        //加载所需的数据
        Map<String, Object> modelMap = this.goodsService.loadModel(id);

        // 把数据放入数据模型
        model.addAllAttributes(modelMap);

        //页面静态化
        this.goodsHtmlService.asyncExcute(id);

        return "item";
    }
}
