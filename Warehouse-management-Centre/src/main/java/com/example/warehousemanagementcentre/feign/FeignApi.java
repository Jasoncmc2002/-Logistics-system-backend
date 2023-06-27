package com.example.warehousemanagementcentre.feign;

import com.example.warehousemanagementcentre.beans.HttpResponseEntity;
import com.example.warehousemanagementcentre.entity.Buy;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @author hzn
 * @version 1.0
 * @date 2023-06-20 13:10
 */
@Service
@FeignClient(name = "api-gateway")
public interface FeignApi {

  @RequestMapping(value = "/distribute/stationInOut/{id}")
  HttpResponseEntity getInStation(@PathVariable("id") Long id);


  @RequestMapping(value = "/distribute/buy/{id}")
  HttpResponseEntity selectBuy(@PathVariable("id") String id);

  @RequestMapping("/distribute/good/{id}")
  HttpResponseEntity getGood(@PathVariable("id") Long id);

  @RequestMapping("/distribute/buy/update")
  HttpResponseEntity updateBuy(Buy buy);

  @RequestMapping(value = "/dispatch/changeTaskOrderType")
  HttpResponseEntity changeTaskOrderType(Map<String,Object> map);
}
