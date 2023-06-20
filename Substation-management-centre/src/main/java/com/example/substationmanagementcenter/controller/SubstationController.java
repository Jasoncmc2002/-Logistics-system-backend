package com.example.substationmanagementcenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.substationmanagementcenter.beans.HttpResponseEntity;
import com.example.substationmanagementcenter.common.Constans;
import com.example.substationmanagementcenter.entity.Task;
import com.example.substationmanagementcenter.sevice.TaskService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author hzn
 * @create 2023-06-19 15:35
 */

@RestController
@RequestMapping("/substation")
public class SubstationController {

    @Autowired
    private TaskService taskService;

    private final Logger logger = LoggerFactory.getLogger(SubstationController.class);


    @GetMapping("/")
    public ResponseEntity<Page<Task>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize){
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<Task> aPage = taskService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    //@PathVariable("date") String date

    @RequestMapping(value = "/getTaskListByDeadline",method = RequestMethod.GET, headers = "Accept"
            + "=application/json")
    public HttpResponseEntity getTaskListByDeadline(@RequestBody Map<String,Object> map){

        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            PageInfo pageInfo= taskService.getTaskListByDeadline(map);
            httpResponseEntity.setData(pageInfo);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);


        } catch (Exception e) {
            logger.info("通过deadline查询>>>>>"+e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;


//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date parse = null;
//        try {
//            parse = simpleDateFormat.parse(date);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        return taskService.getTaskListByDeadline(parse);
    }








//    @RequestMapping("/{date}")
//    public List<Task> getTaskListByDeadline(@PathVariable("date") String date){
//
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date parse = null;
//        try {
//            parse = simpleDateFormat.parse(date);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        return taskService.getTaskListByDeadline(parse);
//    }

}
