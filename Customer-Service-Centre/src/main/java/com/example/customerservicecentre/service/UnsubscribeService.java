package com.example.customerservicecentre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.customerservicecentre.entity.Orders;
import com.example.customerservicecentre.entity.Return;
import com.example.customerservicecentre.entity.Unsubscribe;
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
public interface UnsubscribeService extends IService<Unsubscribe> {
  int insert(Unsubscribe unsubscribe);
  int check(Orders orders);
}
