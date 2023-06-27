package com.example.warehousemanagementcentre.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.warehousemanagementcentre.beans.HttpResponseEntity;
import com.example.warehousemanagementcentre.common.Constans;
import com.example.warehousemanagementcentre.entity.CentralStation;
import com.example.warehousemanagementcentre.service.CentralstationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 中心库房存量  前端控制器
 * </p>
 *
 * @author hzn
 * @since 2023-06-25
 */
@RestController
@RequestMapping("/centralstation")
public class CentralstationAction {


    private final Logger logger = LoggerFactory.getLogger(CentralstationAction.class);
    @Autowired
    private CentralstationService centralstationService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<CentralStation>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<CentralStation> aPage = centralstationService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public HttpResponseEntity getById(@PathVariable("id") String id) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            CentralStation centralStation=centralstationService.getById(id);
            if(centralStation!=null)
            {
                httpResponseEntity.setData(centralStation);
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }else
            {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.ADD_FAIL);
            }

        } catch (Exception e) {
            logger.info("getById ID查找仓库内容>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody CentralStation params) {
        centralstationService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        centralstationService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody CentralStation params) {
        centralstationService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }


    @RequestMapping(value = "/updateCentralStation",method = RequestMethod.POST, headers = "Accept"
            + "=application/json")
    public HttpResponseEntity updateCentralStation(@RequestBody CentralStation params) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            int res=centralstationService.updatebyId(params);
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
            logger.info("updateUser 更新客户信息>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }



}
