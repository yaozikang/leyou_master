package com.leyou.search.responsitory;

import com.leyou.LeyouSearchApplication;
import com.leyou.search.pojo.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeyouSearchApplication.class)
public class ElasticSearchTest {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void createIndex(){
        this.elasticsearchTemplate.createIndex(Goods.class);
        this.elasticsearchTemplate.putMapping(Goods.class);
        //System.out.println(this.elasticsearchTemplate.getMapping(Goods.class));
    }
}