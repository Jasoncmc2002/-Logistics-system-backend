//package com.example.warehousemanagementcentre.controller;
//
//import com.example.warehousemanagementcentre.common.Constans;
//import com.example.warehousemanagementcentre.entity.Station;
//import com.example.warehousemanagementcentre.service.StationService;
//import com.github.pagehelper.PageInfo;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import com.example.warehousemanagementcentre.beans.HttpResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
///**
// * <p>
// * 库房出库 前端控制器
// * </p>
// *
// * @author hzn
// * @since 2023-06-21
// */
//@RestController
//@RequestMapping("/station")
//public class StationAction {
//
//
//    private final Logger logger = LoggerFactory.getLogger(StationAction.class);
//    @Autowired
//    private StationService stationService;
//
//    @RequestMapping(value = "/GetContentOfCentralWarehouse",method = RequestMethod.GET, headers = "Accept"
//            + "=application/json")
//    public HttpResponseEntity GetContentOfCentralWarehouse(@RequestBody Map<String,Object> map) {
//        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
//        try {
//            PageInfo pageInfo= stationService.selectAll(map);
//            httpResponseEntity.setData(pageInfo);
//            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
//            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
//
//
//        } catch (Exception e) {
//            logger.info("selectAll 展示仓库物品信息>>>>>>>>>>>" + e.getLocalizedMessage());
//            httpResponseEntity.setCode(Constans.EXIST_CODE);
//            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
//        }
//        return httpResponseEntity;
//    }
//
////    @RequestMapping(value = "/getInStation",method = RequestMethod.GET, headers = "Accept"
////            + "=application/json")
////    public HttpResponseEntity getInStation(@RequestBody Map<String,Object> map){
////        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
////        try {
////            PageInfo pageInfo= stationService.getInStation(map);
////            httpResponseEntity.setData(pageInfo);
////            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
////            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
////
////
////        } catch (Exception e) {
////            logger.info("selectAll 展示仓库物品信息>>>>>>>>>>>" + e.getLocalizedMessage());
////            httpResponseEntity.setCode(Constans.EXIST_CODE);
////            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
////        }
////        return httpResponseEntity;
////    }
//
//    @RequestMapping(value = "/updateStationById",method = RequestMethod.POST, headers = "Accept"
//            + "=application/json")
//    public HttpResponseEntity updateStation(@RequestBody Station params) {
//        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
//        try {
//            int res=stationService.updatebyId(params);
//            if(res==1)
//            {
//                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
//                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
//            }else
//            {
//                httpResponseEntity.setCode(Constans.EXIST_CODE);
//                httpResponseEntity.setMessage(Constans.UPDATE_FAIL);
//            }
//
//        } catch (Exception e) {
//            logger.info("updateStation 更新仓库商品信息>>>>>>>>>>>" + e.getLocalizedMessage());
//            httpResponseEntity.setCode(Constans.EXIST_CODE);
//            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
//        }
//        return httpResponseEntity;
//    }
//
//
//
//
//
//}
