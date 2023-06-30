package com.example.financialmanagement.service.impl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YANG FUCHAO
 * @version 1.0
 * @date 2023-06-30 10:41
 */
public class test {

  public static void main(String[] args) {
    Map<String,Object> map=new HashMap<>();
    map.put("buyType","全部");
    System.out.println(map.get("buyType").equals("ss"));
    if(!(map.get("buyType")=="全部")){
      System.out.println("sss");
    }
  }
}
