package com.example.substationmanagementcenter.sevice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.substationmanagementcenter.entity.Task;
import com.example.substationmanagementcenter.mapper.TaskMapper;
import com.example.substationmanagementcenter.sevice.TaskService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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


    @Override
    public PageInfo getTaskListByCriteria(Map<String,Object> map) throws ParseException {
        PageHelper.startPage(Integer.valueOf((String)map.get("pageNum")),
                Integer.valueOf((String)map.get("pageSize")));
        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(map.get("deadline") != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date deadline =sdf.parse((String) map.get("deadline"));

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(deadline);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            Date endTime = calendar.getTime();
            queryWrapper.between("deadline",deadline,endTime);
        }else if(map.get("task_type") != null){
            queryWrapper.eq("task_type",map.get("task_type"));
        }else if(map.get("task_status") != null){
            queryWrapper.eq("task_status",map.get("task_status"));
        }
        List<Task> res= taskMapper.selectList(queryWrapper);
        PageInfo pageInfo = new PageInfo(res);
        return pageInfo;
    }

    @Override
    public int updatebyId(Task task) {
        return taskMapper.updateById(task);
    }

    @Override
    public PageInfo getTaskToDistribute(Map<String, Object> map) throws ParseException {
        PageHelper.startPage(Integer.valueOf((String)map.get("pageNum")),
                Integer.valueOf((String)map.get("pageSize")));
        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("task_status","可分配");
        List<Task> res= taskMapper.selectList(queryWrapper);
        PageInfo pageInfo = new PageInfo(res);
        return pageInfo;
    }

    @Override
    public PageInfo getTaskToReceipt(Map<String, Object> map) throws ParseException {
        PageHelper.startPage(Integer.valueOf((String)map.get("pageNum")),
                Integer.valueOf((String)map.get("pageSize")));
        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("task_status","已分配");
        List<Task> res= taskMapper.selectList(queryWrapper);
        PageInfo pageInfo = new PageInfo(res);
        return pageInfo;
    }


}
