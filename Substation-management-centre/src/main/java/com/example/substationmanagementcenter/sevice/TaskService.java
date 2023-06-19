package com.example.substationmanagementcenter.sevice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.substationmanagementcenter.entity.Task;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 退订 服务类
 * </p>
 *
 * @author gahgfa
 * @since 2023-06-19
 */
public interface TaskService extends IService<Task> {
    List<Task> getTaskListByDeadline(Date date);
}
