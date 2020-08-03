package com.leyou.item.api;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 要子康
 * @description SpecificationApi
 * @since 2020/7/10 10:56
 */
@RequestMapping("spec")
public interface SpecificationApi {

    /**
     *
     * @param gid
     * @param cid
     * @param generic
     * @param searching
     * @return
     */
    @GetMapping("params")
    List<SpecParam> queryParams(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "generic", required = false) Boolean generic,
            @RequestParam(value = "searching", required = false) Boolean searching
    );

    @GetMapping("groups/{cid}")
    ResponseEntity<List<SpecGroup>> querySpecGroups(@PathVariable("cid") Long cid);

    /**
     * 查询规格参数组，及组内参数
     * 接口
     * 根据Cid查询规格
     * @param cid
     * @return
     */
    @GetMapping("{cid}")
    List<SpecGroup> querySpecsByCid(@PathVariable("cid") Long cid);

}
