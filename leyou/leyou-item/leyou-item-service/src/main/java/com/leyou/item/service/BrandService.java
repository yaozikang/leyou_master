package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import com.netflix.discovery.converters.jackson.EurekaXmlJacksonCodec;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author 要子康
 * @description BrandService
 * @since 2020/6/26 16:08
 */
@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 根据查询条件分页并排序查询品牌信息
     *
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc){

        // 初始化example对象
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        // 根据name模糊查询，或者根据首字母查询
        if (StringUtils.isNotBlank(key)){
            criteria.andLike("name", "%" + key + "%").orEqualTo("letter", key);
        }

        // 添加分页条件
        PageHelper.startPage(page, rows);

        // 添加排序条件
        if (StringUtils.isNotBlank(sortBy)){
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }

        // 包装成pageInfo
        List<Brand> brands = this.brandMapper.selectByExample(example);

        // 包装成分页结果集返回
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 根据Id 查询单个品牌
     * @param id
     * @return
     */
    public Brand queryBrandById(Long id){
        return this.brandMapper.selectByPrimaryKey(id);
    }

    /**
     * 新增品牌
     *
     * @param brand
     * @param cids
     */
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids){
        this.brandMapper.insert(brand);

        cids.forEach(cid -> {
            this.brandMapper.insertCategoryAndBrand(cid, brand.getId());
        });
    }

    /**
     * 更新品牌
     *
     * @param brand
     * @param cids
     */
    @Transactional
    public void updateBrand(Brand brand, List<Long> cids){
        this.brandMapper.updateByPrimaryKey(brand);
        this.brandMapper.deleteCategoryAndBrand(brand.getId());
        cids.forEach(cid -> {
            this.brandMapper.insertCategoryAndBrand(cid, brand.getId());
        });
    }

    /**
     * 删除品牌
     * @param id
     */
    @Transactional
    public Integer deleteBrandById(Long id){
        this.brandMapper.deleteByPrimaryKey(id);
        return this.brandMapper.deleteCategoryAndBrand(id);
    }

    /**
     * 查找品牌集合
     * @param cid
     * @return
     */
    public List<Brand> queryBrandsByCid(Long cid){
        return this.brandMapper.selectBrandByCid(cid);
    }
}