package com.example.warehousemanagementcentre.feign;

import com.example.warehousemanagementcentre.beans.HttpResponseEntity;
import com.example.warehousemanagementcentre.entity.InStation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hzn
 * @since 2023-06-21
 */
@Service
@FeignClient(name = "distribution-application")
public interface DistributionFeign {

  @RequestMapping(value = "/stationInOut/{id}")
  HttpResponseEntity getInStation(@PathVariable("id") Long id);

}
