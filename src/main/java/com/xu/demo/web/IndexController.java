package com.xu.demo.web;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.xu.demo.mapper.BotXMapper;
import com.xu.demo.pojo.dao.BotX;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xu
 * @version 1.0
 */
@RestController
public class IndexController {
    private Logger logger = LogManager.getLogger();

    @Autowired
    private BotXMapper botXMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Resource(name = "stringRedisTemplate")
    ValueOperations<String, String> valOpsStr;


    @RequestMapping(value = "/xuhua", method = RequestMethod.GET)
    public String xuhua() throws Exception {
        BotX botX = botXMapper.findById(1);
        logger.info(botX.getName());
        return botX.getName();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        System.out.println("index");
        return "index";
    }

    @RequestMapping(value = "/t1", method = RequestMethod.GET)
    public String redisT1() {
        valOpsStr.set("1234", "321");
        return valOpsStr.get("1234");
    }

    @RequestMapping(value = "/mt1", method = RequestMethod.GET)
    public void mongoT1() {
        String BASE_INFO_COLLECTION = "baseInfo";
        JSONObject json = new JSONObject();
        json.put("id", "123");
        mongoTemplate.insert(json, BASE_INFO_COLLECTION);
    }

    @RequestMapping(value = "/mt2", method = RequestMethod.GET)
    public long MongoT2() {
        String BASE_INFO_COLLECTION = "baseInfo";
        Query query = new Query(Criteria.where("id").is("123"));
        return mongoTemplate.count(query, BASE_INFO_COLLECTION);
    }

    @RequestMapping(value = "/mt3", method = RequestMethod.GET)
    public void MongoT3() {
        BotX x = new BotX();
        x.setId(111);
        x.setBot("doudou");
        mongoTemplate.save(x);
    }

    @RequestMapping(value = "/mt4", method = RequestMethod.GET)
    public BotX MongoT4() {
        BasicDBList andList = new BasicDBList();
        andList.add(new BasicDBObject("bot", "doudou"));
        BasicDBObject andDBObject = new BasicDBObject("$and", andList);

        return mongoTemplate.findOne(new BasicQuery(andDBObject), BotX.class);
    }

}
