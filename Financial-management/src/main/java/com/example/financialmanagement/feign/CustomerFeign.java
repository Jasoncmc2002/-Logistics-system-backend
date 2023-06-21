package com.example.financialmanagement.feign;

import com.example.financialmanagement.entity.Orders;
import com.example.financialmanagement.entity.Return;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author YANG FUCHAO
 * @version 1.0
 * @date 2023-06-20 10:58
 */
@FeignClient(name = "customer-application")
public interface CustomerFeign {

  @RequestMapping(value = "/user/a123")
  List<Orders> getorders(Map<String,Object> map);

  @RequestMapping(value = "/user/a123")
  List<Return> getreturn(Map<String,Object> map);

}
