package com.example.dispatchcentre.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dispatchcentre.beans.HttpResponseEntity;
import com.example.dispatchcentre.common.Constans;
import com.example.dispatchcentre.entity.Task;
import com.example.dispatchcentre.service.TaskService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 任务单 前端控制器
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-25
 */
@RestController
@RequestMapping("/dispatch")
public class TaskAction {
    private final Logger logger = LoggerFactory.getLogger(TaskAction.class);

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/addTask",method = RequestMethod.POST, headers = "Accept"
        + "=application/json")
    public HttpResponseEntity addUser(@RequestBody Map<String,Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            int res=taskService.insert(map);
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
            logger.info("addTask 添加订单>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    @RequestMapping(value = "/getTasksByCriteria",method = RequestMethod.POST, headers = "Accept"
        + "=application/json")
    public HttpResponseEntity getOrdersByCriteria(@RequestBody Map<String,Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            PageInfo res=taskService.searchbykey(map);
            httpResponseEntity.setData(res);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);

        } catch (Exception e) {
            logger.info("getTasksByCriteria 搜索订单>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    @RequestMapping(value = "/changeTaskOrderType",method = RequestMethod.POST, headers = "Accept"
        + "=application/json")
    public HttpResponseEntity changeTaskOrderType(@RequestBody Map<String,Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            int res=taskService.changeTaskOrderType(map);
            httpResponseEntity.setData(res);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);

        } catch (Exception e) {
            logger.info("changeTaskOrderType 根据id，改变task和order的>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    @RequestMapping(value = "/selectByDate",method = RequestMethod.POST, headers = "Accept"
        + "=application/json")
    public HttpResponseEntity selectByDate(@RequestBody Map<String,Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        try {
            httpResponseEntity.setData(taskService.selectByDate(map));
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);

        } catch (Exception e) {
            logger.info("changeTaskOrderType 根据id，改变task和order的>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }
    @RequestMapping(value = "/getGoodListByOrderId",method = RequestMethod.POST, headers = "Accept"
        + "=application/json")
    public HttpResponseEntity getGoodListByTaskId(@RequestBody Map<String, Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        try {
            httpResponseEntity.setData(taskService.getGoodListByTaskId(map));
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);

        } catch (Exception e) {
            logger.info("changeTaskOrderType 根据id，改变task和order的>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }
}
