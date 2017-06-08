package com.xu.demo.web;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试 Redis 的功能
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    private final StringRedisTemplate redisTemplate;

    @Autowired
    public RedisController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @ApiOperation(value = "测试Redis")
    @GetMapping(value = "/set")
    public void set() {
        // 保存字符串
        redisTemplate.opsForValue().set("aaa", "111");

        redisTemplate.opsForValue().get("aaa");

        redisTemplate.opsForSet().add("test_set", "1111");
        redisTemplate.opsForSet().add("test_set", "2222");
    }

}
