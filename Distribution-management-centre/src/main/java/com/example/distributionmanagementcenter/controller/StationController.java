package com.example.distributionmanagementcenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.distributionmanagementcenter.entity.CentralStation;
import com.example.distributionmanagementcenter.entity.Constans;
import com.example.distributionmanagementcenter.entity.HttpResponseEntity;
import com.example.distributionmanagementcenter.entity.Station;
import com.example.distributionmanagementcenter.service.CentralstationService;
import com.example.distributionmanagementcenter.service.StationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 库房 前端控制器
 * </p>
 *
 * @author Jason_cai
 * @since 2023-06-19
 */
@RestController
@RequestMapping("/station")
public class StationController {

    private final Logger logger = LoggerFactory.getLogger(StationController.class);
    @Autowired
    private StationService stationService;
    @Autowired
    private CentralstationService centralstationService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<Station>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<Station> aPage = stationService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Station> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(stationService.getById(id), HttpStatus.OK);
    }


    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody Station params) {
        stationService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        stationService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody Station params) {
        stationService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
//    @PostMapping(value = "/check")
//    public ResponseEntity<Object> checkGoods(@RequestBody List<String> goodNames) {
//
//        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
//    }
@RequestMapping(value = "/stock/{sid}/{cid}",method = RequestMethod.GET, headers = "Accept"
        + "=application/json")
public HttpResponseEntity stockQuery(@PathVariable("sid") String sid,@PathVariable("cid") String cid) {
    HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
    try {
        Station station = stationService.getById(sid);
        CentralStation centralStation= centralstationService.getById(cid);
        HashMap<String,Object> responseContent=new HashMap<String,Object>();

        responseContent.put("StationName",station.getName());
        responseContent.put("GoodName",centralStation.getGoodName());
        int allStock=centralStation.getWaitAllo()+centralStation.getWithdrawal()+centralStation.getDoneAllo();
        responseContent.put("AllStock",allStock);
        responseContent.put("Withdrawal",centralStation.getWithdrawal());
        responseContent.put("DoneAllo",centralStation.getDoneAllo());
        responseContent.put("WaitAllo",centralStation.getWaitAllo());
        httpResponseEntity.setData(responseContent);
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);


    } catch (Exception e) {
        logger.info("stockQuery 库存量查询>>>>>>>>>>>" + e.getLocalizedMessage());
        httpResponseEntity.setCode(Constans.EXIST_CODE);
        httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
    }
    return httpResponseEntity;
}

    @RequestMapping(value = "/stationInOut/{sid}/{gid}",method = RequestMethod.POST, headers = "Accept"
            + "=application/json")
    public HttpResponseEntity StationInOutQuery(@PathVariable("sid") String sid, @PathVariable("gid") String gid, @RequestBody Map<String, Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            Station station = stationService.getById(sid);
            HashMap<String,Object> responseContent=new HashMap<String,Object>();
            responseContent.put("StationName",station.getName());
            responseContent.put("RecordList",stationService.stationInOutQueryService(map));
            httpResponseEntity.setData(responseContent);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);


        } catch (Exception e) {
            logger.info("StationInOutQuery 出入库单查询>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }
}
