package com.example.substationmanagementcenter.feign;

import com.example.substationmanagementcenter.beans.HttpResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;

/**
 * @author hzn
 * @version 1.0
 * @date 2023-06-20 13:10
 */
@Service
@FeignClient(name = "api-gateway")
public interface FeignApi {

  @RequestMapping(value = "/customer/getOrderByid")
  HttpResponseEntity getByOrderId(Map<String, Object> map);


}
