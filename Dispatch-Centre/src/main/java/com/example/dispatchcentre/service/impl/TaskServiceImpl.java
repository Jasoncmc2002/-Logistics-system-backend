package com.example.dispatchcentre.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.dispatchcentre.beans.HttpResponseEntity;
import com.example.dispatchcentre.common.utils.DateUtil;
import com.example.dispatchcentre.entity.Allocation;
import com.example.dispatchcentre.entity.Good;
import com.example.dispatchcentre.entity.Orders;
import com.example.dispatchcentre.entity.Task;
import com.example.dispatchcentre.entity.vo.Delivery;
import com.example.dispatchcentre.feign.FeignApi;
import com.example.dispatchcentre.mapper.TaskMapper;
import com.example.dispatchcentre.service.TaskService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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
  @Autowired
  private TaskMapper taskMapper;
  @Autowired
  private FeignApi feignApi;
  private AllocationServiceImpl allocationService=new AllocationServiceImpl();
  @Override
  public Long insert(Map<String,Object > map) throws ParseException {
//    String jsonString1 = JSON.toJSONString(map);  // 将对象转换成json格式数据
//    JSONObject jsonObject = JSON.parseObject(jsonString1); // 在转回去
//    Task task = JSON.parseObject(jsonObject.getString("Task"), Task.class); // 这样就可以了
    Task task =new Task();
    Date date = DateUtil.getCreateTime();
    task.setTaskDate(date);
    HttpResponseEntity res= feignApi.getOrderByid(Long.valueOf(String.valueOf(map.get("orderId"))));
    String jsonString2 = JSON.toJSONString(res.getData());  // 将对象转换成json格式数据
    Orders orders = JSON.parseObject(jsonString2,Orders.class);
    task.setOrderId(orders.getId());
    task.setCustomerId(Long.valueOf(orders.getCustomerId()));
    task.setCustomerName(orders.getCustomerName());
    task.setCreater(map.get("creator").toString());

    ZoneId chinaZoneId = ZoneId.of("Asia/Shanghai");
    // 格式化中国时区时间为指定格式的字符串
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String deadline = LocalDateTime.parse(String.valueOf(map.get("deadline")),
        DateTimeFormatter.ISO_DATE_TIME).atZone(
        ZoneOffset.UTC).withZoneSameInstant(chinaZoneId).format(formatter);
    Date deaddate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(deadline);
    task.setDeadline(deaddate);
    if(orders.getOrderType()=="新订"){
      task.setTaskType("送货");
    }else if(orders.getOrderType()=="换货"){
      task.setTaskType("换货");
    }else if(orders.getOrderType()=="退货"){
      task.setTaskType("退货");
    }else if(orders.getOrderType()=="送货收款"){
      task.setTaskType("送货收款");
    }
    task.setTaskStatus("已调度");
    task.setSubstation(map.get("substation").toString());
    task.setAddress(orders.getCustomerAddress());
    task.setPrintNumber(0);
    int res1 = taskMapper.insert(task);//添加order;
    if(res1==1){
      return task.getId();
    }
    return 0L;
  }

  @Override
  public int updatebyId(Task task) {
    int res=  taskMapper.updateById(task);
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
  public PageInfo searchbykey(Map<String, Object> map) {
    PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
        Integer.valueOf(String.valueOf(map.get("pageSize"))));
    QueryWrapper<Task> queryWrapper = new QueryWrapper<>();

    ZoneId chinaZoneId = ZoneId.of("Asia/Shanghai");
    // 格式化中国时区时间为指定格式的字符串
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String startDate = LocalDateTime.parse(String.valueOf(map.get("startTime")), DateTimeFormatter.ISO_DATE_TIME).atZone(
        ZoneOffset.UTC).withZoneSameInstant(chinaZoneId).format(formatter);
    String endDate = LocalDateTime.parse(String.valueOf(map.get("endTime")), DateTimeFormatter.ISO_DATE_TIME).atZone(
        ZoneOffset.UTC).withZoneSameInstant(chinaZoneId).format(formatter);


    queryWrapper.between("deadline", startDate, endDate)
        .or().eq("substation",  map.get("substation"))
        .or().eq("task_type",  map.get("task_type"))
        .or().eq("task_status",  map.get("task_status"));
    List<Task> res= taskMapper.selectList(queryWrapper);
//    System.out.println(res);
    PageInfo pageInfo = new PageInfo(res);
    return pageInfo;
  }

  @Override
  public PageInfo selectOrder(Map<String, Object> map) {
    return null;
  }

  @Override
  public int changeTaskOrderType(Map<String, Object> map) {
    Task task=new Task();
    task.setId( Long.valueOf(String.valueOf(map.get("id"))));
    task.setTaskStatus(String.valueOf(map.get("task_status")));
    taskMapper.updateById(task);
    Task task1=new Task();
    task1 =taskMapper.selectById(Long.valueOf(String.valueOf(map.get("id"))));
    Long orderId=task1.getOrderId();
    Map<String, Object> orderMap=new HashMap<>();
    orderMap.put("id",orderId);
    orderMap.put("orderStatus",map.get("order_status"));
    HttpResponseEntity res =feignApi.changeOrderStatusById(orderMap);
    return (int) res.getData();
  }


  @Override
  public List<Task> selectByDate(Map<String, Object> map) {

    QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
    ZoneId chinaZoneId = ZoneId.of("Asia/Shanghai");
    // 格式化中国时区时间为指定格式的字符串
    if(map.get("startTime")!=null&&map.get("endTime")!=null&&map.get("startTime")!=""&&map.get("endTime")!=""){
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      String startDate = LocalDateTime.parse(String.valueOf(map.get("startTime")), DateTimeFormatter.ISO_DATE_TIME).atZone(
              ZoneOffset.UTC).withZoneSameInstant(chinaZoneId).format(formatter);
      String endDate = LocalDateTime.parse(String.valueOf(map.get("endTime")), DateTimeFormatter.ISO_DATE_TIME).atZone(
              ZoneOffset.UTC).withZoneSameInstant(chinaZoneId).format(formatter);

      queryWrapper.between("task_date", startDate, endDate);
    }

    List<Task> res= taskMapper.selectList(queryWrapper);

    return res;
  }

  @Override
  public PageInfo getGoodListByTaskId(Map<String, Object> map) {
    PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
        Integer.valueOf(String.valueOf(map.get("pageSize"))));
    Long orderId = Long.valueOf(String.valueOf(map.get("orderId")));
    HttpResponseEntity goodhttp=feignApi.getGoodByOrderId(orderId);
    /*查询并统计结果*/
    String jsonString2 = JSON.toJSONString(goodhttp.getData());  // 将对象转换成json格式数据
    List<Good> goodsList = JSON.parseArray(jsonString2,Good.class);
    PageInfo pageInfo = new PageInfo(goodsList);
    return pageInfo;
  }

  @Override
  public Delivery getDelivery(Map<String, Object> map) {
    PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
        Integer.valueOf(String.valueOf(map.get("pageSize"))));
    Long taskId = Long.valueOf(String.valueOf(map.get("taskId")));
    Task task = taskMapper.selectById(taskId);
    Delivery delivery=new Delivery();
    if(task.getTaskStatus()=="已分配") {

      HttpResponseEntity goodhttp = feignApi.getGoodByOrderId(task.getOrderId());
      /*查询并统计结果*/
      String jsonString2 = JSON.toJSONString(goodhttp.getData());  // 将对象转换成json格式数据
      List<Good> goodsList = JSON.parseArray(jsonString2, Good.class);
      PageInfo pageInfo = new PageInfo(goodsList);
      delivery.setTask(task);
      delivery.setPageInfo(pageInfo);
      return delivery;
    }
    return null;
  }
}
