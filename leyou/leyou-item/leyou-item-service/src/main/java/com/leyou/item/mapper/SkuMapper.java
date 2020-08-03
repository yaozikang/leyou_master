package com.leyou.item.mapper;

import com.leyou.item.pojo.Sku;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 要子康
 * @description SkuMapper
 * @since 2020/7/5 14:53
 */
public interface SkuMapper extends Mapper<Sku> {

    /**
     * 根据SpuId查询sku
     * @param id
     * @return
     */
    @Select("SELECT id FROM tb_sku WHERE spu_id = #{id}")
    List<Long> selectBySpuId(Long id);
}
