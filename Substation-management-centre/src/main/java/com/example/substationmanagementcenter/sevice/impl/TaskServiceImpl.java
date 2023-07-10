package com.example.substationmanagementcenter.sevice.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.substationmanagementcenter.beans.HttpResponseEntity;
import com.example.substationmanagementcenter.entity.Customer;
import com.example.substationmanagementcenter.entity.Orders;
import com.example.substationmanagementcenter.entity.Postman;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZoneId chinaZoneId = ZoneId.of("Asia/Shanghai");
        if(map.get("startLine") != null && !map.get("startLine").equals("")){

            // 格式化中国时区时间为指定格式的字符串
            String date = LocalDateTime.parse(String.valueOf(map.get("startline")), DateTimeFormatter.ISO_DATE_TIME).atZone(
                    ZoneOffset.UTC).withZoneSameInstant(chinaZoneId).format(formatter);
            Date startline = simpleDateFormat.parse(date);
            System.out.println("start"+startline);
            queryWrapper.ge("deadline",startline);
        }
        if(map.get("endLine") != null && !map.get("endLine").equals("")){
            String date = LocalDateTime.parse(String.valueOf(map.get("endline")), DateTimeFormatter.ISO_DATE_TIME).atZone(
                    ZoneOffset.UTC).withZoneSameInstant(chinaZoneId).format(formatter);
            Date endline = simpleDateFormat.parse(date);
            System.out.println("end!!!"+endline);
            queryWrapper.lt("deadline",endline);
        }
        if(map.get("taskType") != null && !map.get("taskType").equals("")){
            System.out.println("taskType");
            queryWrapper.eq("task_type",map.get("taskType"));
        }
        if(map.get("taskStatus") != null && !map.get("taskStatus").equals("")){
            queryWrapper.eq("task_status",map.get("taskStatus"));
        }
        if(map.get("substation") != null && !map.get("substation").equals("")){
            queryWrapper.eq("substation",map.get("substation"));
        }
        if(map.get("postman") != null && !map.get("postman").equals("")){
            queryWrapper.eq("postman",map.get("postman"));
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
            System.out.println("111111"+task.getOrderId());
            HttpResponseEntity resOrder = feignApi.getOrderByid(map1);
            System.out.println("resOrder"+resOrder);
            String jsonString1 = JSON.toJSONString(resOrder.getData());  // 将对象转换成json格式数据
            Orders order = JSON.parseObject(jsonString1, Orders.class); // 这样就可以了
            System.out.println("order"+order);

            //由task找对应的customer
            HttpResponseEntity resCustomer = feignApi.selectByCustomerId(task.getCustomerId());
            String jsonString3 = JSON.toJSONString(resCustomer.getData());  // 将对象转换成json格式数据
            Customer customer = JSON.parseObject(jsonString3, Customer.class); // 这样就可以了
            System.out.println("customer!!!"+customer);


            //列表内每一列的数据
            TaskOrder taskOrder = new TaskOrder();
            taskOrder.setOrderId(task.getOrderId());
            taskOrder.setId(task.getId());
            taskOrder.setTaskType(task.getTaskType());
            taskOrder.setCustomerName(task.getCustomerName());
            taskOrder.setAddress(task.getAddress());
            taskOrder.setReceiveName(order.getReceiveName());
            taskOrder.setDeadline(task.getDeadline());
            taskOrder.setGoodSum(order.getGoodSum());
            taskOrder.setEndDate(task.getEndDate());
            System.out.println("task.getTaskDate()!!"+task.getTaskDate());
            taskOrder.setTaskDate(task.getTaskDate());
            taskOrder.setTaskStatus(task.getTaskStatus());
            taskOrder.setIsInvoice(order.getIsInvoice());
            taskOrder.setSubstation(task.getSubstation());
            taskOrder.setPostman(task.getPostman());
            taskOrder.setMobilePhone(customer.getMobilephone());
            taskOrder.setCustomerAddress(customer.getAddress());
            taskOrders.add(taskOrder);
        }
        PageInfo pageInfo = new PageInfo(taskOrders);
        return pageInfo;
    }

    @Override
    public int updateTaskPostmanById(Map<String, Object> map) {
        UpdateWrapper<Task> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",map.get("id"));
        if(!map.get("taskStatus").equals("已分配")){
            System.out.println("!!!!!");
            updateWrapper.set("task_status","已分配");
        }
//        String jsonString1 = JSON.toJSONString(map);  // 将对象转换成json格式数据
//        JSONObject jsonObject = JSON.parseObject(jsonString1); // 在转回去
//        Postman postman = JSON.parseObject(jsonObject.getString("postman"), Postman.class); // 这样就可以了

        updateWrapper.set("postman",map.get("postman"));
        Integer rows = taskMapper.update(null, updateWrapper);
        return rows;
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
