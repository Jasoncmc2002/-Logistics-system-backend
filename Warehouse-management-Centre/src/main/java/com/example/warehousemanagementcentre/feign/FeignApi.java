package com.example.warehousemanagementcentre.feign;

import com.example.warehousemanagementcentre.beans.HttpResponseEntity;
import com.example.warehousemanagementcentre.entity.Buy;
import com.example.warehousemanagementcentre.entity.Good;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
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


  @RequestMapping(value = "/distribute/buy/{id}",method = RequestMethod.POST)
  HttpResponseEntity selectBuy(@PathVariable("id") String id);

//  @RequestMapping(value = "/distribute/good/{id}",method = RequestMethod.POST)
//  HttpResponseEntity getGood(@PathVariable("id") Long id);

  @RequestMapping(value = "/distribute/good/getGoodByGoodId1")
  HttpResponseEntity getGood(Map<String,Object> map);


  @RequestMapping(value = "/distribute/buy/update")
  HttpResponseEntity updateBuy(Buy buy);

  @RequestMapping(value = "/dispatch/changeTaskOrderType")
  HttpResponseEntity changeTaskOrderType(Map<String,Object> map);

//  @RequestMapping(value = "/distribute/good/getByOrderId")
//  HttpResponseEntity getByOrderId(Map<String, Object> map);

  @RequestMapping(value = "/distribute/good/{id}")
  HttpResponseEntity getGoodById(@PathVariable("id")String id);
}
