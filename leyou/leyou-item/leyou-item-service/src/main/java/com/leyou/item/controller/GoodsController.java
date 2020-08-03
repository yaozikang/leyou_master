package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuBo;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.service.GoodsService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 要子康
 * @description GoodsController
 * @since 2020/7/4 11:27
 */
@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuBoByPage(
            @RequestParam(value = "key", required = false)String key,
            @RequestParam(value = "saleable", required = false)Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "rows", defaultValue = "5")Integer rows
    ){
        PageResult<SpuBo> pageResult = this.goodsService.querySpuBoByPage(key, saleable, page, rows);
        if (CollectionUtils.isEmpty(pageResult.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pageResult);
    }

    @PostMapping("querySpuBoByPage")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuBo spuBo){
        this.goodsService.saveGoods(spuBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("spu/detail/{spuId}")
    public ResponseEntity<SpuDetail> querySpuDetailBySpuId(@PathVariable("spuId") Long spuId){
        SpuDetail spuDetail = this.goodsService.querySpuDetailBySpuId(spuId);
        if (spuDetail == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(spuDetail);
    }

    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("id")Long spuId){
        List<Sku> skus = this.goodsService.querySkusBySpuId(spuId);
        if (skus == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(skus);
    }

    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody SpuBo spuBo){
        this.goodsService.updateGoods(spuBo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("spu/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable("id") Long id){
        this.goodsService.deleteGoodsBySpuId(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("spu/saleable")
    public ResponseEntity<Void> goodShelve(
            @RequestParam(value = "id") Long id,
            @RequestParam(value = "saleable") Boolean saleable){

        Integer sale = (saleable == true ? 1 : 0);

        Boolean isShelve = this.goodsService.goodShelve(id, sale);
        System.out.println(isShelve);

        if(!isShelve){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("spu/{id}")
    public ResponseEntity<Spu> querySpuBySpuId(@PathVariable("id") Long id){
        Spu spu = this.goodsService.querySpuBySpuId(id);
        if (spu == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(spu);
    }

    @GetMapping("sku/{id}")
    public ResponseEntity<Sku> querySkuBySkuId(@PathVariable("id")Long id){
        Sku sku = this.goodsService.querySkuBySkuId(id);
        if (sku == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(sku);
    }
}
