package com.leyou.item.mapper;

import com.leyou.item.pojo.Category;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 要子康
 * @description CategoryMapper
 * @since 2020/6/26 11:31
 */
public interface CategoryMapper extends Mapper<Category>, SelectByIdListMapper<Category, Long> {

    @Select("SELECT c.* FROM tb_category c INNER JOIN tb_category_brand b ON c.id = b.category_id WHERE b.brand_id = #{bid}")
    List<Category> queryByBrandId(Long bid);
}
