package com.xu.demo.web;

import com.alibaba.fastjson.JSONObject;
import com.xu.demo.entity.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试 MongoDB 的功能
 */
@RestController
@RequestMapping("/mongo")
public class MongoController {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @GetMapping(value = "/test")
    public void mongoT1() {
        //对象存储
        String BASE_INFO_COLLECTION = "baseInfo";
        JSONObject json = new JSONObject();
        json.put("id", "123");
        mongoTemplate.insert(json, BASE_INFO_COLLECTION);

        Query query = new Query(Criteria.where("id").is("123"));
        Long count = mongoTemplate.count(query, BASE_INFO_COLLECTION);
        System.out.println("count=" + count);
    }

    @GetMapping(value = "/test2")
    public void MongoT2() {
        Info info = new Info();
        info.setId(111);
        info.setName("dou");
        mongoTemplate.save(info);
    }
}
