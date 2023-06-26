package com.example.financialmanagement.service.impl;

import com.example.financialmanagement.entity.Orders;
import com.example.financialmanagement.entity.Return;
import com.example.financialmanagement.entity.result;
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
   private DistributionFeign distributionFeign;
  public result settlementSupply(Map<String,Object> map){
    List<Buy> buys= distributionFeign.getbuys(map);//得到商品的id
    List<Orders> orders= customerFeign.getorders(map);//得到该批商品
    List<Return> returns= customerFeign.getreturn(map);

    return null;
  }

}
