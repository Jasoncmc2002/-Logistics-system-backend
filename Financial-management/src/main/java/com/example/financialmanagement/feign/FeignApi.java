package com.example.financialmanagement.feign;

import com.example.financialmanagement.beans.HttpResponseEntity;
import com.example.financialmanagement.entity.Good;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author YANG FUCHAO
 * @version 1.0
 * @date 2023-06-20 13:10
 */
@Service
@FeignClient(name = "api-gateway")
public interface FeignApi {

//  @RequestMapping(value = "/customer/getOrderByStationFin")
//  HttpResponseEntity getOrderByStationFin(@RequestParam Map<String,Object> map);

  @RequestMapping(value = "/customer/getOrderByStationFin")
  HttpResponseEntity getOrderByStationFin( Map<String,Object> map);

  @RequestMapping(value = "/distribute/good/getByOrderId")
  HttpResponseEntity getGoodByOrderId(Integer id);

  @RequestMapping(value = "/distribute/good/getByOrderId")
  HttpResponseEntity getBuyBySupplyFin(Map<String,Object> map);

  @RequestMapping(value = "/distribute/good/getByOrderId")
  HttpResponseEntity getGoodById(Integer id);

}
