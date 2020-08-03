package com.leyou.item.controller;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 要子康
 * @description SpecificationController
 * @since 2020/6/28 18:37
 */
@Controller
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupByCid(@PathVariable("cid") Long cid){
        List<SpecGroup> params = this.specificationService.queryGroupByCid(cid);
        if (CollectionUtils.isEmpty(params)){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(params);
    }

    /**
     * 添加分组
     * @param specGroup
     * @return
     */
    @PostMapping("group")
    public ResponseEntity<Void> saveGroup(@RequestBody SpecGroup specGroup){
        Integer saveGroup = this.specificationService.saveGroup(specGroup);

        if (saveGroup == 0 || saveGroup.equals(0)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改分组
     * @param specGroup
     * @return
     */
    @PutMapping("group")
    public ResponseEntity<Void> updateGroup(@RequestBody SpecGroup specGroup){
        Integer updateGroup = this.specificationService.updateGroup(specGroup);

        if (updateGroup == 0 || updateGroup.equals(0)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据id删除分组
     * @param id
     * @return
     */
    @DeleteMapping("group/{id}")
    public ResponseEntity<Void> deleteGroupById(@PathVariable("id") Long id){
        Integer delete = this.specificationService.deleteGroupById(id);
        if (delete == 0 || delete.equals(0)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据条件查询规格参数
     * @param gid
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParams(
            @RequestParam(value = "gid", required = false)Long gid,
            @RequestParam(value = "cid", required = false)Long cid,
            @RequestParam(value = "generic", required = false)Boolean generic,
            @RequestParam(value = "searching", required = false)Boolean searching
            ){
        List<SpecParam>  params = this.specificationService.queryParams(gid, cid, generic, searching);
        if (CollectionUtils.isEmpty(params)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(params);
    }

    /**
     * 添加规格参数
     * @param specParam
     * @return
     */
    @PostMapping("param")
    public ResponseEntity<Void> saveParam(@RequestBody SpecParam specParam){

        Integer saveParam = this.specificationService.saveParam(specParam);
        if (saveParam == 0 || saveParam.equals(0)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据id删除规格参数
     * @param pid
     * @return
     */
    @DeleteMapping("param/{pid}")
    public ResponseEntity<Void> deleteParamById(@PathVariable("pid") Long pid){
        Integer deleteParam = this.specificationService.deleteParamById(pid);
        if (deleteParam == 0 || deleteParam.equals(0)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改规格参数
     * @param specParam
     * @return
     */
    @PutMapping("param")
    public ResponseEntity<Void> updateParam(@RequestBody SpecParam specParam){
        Integer updateParam = this.specificationService.updateParam(specParam);

        if (updateParam == 0 || updateParam.equals(0)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据Cid查询商品规格
     * @param cid
     * @return
     */
    @GetMapping("{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecsByCid(@PathVariable("cid") Long cid){
        List<SpecGroup> list = this.specificationService.querySpecsByCid(cid);
        if(list == null || list.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }
}
