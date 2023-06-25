//package com.example.dispatchcentre.feign;
//
//import com.example.customerservicecentre.beans.HttpResponseEntity;
//import com.example.customerservicecentre.entity.Good;
//import com.example.dispatchcentre.beans.HttpResponseEntity;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestMapping;
//
///**
// * @author YANG FUCHAO
// * @version 1.0
// * @date 2023-06-20 13:10
// */
//@Service
//@FeignClient(name = "distribution-application")
//public interface DistributionFeign {
//
//  @RequestMapping(value = "/good/create")
//  HttpResponseEntity addGoods(Good good);
//
//}
