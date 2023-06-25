package com.example.customerservicecentre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.customerservicecentre.entity.Orders;
import com.example.customerservicecentre.entity.Return;
import org.springframework.stereotype.Service;

@Service
/**
 * <p>
 * 退订 服务类
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-19
 */
public interface ReturnService extends IService<Return> {
  int insert(Return re);
  int check(Orders orders);
}
