package com.leyou.item.service;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 要子康
 * @description CategoryService
 * @since 2020/6/26 11:48
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> queryCategoriesByPid(Long pid){
        Category category = new Category();
        category.setParentId(pid);
        return this.categoryMapper.select(category);
    }

    public List<Category> queryByBrandId(Long bid){
        return this.categoryMapper.queryByBrandId(bid);
    }

    public Integer updateBrandById(Category category) {
        return this.categoryMapper.updateByPrimaryKey(category);
    }

    public List<String> queryNamesByIds(List<Long> ids){
        List<Category> list = this.categoryMapper.selectByIdList(ids);
        List<String> names = new ArrayList<>();
        for (Category category : list){
            names.add(category.getName());
        }
        return names;
    }

    public List<Category> queryAllByCid3(Long id){
        Category c3 = this.categoryMapper.selectByPrimaryKey(id);
        Category c2 = this.categoryMapper.selectByPrimaryKey(c3.getParentId());
        Category c1 = this.categoryMapper.selectByPrimaryKey(c2.getParentId());
        return Arrays.asList(c1, c2, c3);
    }

}
