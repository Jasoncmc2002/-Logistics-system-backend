package com.example.customerservicecentre.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.customerservicecentre.beans.HttpResponseEntity;
import com.example.customerservicecentre.common.Constans;
import com.example.customerservicecentre.entity.Orders;
import com.example.customerservicecentre.entity.Return;
import com.example.customerservicecentre.service.ReturnService;
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
public class ReturnAction {
    private final Logger logger = LoggerFactory.getLogger(CustomerAction.class);

    @Autowired
    private ReturnService returnService;

    @RequestMapping(value = "/addReturn",method = RequestMethod.POST, headers = "Accept"
        + "=application/json")
    public HttpResponseEntity addReturn(@RequestBody Return params) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            int res=returnService.insert(params);
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
            logger.info("addReturn 添加退换货单>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    @RequestMapping(value = "/checkReturn",method = RequestMethod.POST, headers = "Accept"
        + "=application/json")
    public HttpResponseEntity checkReturn(@RequestBody Orders params) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            int res=returnService.check(params);
            if(res==1)
            {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }else if (res==0)
            {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.checkRETURN);
            }

        } catch (Exception e) {
            logger.info("checkReturn 检查是否可以退换货>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }
}
