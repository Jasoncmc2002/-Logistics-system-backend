package com.example.warehousemanagementcentre.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.warehousemanagementcentre.beans.HttpResponseEntity;
import com.example.warehousemanagementcentre.common.Constans;
import com.example.warehousemanagementcentre.entity.InStation;
import com.example.warehousemanagementcentre.service.InStationService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 入库 前端控制器
 * @author hzn
 * @create 2023-06-19 15:35
 */
@RestController
@RequestMapping("/warehouse/instation")
public class InStationAction {


    private final Logger logger = LoggerFactory.getLogger(InStationAction.class);
    @Autowired
    private InStationService inStationService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<InStation>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<InStation> aPage = inStationService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<InStation> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(inStationService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody InStation params) {
        inStationService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        inStationService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody InStation params) {
        inStationService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }

    @RequestMapping(value = "/getInStation",method = RequestMethod.GET, headers = "Accept"
            + "=application/json")
    public HttpResponseEntity getInStation(@RequestBody Map<String,Object> map){

        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            PageInfo pageInfo= inStationService.getInStation(map);
            httpResponseEntity.setData(pageInfo);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);


        } catch (Exception e) {
            logger.info("查入库类型为9的入库单”>>>>>"+e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;

    }

    @RequestMapping(value = "/updateInstation",method = RequestMethod.POST, headers = "Accept"
            + "=application/json")
    public HttpResponseEntity updateInstation(@RequestBody InStation params) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            int res=inStationService.updatebyId(params);
            if(res==1)
            {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }else
            {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.UPDATE_FAIL);
            }

        } catch (Exception e) {
            logger.info("updateUser 更新入库信息>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }




}
