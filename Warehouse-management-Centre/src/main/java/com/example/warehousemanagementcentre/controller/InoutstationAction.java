package com.example.warehousemanagementcentre.controller;

import com.example.warehousemanagementcentre.beans.HttpResponseEntity;
import com.example.warehousemanagementcentre.common.Constans;
import com.example.warehousemanagementcentre.service.InoutstationService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 库房出入库 前端控制器
 * </p>
 *
 * @author hzn
 * @since 2023-07-07
 */
@RestController
@RequestMapping("/warehouse/inoutstation")
public class InoutstationAction {


    @Autowired
    private InoutstationService inoutstationService;

    private final Logger logger = LoggerFactory.getLogger(CentralStationController.class);


    @RequestMapping(value = "/selectBuyType",method = RequestMethod.POST, headers = "Accept"
            + "=application/json")
    public HttpResponseEntity selectBuyType(@RequestBody Map<String,Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            PageInfo res = inoutstationService.selectByType(map);
            httpResponseEntity.setData(res);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
        } catch (Exception e) {
            logger.info("search 按类型搜索出入库单>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }


}
