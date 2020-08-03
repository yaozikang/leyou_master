package com.leyou.item.mapper;

import com.leyou.item.pojo.Spu;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author 要子康
 * @description SpuMapper
 * @since 2020/7/4 11:12
 */
public interface SpuMapper extends Mapper<Spu> {

    /**
     * 修改商品上架状态
     * @param id
     * @param saleable
     * @return
     */
    @Update("UPDATE tb_spu SET saleable = #{saleable} WHERE id = #{id}")
    Integer updateSaleableBySpuId(Long id, Integer saleable);

    /**
     * 查询商品上架状态
     * @param id
     * @return
     */
    @Select("SELECT saleable FROM tb_spu WHERE id = #{id}")
    Integer selectSaleableBySpuId(Long id);
}