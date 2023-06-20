package com.example.substationmanagementcenter.sevice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.substationmanagementcenter.entity.Task;
import com.github.pagehelper.PageInfo;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 退订 服务类
 * </p>
 *
 * @author hzn
 * @since 2023-06-19
 */
public interface TaskService extends IService<Task> {
    PageInfo getTaskListByDeadline(Map<String,Object> map) throws ParseException;
}
