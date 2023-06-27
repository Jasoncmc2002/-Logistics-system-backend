package com.example.substationmanagementcenter.controller;

import com.example.substationmanagementcenter.beans.HttpResponseEntity;
import com.example.substationmanagementcenter.common.Constans;
import com.example.substationmanagementcenter.entity.Task;
import com.example.substationmanagementcenter.sevice.TaskService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 任务单 前端控制器
 * @author hzn
 * @create 2023-06-19 15:35
 */

@RestController
@RequestMapping("/task")
public class TaskAction {

    private final Logger logger = LoggerFactory.getLogger(TaskAction.class);

    @Autowired
    private TaskService taskService;


    @RequestMapping(value = "/selectAllTask",method = RequestMethod.GET, headers = "Accept"
            + "=application/json")
    public HttpResponseEntity selectAllTask(@RequestBody Map<String,Object> map){
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            PageInfo pageInfo= taskService.selectAll(map);
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



    @RequestMapping(value = "/getTaskListByCriteria",method = RequestMethod.GET, headers = "Accept"
            + "=application/json")
    public HttpResponseEntity getTaskListByDeadline(@RequestBody Map<String,Object> map){

        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            PageInfo pageInfo= taskService.getTaskListByCriteria(map);
            httpResponseEntity.setData(pageInfo);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);


        } catch (Exception e) {
            logger.info("通过各种东西查询task>>>>>"+e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;

    }

    @RequestMapping(value = "/updateTask",method = RequestMethod.POST, headers = "Accept"
            + "=application/json")
    public HttpResponseEntity updateTask(@RequestBody Task params){
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            int res=taskService.updatebyId(params);
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
            logger.info("updateTask 更新任务单信息>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    @RequestMapping(value = "/getTaskToDistribute",method = RequestMethod.GET, headers = "Accept"
            + "=application/json")
    public HttpResponseEntity getTaskToDistribute(@RequestBody Map<String,Object> map){

        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            PageInfo pageInfo= taskService.getTaskToDistribute(map);
            httpResponseEntity.setData(pageInfo);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);


        } catch (Exception e) {
            logger.info("查可分配task>>>>>"+e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;

    }


    @RequestMapping(value = "/getTaskToReceipt",method = RequestMethod.GET, headers = "Accept"
            + "=application/json")
    public HttpResponseEntity getTaskToReceipt(@RequestBody Map<String,Object> map){

        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            PageInfo pageInfo= taskService.getTaskToReceipt(map);
            httpResponseEntity.setData(pageInfo);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);


        } catch (Exception e) {
            logger.info("查已分配任务task进行回执录入，之后要使任务变为“已完成”>>>>>"+e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;

    }




}