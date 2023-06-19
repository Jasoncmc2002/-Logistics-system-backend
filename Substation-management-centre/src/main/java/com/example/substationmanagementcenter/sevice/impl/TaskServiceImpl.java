package com.example.substationmanagementcenter.sevice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.substationmanagementcenter.entity.Task;
import com.example.substationmanagementcenter.mapper.TaskMapper;
import com.example.substationmanagementcenter.sevice.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 退订 服务实现类
 * </p>
 *
 * @author gahgfa
 * @since 2023-06-19
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Autowired
    private TaskMapper taskMapper;


    @Override
    public List<Task> getTaskListByDeadline(Date date) {
        return taskMapper.getTaskListByDeadline(date);
    }
}
