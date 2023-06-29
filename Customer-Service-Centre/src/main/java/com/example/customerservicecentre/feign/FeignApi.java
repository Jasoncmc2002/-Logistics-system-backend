package com.example.customerservicecentre.feign;

import com.example.customerservicecentre.beans.HttpResponseEntity;
import com.example.customerservicecentre.entity.Buy;
import com.example.customerservicecentre.entity.Good;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
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
  HttpResponseEntity addGoods(Good good);

  @RequestMapping(value = "/distribute/good/update")
  HttpResponseEntity updateGoodByid(Good good);

  @RequestMapping(value = "/distribute/good/delete/{id}")
  HttpResponseEntity deleteGoodByid(@PathVariable("id") String id);

  @RequestMapping(value = "/distribute/good/{id}")
  HttpResponseEntity getGoodByid(@PathVariable("id")Long id);

  @RequestMapping(value = "/distribute/good/deleteBuyByGoodId")
  HttpResponseEntity deleteBuyByGoodid(Map<String,Object > map);

  @RequestMapping(value = "/distribute/good/updateBuyByGoodId")
  HttpResponseEntity updateBuyByid(Map<String,Object > map);
}
