package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 要子康
 * @description CategoryController
 * @since 2020/6/26 11:39
 */
@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoriesByPid(@RequestParam("pid") Long pid){
        if (pid == null || pid.longValue() < 0){
            return ResponseEntity.badRequest().build();
        }
        List<Category> categories = this.categoryService.queryCategoriesByPid(pid);
        if (CollectionUtils.isEmpty(categories)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categories);
    }

    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>> queryBrandById(@PathVariable("bid") Long bid){
        List<Category> list = this.categoryService.queryByBrandId(bid);
        if (list == null || list.size() < 1){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }

    @PutMapping("brand")
    public ResponseEntity<Void> updateBrandById(@RequestBody Category category){
        Integer update = this.categoryService.updateBrandById(category);
        if (update.equals(0) || update == 0){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("names")
    public ResponseEntity<List<String>> queryNamesByIds(@RequestParam("ids")List<Long> ids){
        List<String> names = this.categoryService.queryNamesByIds(ids);
        if (CollectionUtils.isEmpty(names)){
         return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(names);
    }

    /**
     * 根据3级分类id,查询1-3级的分类
     * @param id
     * @return
     */
    @GetMapping("all/level")
    public ResponseEntity<List<Category>> queryAllByCid3(@RequestParam("id")Long id){
        List<Category> list = this.categoryService.queryAllByCid3(id);
        if (list == null || list.size() < 1){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }

}
