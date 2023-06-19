package com.example.user;

import com.example.user.mapper.UserMapper;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserApplicationTests {

  @Autowired
  UserMapper mapper;

  @Test
  void contextLoads() {
    HashMap<String, Object> map = new HashMap<>();
    map.put("username","…€Ï≈√˜");
    System.out.println(mapper.selectByMap(map));
  }
  @Test
  void contextLoads1() {

    System.out.println(mapper.getAllUser());
  }

  @Test
  void contextLoads2() {

    System.out.println(mapper.selectById(1L));
  }
}
