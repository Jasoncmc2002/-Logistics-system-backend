package com.example.financialmanagement.feign;

import com.example.customerservicecentre.beans.HttpResponseEntity;
import com.example.customerservicecentre.entity.Good;
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

  @RequestMapping(value = "/distribute/good/create")
  HttpResponseEntity getGoodsByIdDate(Map<String,Object> map);


  @RequestMapping(value = "/distribute/good/create")
  HttpResponseEntity getPriceById(Long id);

  @RequestMapping(value = "/distribute/good/create")
  HttpResponseEntity getbuysById(Long id);

}
