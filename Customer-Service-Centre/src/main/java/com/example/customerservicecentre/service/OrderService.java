package com.example.customerservicecentre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.customerservicecentre.entity.Orders;
import com.github.pagehelper.PageInfo;
import java.text.ParseException;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-19
 */
public interface OrderService extends IService<Orders> {
  int insert(Map<String,Object > map);
  PageInfo getOrdersByCriteria(Map<String, Object> map) throws ParseException;
  Map<String,Object> getCreaterwork(Map<String, Object> map) throws ParseException;
  PageInfo getAllOrder(Map<String, Object> map);
  PageInfo selectOrderbyCustomer(Map<String, Object> map);
  int updatebyId(Orders orders);
}
