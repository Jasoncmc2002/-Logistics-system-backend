package com.example.customerservicecentre.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.customerservicecentre.entity.Order;
import com.example.customerservicecentre.mapper.OrderMapper;
import com.example.customerservicecentre.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-19
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
