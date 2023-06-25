package com.example.dispatchcentre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dispatchcentre.entity.Task;
import com.github.pagehelper.PageInfo;
import java.text.ParseException;
import java.util.Map;

/**
 * <p>
 * 任务单 服务类
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-25
 */
public interface TaskService extends IService<Task> {
  int insert(Map<String,Object > map) throws ParseException;
  int updatebyId(Task customer);
  Task selectbyId(Long id);
  PageInfo selectAll(Map<String,Object> map);
  int  deletebyId(Long id);
  PageInfo searchbykey(Map<String,Object> map) throws ParseException ;
  PageInfo selectOrder(Map<String, Object> map);

}
