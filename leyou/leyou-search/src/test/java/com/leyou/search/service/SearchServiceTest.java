package com.leyou.search.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuBo;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.responsitory.GoodsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.ElasticsearchException;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SearchServiceTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private SearchService searchService;

    @Autowired
    private GoodsClient goodsClient;

    @Test
    public void buildGoods() {
        this.elasticsearchTemplate.createIndex(Goods.class);
        this.elasticsearchTemplate.putMapping(Goods.class);
        Integer page = 1;
        Integer rows = 100;

        do {
            PageResult<SpuBo> pageResult = this.goodsClient.querySpuBoByPage(null, true, page, rows);
            List<Goods> goodsList = pageResult.getItems().stream().map(spuBo -> {
                try{
                    return this.searchService.buildGoods((Spu)spuBo);
                }catch (IOException e){
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
            this.goodsRepository.saveAll(goodsList);
            rows = pageResult.getItems().size();
            page++;
        }while (rows == 100);
    }
}