package com.leyou.search.responsitory;

import com.leyou.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author 要子康
 * @description GoodsRepository
 * @since 2020/7/10 11:34
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods, Long>{
}