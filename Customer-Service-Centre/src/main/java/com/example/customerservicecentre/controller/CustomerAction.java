package com.example.customerservicecentre.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.customerservicecentre.beans.HttpResponseEntity;
import com.example.customerservicecentre.common.Constans;
import com.example.customerservicecentre.entity.Customer;
import com.example.customerservicecentre.service.CustomerService;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;
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
 * 用户表信息 前端控制器
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-19
 */
@RestController
@RequestMapping("/customer")
public class CustomerAction {
    private final Logger logger = LoggerFactory.getLogger(CustomerAction.class);

    @Autowired
    private CustomerService customerService;


    @RequestMapping(value = "/addUser",method = RequestMethod.POST, headers = "Accept"
        + "=application/json")
    public HttpResponseEntity addUser(@RequestBody Customer params) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            int res=customerService.insert(params);
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
            logger.info("addUser 添加客户>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST, headers = "Accept"
        + "=application/json")
    public HttpResponseEntity updateUser(@RequestBody Customer params) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            int res=customerService.updatebyId(params);
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
    @RequestMapping(value = "/selectAllUser",method = RequestMethod.GET, headers = "Accept"
        + "=application/json")
    public HttpResponseEntity selectAllUser(@RequestBody Map<String,Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            PageInfo pageInfo= customerService.selectAll(map);
            httpResponseEntity.setData(pageInfo);
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);


        } catch (Exception e) {
            logger.info("updateUser 更新客户信息>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    @RequestMapping(value = "/delectUserByid",method = RequestMethod.POST, headers = "Accept"
        + "=application/json")
    public HttpResponseEntity delectUserByid(@RequestBody Customer params) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        System.out.println(params);
        try {
            int res = customerService.deletebyId(params.getId());
            if(res==1)
            {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
            }else
            {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage(Constans.DELETE_FAIL);
            }

        } catch (Exception e) {
            logger.info("delectUserByid 删除客户信息>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }
}
