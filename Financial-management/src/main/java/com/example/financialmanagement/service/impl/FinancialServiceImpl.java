package com.example.financialmanagement.service.impl;

import com.example.financialmanagement.entity.Buy;
import com.example.financialmanagement.entity.Orders;
import com.example.financialmanagement.entity.Return;
import com.example.financialmanagement.entity.result;
import com.example.financialmanagement.feign.CustomerFeign;
import com.example.financialmanagement.feign.OrderFeign;
import com.example.financialmanagement.feign.WareFeign;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 进货单 服务实现类
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-20
 */
@Service
public class FinancialServiceImpl {
   @Autowired
   private CustomerFeign customerFeign;
   @Autowired
   private WareFeign wareFeign;
  public result settlementSupply(Map<String,Object> map){
    List<Buy> buys= wareFeign.getbuys(map);//得到商品的id
    List<Orders> orders= customerFeign.getorders(map);//得到该批商品
    List<Return> returns= customerFeign.getreturn(map);

    return null;
  }

}
