package com.example.financialmanagement.feign;

import com.example.financialmanagement.entity.Buy;
import com.example.financialmanagement.entity.Orders;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author YANG FUCHAO
 * @version 1.0
 * @date 2023-06-20 13:10
 */
@FeignClient(name = "distribution-application")
public interface DistributionFeign {

  @RequestMapping(value = "/user/a123")
  List<Buy> getbuys(Map<String,Object> map);

}
