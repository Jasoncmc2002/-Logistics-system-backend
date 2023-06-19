package com.example.substationmanagementcenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.substationmanagementcenter.entity.Task;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 退订 Mapper 接口
 * </p>
 *
 * @author hzn
 * @since 2023-06-19
 */
public interface TaskMapper extends BaseMapper<Task> {
    List<Task> getTaskListByDeadline(Date date);

}
