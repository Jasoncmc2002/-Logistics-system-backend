package com.example.customerservicecentre.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.customerservicecentre.common.utils.DateUtil;
import com.example.customerservicecentre.entity.Orders;
import com.example.customerservicecentre.entity.Unsubscribe;
import com.example.customerservicecentre.mapper.OrderMapper;
import com.example.customerservicecentre.mapper.UnsubscribeMapper;
import com.example.customerservicecentre.service.UnsubscribeService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
public class UnsubscribeServiceImpl extends ServiceImpl<UnsubscribeMapper, Unsubscribe> implements UnsubscribeService {
  @Autowired
  private UnsubscribeMapper unsubscribeMapper;
  @Autowired
  private OrderMapper orderMapper;
  @Override
  public int insert(Unsubscribe unsubscribe) {
    Date date = DateUtil.getCreateTime();
    unsubscribe.setOrderDate(date);
    System.out.println(unsubscribe);
    int res= unsubscribeMapper.insert(unsubscribe);
    return res;
  }

  @Override
  public int check(Orders orders) {
    Orders res= orderMapper.selectById(orders.getId());
    if(res.getOrderStatus().equals("缺货")||res.getOrderStatus().equals("可分配")){
      return 0;
    }
    return 1;
  }
}
