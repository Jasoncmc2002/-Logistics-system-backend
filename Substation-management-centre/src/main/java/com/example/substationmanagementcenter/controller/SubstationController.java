package com.example.substationmanagementcenter.controller;

import com.example.substationmanagementcenter.entity.Task;
import com.example.substationmanagementcenter.sevice.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author hzn
 * @create 2023-06-19 15:35
 */

@RestController
@RequestMapping("/substation")
public class SubstationController {

    @Autowired
    private TaskService taskService;

    @RequestMapping("getTaskListByDeadline/{date}")
    public List<Task> getTaskListByDeadline(@PathVariable("date") String date){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = null;
        try {
            parse = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return taskService.getTaskListByDeadline(parse);

//        List<Task> taskListByDeadline = taskService.getTaskListByDeadline(date);
//        return taskListByDeadline;
    }

}
