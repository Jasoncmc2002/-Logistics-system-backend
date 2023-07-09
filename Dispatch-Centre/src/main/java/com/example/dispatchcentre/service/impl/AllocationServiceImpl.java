package com.example.dispatchcentre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dispatchcentre.entity.Allocation;
import com.example.dispatchcentre.entity.Task;
import com.example.dispatchcentre.mapper.AllocationMapper;
import com.example.dispatchcentre.service.AllocationService;
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
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品调拨 服务实现类
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-25
 */
@Service
public class AllocationServiceImpl extends ServiceImpl<AllocationMapper, Allocation> implements
    AllocationService {
  @Autowired
  private AllocationMapper allocationMapper;
  @Autowired
  private TaskServiceImpl taskService;
  @Override
  public int insertTaskDispatch(Map<String,Object> map) throws ParseException {
    Long taskID= taskService.insert(map);
   Allocation allocation = new Allocation();
   allocation.setTaskId(taskID);
   allocation.setOrderId(Long.valueOf(String.valueOf(map.get("orderId"))));
   allocation.setInStation(map.get("inStation").toString());
   allocation.setOutStation(map.get("outStation").toString());
   allocation.setAlloType(Byte.valueOf(String.valueOf(map.get("alloType"))));
   int res=allocationMapper.insert(allocation);
    return res;
  }

  @Override
  public int insertSationDispatch(Allocation allocation) {
    int res=allocationMapper.insert(allocation);
    return res;
  }
/*
  根据id来更改type
 */
  @Override
  public int updatebyId(Map<String, Object> map) {
    UpdateWrapper<Allocation> updateWrapper = new UpdateWrapper<>();
    updateWrapper.eq("id",map.get("id")).set("allo_type", map.get("allo_type"));
    Integer rows = allocationMapper.update(null, updateWrapper);
    return rows;
  }

  @Override
  public Allocation selectbyId(Long id) {
    return null;
  }

  @Override
  public PageInfo selectAll(Map<String, Object> map) {
    return null;
  }

  @Override
  public PageInfo searchbykey(Map<String, Object> map) {
    PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
        Integer.valueOf(String.valueOf(map.get("pageSize"))));
    QueryWrapper<Allocation> queryWrapper = new QueryWrapper<>();
    int type=Integer.valueOf(String.valueOf(map.get("alloType")));
    ZoneId chinaZoneId = ZoneId.of("Asia/Shanghai");
    // 格式化中国时区时间为指定格式的字符串
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String startDate = LocalDateTime.parse(String.valueOf(map.get("startLine")),
        DateTimeFormatter.ISO_DATE_TIME).atZone(
        ZoneOffset.UTC).withZoneSameInstant(chinaZoneId).format(formatter);
    String endDate = LocalDateTime.parse(String.valueOf(map.get("endLine")), DateTimeFormatter.ISO_DATE_TIME).atZone(
        ZoneOffset.UTC).withZoneSameInstant(chinaZoneId).format(formatter);

    queryWrapper.between("allocation_date", startDate, endDate);
    queryWrapper.eq("allo_type",type);
    List<Allocation> res= allocationMapper.selectList(queryWrapper);
//    System.out.println(res);
    PageInfo pageInfo = new PageInfo(res);
    return pageInfo;
  }
}
