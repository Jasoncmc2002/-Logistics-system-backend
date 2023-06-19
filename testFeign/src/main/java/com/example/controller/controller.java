package com.example.controller;

import com.example.Feign.UserClient;
import com.example.entity.me;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YANG FUCHAO
 * @version 1.0
 * @date 2023-06-16 15:00
 */
@RestController
public class controller {
  @Autowired
  private UserClient fe;

  @RequestMapping("get")
  public  ResponseEntity<Object> aa() {
    return fe.aa();
  }

  @RequestMapping("add")
  public me add() {
//    String aaa= fe.a();
//    System.out.println(aaa);
    System.out.println(fe.a1());
    return fe.a1();
  }
}
