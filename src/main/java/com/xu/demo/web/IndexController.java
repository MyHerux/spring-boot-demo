//package com.xu.demo.web;
//
//import com.mongodb.BasicDBList;
//import com.mongodb.BasicDBObject;
//import io.swagger.annotations.*;
//import net.sf.json.JSONObject;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.BasicQuery;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @author xu
// * @version 1.0
// */
//@RestController
//public class IndexController {
//    private Logger logger = LogManager.getLogger();
//
//    @Autowired
//    private BotXMapper botXMapper;
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @Resource(name = "stringRedisTemplate")
//    ValueOperations<String, String> valOpsStr;
//
//    @ApiOperation(value = "获取信息")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "name", value = "姓名")
//    })
//    @ApiResponses({
//            @ApiResponse(code = 400, message = "请求失败")
//    })
//    @RequestMapping(value = "/xuhua", method = RequestMethod.GET)
//    public String xuhua() throws Exception {
//        BotX botX = botXMapper.findById(1);
//        logger.info(botX.getName());
//        return botX.getName();
//    }
//
//    @ApiOperation(value = "获取信息")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "name", value = "姓名")
//    })
//    @ApiResponses({
//            @ApiResponse(code = 400, message = "请求失败")
//    })
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public void index(HttpServletResponse response) throws IOException {
//        System.out.println("index");
//        response.getWriter().write("index");
//    }
//
//    @RequestMapping(value = "/t1", method = RequestMethod.GET)
//    public String redisT1() {
//        valOpsStr.set("1234", "321");
//        return valOpsStr.get("1234");
//    }
//
//    @RequestMapping(value = "/mt1", method = RequestMethod.GET)
//    public void mongoT1() {
//        String BASE_INFO_COLLECTION = "baseInfo";
//        JSONObject json = new JSONObject();
//        json.put("id", "123");
//        mongoTemplate.insert(json, BASE_INFO_COLLECTION);
//    }
//
//    @RequestMapping(value = "/mt2", method = RequestMethod.GET)
//    public long MongoT2() {
//        String BASE_INFO_COLLECTION = "baseInfo";
//        Query query = new Query(Criteria.where("id").is("123"));
//        return mongoTemplate.count(query, BASE_INFO_COLLECTION);
//    }
//
//    @RequestMapping(value = "/mt3", method = RequestMethod.GET)
//    public void MongoT3() {
//        BotX x = new BotX();
//        x.setId(111);
//        x.setBot("doudou");
//        mongoTemplate.save(x);
//    }
//
//    @RequestMapping(value = "/mt4", method = RequestMethod.GET)
//    public BotX MongoT4() {
//        BasicDBList andList = new BasicDBList();
//        andList.add(new BasicDBObject("bot", "doudou"));
//        BasicDBObject andDBObject = new BasicDBObject("$and", andList);
//
//        return mongoTemplate.findOne(new BasicQuery(andDBObject), BotX.class);
//    }
//
//}
