package com.example.customerservicecentre.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.customerservicecentre.beans.HttpResponseEntity;
import com.example.customerservicecentre.common.Constans;
import com.example.customerservicecentre.entity.Orders;
import com.example.customerservicecentre.entity.Unsubscribe;
import com.example.customerservicecentre.service.UnsubscribeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 退订 前端控制器
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-19
 */
@RestController
@RequestMapping("/customer")
public class UnsubscribeAction {
    private final Logger logger = LoggerFactory.getLogger(CustomerAction.class);

    @Autowired
    private UnsubscribeService unsubscribeService;
/*    增加退订单*/
    @RequestMapping(value = "/addUnsubscribe",method = RequestMethod.POST, headers = "Accept"
        + "=application/json")
    public HttpResponseEntity addUnsubscribe(@RequestBody Unsubscribe params) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            int res=unsubscribeService.insert(params);
            if(res==1)
            {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }else
            {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.ADD_FAIL);
            }

        } catch (Exception e) {
            logger.info("addUnsubscribe 添加退订单>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }
/* 检查是否可以退订*/
    @RequestMapping(value = "/checkUnsubscribe",method = RequestMethod.POST, headers = "Accept"
        + "=application/json")
    public HttpResponseEntity checkUnsubscribe(@RequestBody Orders params) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            int res=unsubscribeService.check(params);
            if(res==1)
            {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }else
            {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.checkUnsubscribeAction);
            }

        } catch (Exception e) {
            logger.info("checkUnsubscribe 检查是否可以退订>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }
}
