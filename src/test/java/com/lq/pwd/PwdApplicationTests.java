package com.lq.pwd;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class PwdApplicationTests {

    @Autowired
    RedisTemplate<String,String> redisTemplate;
    //@Test
    void contextLoads() {
    }

    @Test
    public void redisTest(){
        System.out.println(redisTemplate.opsForValue().get("pwd_invite_code"));
    }


}
