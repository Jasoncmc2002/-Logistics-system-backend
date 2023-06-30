package com.example.financialmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class FinancialManagementApplicationTests {

  @Autowired
  private RedisTemplate redisTemplate;
  @Test
  void contextLoads() {
    redisTemplate.opsForValue().set("user", "user");
  }

}
