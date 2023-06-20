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
    public PageInfo getTaskListByDeadline(Map<String,Object> map) throws ParseException {
        PageHelper.startPage(Integer.valueOf((String)map.get("pageNum")),
                Integer.valueOf((String)map.get("pageSize")));
        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date deadline =sdf.parse((String) map.get("deadline"));
        queryWrapper.eq("deadline",deadline);

        List<Task> res= taskMapper.selectList(queryWrapper);
        PageInfo pageInfo = new PageInfo(res);
        System.out.println(res);
        return pageInfo;
    }
}
