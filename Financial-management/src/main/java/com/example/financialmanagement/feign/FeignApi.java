package com.example.financialmanagement.feign;

import com.example.financialmanagement.beans.HttpResponseEntity;
import com.example.financialmanagement.entity.Good;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author YANG FUCHAO
 * @version 1.0
 * @date 2023-06-20 13:10
 */
@Service
@FeignClient(name = "api-gateway")
public interface FeignApi {


  @RequestMapping(value = "/customer/getOrderByStationFin")
  HttpResponseEntity getOrderByStationFin(Map<String,Object> map);


  //To do
  @RequestMapping(value = "/distribute/good/create")
  HttpResponseEntity getGoodByOrderId(Integer id);

}
