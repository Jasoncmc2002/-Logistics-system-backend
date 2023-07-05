package com.example.substationmanagementcenter.sevice.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.substationmanagementcenter.beans.HttpResponseEntity;
import com.example.substationmanagementcenter.entity.Orders;
import com.example.substationmanagementcenter.entity.Task;
import com.example.substationmanagementcenter.entity.vo.TaskOrder;
import com.example.substationmanagementcenter.feign.FeignApi;
import com.example.substationmanagementcenter.mapper.TaskMapper;
import com.example.substationmanagementcenter.sevice.TaskService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 退订 服务实现类
 * </p>
 *
 * @author hzn
 * @since 2023-06-19
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private FeignApi feignApi;


    @Override
    public PageInfo selectAll(Map<String,Object> map) {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
                Integer.valueOf(String.valueOf(map.get("pageSize"))));
        List<Task> res= taskMapper.selectList(null);
        PageInfo pageInfo = new PageInfo(res);
        return pageInfo;
    }

    @Override
    public PageInfo getTaskListByCriteria(Map<String,Object> map) throws ParseException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
                Integer.valueOf(String.valueOf(map.get("pageSize"))));

        //筛选task
        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(map.get("startLine") != null){
            Date startline =sdf.parse((String) map.get("startLine"));
            System.out.println("start"+startline);
            queryWrapper.ge("deadline",startline);
        }
        if(map.get("endLine") != null){
            Date endline =sdf.parse((String) map.get("endLine"));
            System.out.println("end!!!"+endline);
            queryWrapper.lt("deadline",endline);
        }
        if(map.get("taskType") != null){
            System.out.println("taskType");
            queryWrapper.eq("task_type",map.get("taskType"));
        }
        if(map.get("taskStatus") != null){
            queryWrapper.eq("task_status",map.get("taskStatus"));
        }
        if(map.get("substation") != null){
            queryWrapper.eq("substation",map.get("substation"));
        }
        List<Task> res= taskMapper.selectList(queryWrapper);
        System.out.println("res!!1"+res);


        //设置返回前端的列表
        List<TaskOrder> taskOrders = new ArrayList<>();
        for(Task task:res){
            System.out.println("for");
            //由task找对应的order
            Map map1 = new HashMap<>();
            map1.put("id",task.getOrderId());
            HttpResponseEntity resOrder = feignApi.getByOrderId(map1);
            System.out.println("resOrder"+resOrder);
            String jsonString1 = JSON.toJSONString(resOrder.getData());  // 将对象转换成json格式数据
            JSONObject jsonObject = JSON.parseObject(jsonString1); // 再转回去
            Orders order = JSON.parseObject(jsonObject.getString("Orders"), Orders.class); // 这样就可以了
            Orders order1 = (Orders) resOrder.getData();
            System.out.println("order"+order);
            System.out.println("order1"+order);


            //列表内每一列的数据
            TaskOrder taskOrder = new TaskOrder();
            taskOrder.setOrderId(task.getOrderId());
            taskOrder.setId(task.getId());
            taskOrder.setTaskType(task.getTaskType());
            taskOrder.setCustomerName(task.getCustomerName());
            taskOrder.setAddress(task.getAddress());
            taskOrder.setRecieveName(order.getReceive_name());
            taskOrder.setDeadline(task.getDeadline());
            taskOrder.setGoodsum(order.getGoodSum());
            taskOrder.setEndDate(task.getEndDate());
            taskOrder.setTaskStatus(task.getTaskStatus());
            taskOrder.setIsInvoice(order.getIsInvoice());
            taskOrders.add(taskOrder);
        }
        PageInfo pageInfo = new PageInfo(taskOrders);
        return pageInfo;
    }

    @Override
    public int updatebyId(Task task) {
        return taskMapper.updateById(task);
    }

    @Override
    public PageInfo getTaskToDistribute(Map<String, Object> map) throws ParseException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
                Integer.valueOf(String.valueOf(map.get("pageSize"))));
        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("task_status","可分配");
        List<Task> res= taskMapper.selectList(queryWrapper);
        PageInfo pageInfo = new PageInfo(res);
        return pageInfo;
    }

    @Override
    public PageInfo getTaskToReceipt(Map<String, Object> map) throws ParseException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
                Integer.valueOf(String.valueOf(map.get("pageSize"))));
        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("task_status","已分配");
        List<Task> res= taskMapper.selectList(queryWrapper);
        PageInfo pageInfo = new PageInfo(res);
        return pageInfo;
    }

    @Override
    public PageInfo selectTaskById(Map<String, Object> map) throws ParseException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
                Integer.valueOf(String.valueOf(map.get("pageSize"))));

        Task res= taskMapper.selectById((int)map.get("id"));
        List<Task> tasks = new ArrayList<>();
        tasks.add(res);
        PageInfo pageInfo = new PageInfo(tasks);
        return pageInfo;
    }



}
