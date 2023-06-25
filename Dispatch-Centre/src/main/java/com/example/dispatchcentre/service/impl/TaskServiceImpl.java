package com.example.dispatchcentre.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.dispatchcentre.common.utils.DateUtil;
import com.example.dispatchcentre.entity.Allocation;
import com.example.dispatchcentre.entity.Task;
import com.example.dispatchcentre.mapper.TaskMapper;
import com.example.dispatchcentre.service.TaskService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 任务单 服务实现类
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-25
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {
  private TaskMapper taskMapper;
  private AllocationServiceImpl allocationService=new AllocationServiceImpl();
  @Override
  public int insert(Map<String,Object > map)throws ParseException {
    String jsonString1 = JSON.toJSONString(map);  // 将对象转换成json格式数据
    JSONObject jsonObject = JSON.parseObject(jsonString1); // 在转回去
    Task task = JSON.parseObject(jsonObject.getString("Task"), Task.class); // 这样就可以了

    Date date = DateUtil.getCreateTime();
    task.setTaskDate(date);

    int res1 = taskMapper.insert(task);//添加order;
    if(res1==1){
    Long Id= task.getId();

    Allocation allocation=new Allocation();
    allocation.setTaskId(Id);
    allocation.setOutStation("中心库房");
    allocation.setInStation(task.getSubstation());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date allocation_date =sdf.parse((String) map.get("allocation_date"));

    allocation.setAllocationDate(allocation_date);
      int res =allocationService.insert(allocation);
      return res;
    }
    return 0;
  }

  @Override
  public int updatebyId(Task customer) {
    int res=  taskMapper.updateById(customer);
    return res;
  }

  @Override
  public Task selectbyId(Long id) {

    return taskMapper.selectById(id);
  }

  @Override
  public PageInfo selectAll(Map<String, Object> map) {

    return null;
  }

  @Override
  public int deletebyId(Long id) {
    return 0;
  }

/*  根据分站，状态，类型查询*/
  @Override
  public PageInfo searchbykey(Map<String, Object> map) throws ParseException  {
    PageHelper.startPage(Integer.valueOf((String)map.get("pageNum")),
        Integer.valueOf((String)map.get("pageSize")));
    QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date startTime =sdf.parse((String) map.get("startTime"));
    Date endTime = sdf.parse((String) map.get("endTime"));
    queryWrapper.between("deadline", startTime, endTime)
        .or().eq("substation",  map.get("substation"))
        .or().eq("task_type",  map.get("task_type"))
        .or().eq("task_status",  map.get("task_status"));
    List<Task> res= taskMapper.selectList(queryWrapper);
//    System.out.println(res);
    PageInfo pageInfo = new PageInfo(res);
    PageInfo pageInfo = new PageInfo(res);
    return pageInfo;
  }

  @Override
  public PageInfo selectOrder(Map<String, Object> map) {
    return null;
  }

}
