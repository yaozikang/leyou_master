package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 要子康
 * @description BrandController
 * @since 2020/6/26 15:54
 */
@Controller
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 根据查询条件分页并排序查询品牌信息
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandsByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", required = false) Boolean desc){
        PageResult<Brand> result = this.brandService.queryBrandsByPage(key, page, rows, sortBy, desc);
        if (CollectionUtils.isEmpty(result.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 新增品牌
     * @param brand
     * @param cids
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids") List<Long> cids){
        this.brandService.saveBrand(brand, cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateBrand(Brand brand, @RequestParam("cids") List<Long> cids){
        this.brandService.updateBrand(brand, cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBrandById(@PathVariable("id") Long id){
        Integer deleteBrand = this.brandService.deleteBrandById(id);

        if (deleteBrand == 0 || deleteBrand.equals(0)){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandsByCid(@PathVariable("cid") Long cid){

        List<Brand> brands = this.brandService.queryBrandsByCid(cid);

        if (CollectionUtils.isEmpty(brands)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brands);
    }

    @GetMapping("{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id") Long id){
        Brand brand = this.brandService.queryBrandById(id);

        if (brand == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brand);
    }
}
