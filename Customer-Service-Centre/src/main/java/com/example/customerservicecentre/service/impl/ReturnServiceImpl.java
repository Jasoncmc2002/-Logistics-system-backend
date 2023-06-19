package com.example.customerservicecentre.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.customerservicecentre.common.utils.DateUtil;
import com.example.customerservicecentre.entity.Orders;
import com.example.customerservicecentre.entity.Return;
import com.example.customerservicecentre.mapper.OrderMapper;
import com.example.customerservicecentre.mapper.ReturnMapper;
import com.example.customerservicecentre.service.ReturnService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 退订 服务实现类
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-19
 */
@Service
public class ReturnServiceImpl extends ServiceImpl<ReturnMapper, Return> implements ReturnService {

  @Autowired
  private ReturnMapper returnMapper;
  @Autowired
  private OrderMapper orderMapper;
  @Override
  public int insert(Return re) {
    Date date = DateUtil.getCreateTime();
    re.setDate(date);
    System.out.println(re);
    int res =returnMapper.insert(re);
    return res;
  }

  @Override
  public int check(Orders orders) {
    Orders res= orderMapper.selectById(orders.getId());
    if(res.getOrdersTatus().equals("完成")){
      return 1;
    }
    return 0;
  }
}
