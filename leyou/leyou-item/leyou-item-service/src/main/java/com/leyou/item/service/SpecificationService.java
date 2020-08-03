package com.leyou.item.service;

import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 要子康
 * @description SpecificationService
 * @since 2020/6/28 18:38
 */
@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     * 根据分类id查询分组
     * @param cid
     * @return
     */
    public List<SpecGroup> queryGroupByCid(Long cid){
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        return this.specGroupMapper.select(specGroup);
    }

    /**
     * 添加分组
     * @param specGroup
     * @return
     */
    public Integer saveGroup(SpecGroup specGroup){
        return this.specGroupMapper.insert(specGroup);
    }

    /**
     * 修改分组
     * @param specGroup
     * @return
     */
    public Integer updateGroup(SpecGroup specGroup){
        return this.specGroupMapper.updateByPrimaryKey(specGroup);
    }

    /**
     * 根据分类id删除分组
     * @param id
     * @return
     */
    public Integer deleteGroupById(Long id){
        return this.specGroupMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据条件查询规格参数
     * @param gid
     * @return
     */
    public List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching) {
        SpecParam record = new SpecParam();
        record.setGroupId(gid);
        record.setGeneric(generic);
        record.setCid(cid);
        record.setSearching(searching);
        return this.specParamMapper.select(record);
    }

    /**
     * 添加规格参数
     * @param specParam
     * @return
     */
    public Integer saveParam(SpecParam specParam){
        return this.specParamMapper.insert(specParam);
    }

    /**
     * 修改规格参数
     * @param specParam
     * @return
     */
    public Integer updateParam(SpecParam specParam){
        return this.specParamMapper.updateByPrimaryKey(specParam);
    }

    /**
     * 根据id删除规格参数
     * @param id
     * @return
     */
    public Integer deleteParamById(Long id){
        return this.specParamMapper.deleteByPrimaryKey(id);
    }

    public List<SpecGroup> querySpecsByCid(Long cid){
        // 查询规格组
        List<SpecGroup> groups = this.queryGroupByCid(cid);
        groups.forEach(g -> {
            // 查询组内参数
            g.setParams(this.queryParams(g.getId(), null, null, null));
        });
        return groups;
    }
}
