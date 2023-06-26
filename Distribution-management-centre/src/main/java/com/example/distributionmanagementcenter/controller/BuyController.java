package com.example.distributionmanagementcenter.controller;

import com.example.distributionmanagementcenter.entity.*;
import com.example.distributionmanagementcenter.service.BuyService;
import com.example.distributionmanagementcenter.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jason_cai
 * @since 2023-06-19
 */
@RestController
@RequestMapping("/buy")
public class BuyController {

    private final Logger logger = LoggerFactory.getLogger(BuyController.class);
    @Autowired
    private BuyService buyService;



    @GetMapping(value = "/{id}")
    public HttpResponseEntity<Buy> getById(@PathVariable("id") String id) {
        HttpResponseEntity<Buy> httpResponseEntity = new HttpResponseEntity<Buy>();
        try {
           Buy buy=buyService.getById(id);
            if(buy!=null)
            {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }else
            {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.ADD_FAIL);
            }

        } catch (Exception e) {
            logger.info("getById ID查找订单>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }
    @PostMapping(value = "/create")
    public HttpResponseEntity<Buy> create(@RequestBody Buy params) {
        HttpResponseEntity<Buy> httpResponseEntity = new HttpResponseEntity<Buy>();
        try {
            boolean flag=buyService.save(params);
            if(flag)
            {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }else
            {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.ADD_FAIL);
            }

        } catch (Exception e) {
            logger.info("create 新建中心库房库存量>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }
//
//    @PostMapping(value = "/delete/{id}")
//    public HttpResponseEntity<Buy> delete(@PathVariable("id") String id) {
//
//        HttpResponseEntity<Category> httpResponseEntity = new HttpResponseEntity<Category>();
//        try {
//            boolean flag=categoryService.removeById(id);
//            if(flag)
//            {
//                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
//                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
//            }else
//            {
//                httpResponseEntity.setCode(Constans.EXIST_CODE);
//                httpResponseEntity.setMessage(Constans.ADD_FAIL);
//            }
//
//        } catch (Exception e) {
//            logger.info("delete 删除种类>>>>>>>>>>>" + e.getLocalizedMessage());
//            httpResponseEntity.setCode(Constans.EXIST_CODE);
//            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
//        }
//        return httpResponseEntity;
//    }
//
//
    @PostMapping(value = "/update")
    public HttpResponseEntity<Buy> update(@RequestBody Buy params) {

        HttpResponseEntity<Buy> httpResponseEntity = new HttpResponseEntity<Buy>();
        try{
            boolean flag=buyService.updateById(params);
            if(flag)
            {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }else
            {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.ADD_FAIL);
            }

        } catch (Exception e) {
            logger.info("update 更新购货单单>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }
}
