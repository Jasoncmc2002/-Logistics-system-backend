package com.example.Feign;

/**
 * @author YANG FUCHAO
 * @version 1.0
 * @date 2023-06-16 14:52
 */

import com.example.entity.me;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "user-application")
public interface UserClient {
  @RequestMapping(value = "/user/aa")

  ResponseEntity<Object>aa();

  @RequestMapping(value = "/user/a123")
  me a1();

}

