package com.example.customerservicecentre.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.customerservicecentre.beans.HttpResponseEntity;
import com.example.customerservicecentre.common.utils.DateUtil;
import com.example.customerservicecentre.entity.Good;
import com.example.customerservicecentre.entity.Orders;
import com.example.customerservicecentre.entity.Return;
import com.example.customerservicecentre.entity.Unsubscribe;
import com.example.customerservicecentre.feign.DistributionFeign;
import com.example.customerservicecentre.mapper.OrderMapper;
import com.example.customerservicecentre.mapper.ReturnMapper;
import com.example.customerservicecentre.mapper.UnsubscribeMapper;
import com.example.customerservicecentre.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {

  @Autowired
  private OrderMapper orderMapper;
  @Autowired
  private UnsubscribeMapper unsubscribeMapper;
  @Autowired
  private ReturnMapper returnMapper;
  @Autowired
  private DistributionFeign distributionFeign;


  @Override
  public int insert(Map<String,Object > map) {
    String jsonString1 = JSON.toJSONString(map);  // 将对象转换成json格式数据
    JSONObject jsonObject = JSON.parseObject(jsonString1); // 在转回去
    Orders order = JSON.parseObject(jsonObject.getString("Orders"), Orders.class); // 这样就可以了

    List<Good> goods=JSON.parseArray(jsonObject.getString("Goods"), Good.class);
    Date date = DateUtil.getCreateTime();
    order.setOrderDate(date);

    int res = orderMapper.insert(order);//添加order;
    Long orderId= order.getId();

    for(Good good:goods){
      good.setKeyId(Math.toIntExact(orderId));
      HttpResponseEntity ss= distributionFeign.addGoods(good);
      System.out.println(ss);
    }
    return res;
  }

  public PageInfo getOrdersByCriteria(Map<String, Object> map) throws ParseException {
    PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
        Integer.valueOf(String.valueOf(map.get("pageSize"))));

    QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date startTime =sdf.parse((String) map.get("startTime"));
    Date endTime = sdf.parse((String) map.get("endTime"));
    queryWrapper.between("order_date", startTime, endTime)
        .eq("customer_name",  map.get("customer_name"))
        .eq("order_type",  map.get("order_type"));
    List<Orders> res= orderMapper.selectList(queryWrapper);

//    System.out.println(res);
    PageInfo pageInfo = new PageInfo(res);
    return pageInfo;
}

  @Override
  public Map<String, Object> getCreaterwork(Map<String, Object> map) throws ParseException{
    HashMap<String, Object> res=new HashMap<String, Object>();

//    PageHelper.startPage(Integer.valueOf((String)map.get("pageNum")),
//        Integer.valueOf((String)map.get("pageSize")));

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date startTime =sdf.parse((String) map.get("startTime"));
    Date endTime = sdf.parse((String) map.get("endTime"));

    QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
    queryWrapper.between("order_date", startTime, endTime)
        .eq("creater",  map.get("creater"));
    List<Orders> resorders= orderMapper.selectList(queryWrapper);
    res.put("newOrder",resorders.size());  /*  新订笔数*/
    Double newMoney=0.0; /*  新订金额*/
    Long newNum=0L;/*  新订数量*/
    for (Orders resorder : resorders) {
      newMoney+=resorder.getGoodSum();

    }

    QueryWrapper<Unsubscribe> queryWrapper1 = new QueryWrapper<>();
    queryWrapper1.between("order_date", startTime, endTime)
        .eq("creater",  map.get("creater"));
    List<Unsubscribe> resUnsubscribe= unsubscribeMapper.selectList(queryWrapper1);
    res.put("unsOrder",resUnsubscribe.size());  /*  新订笔数*/
    Double unsMoney=0.0; /*  退订金额*/
//    Long unsNum=0L;/*  退订数量*/
//    for (Unsubscribe resuns : resUnsubscribe) {
//      unsMoney+=resuns.getSum()*resuns.getGoodPrice();
//      unsNum+=resuns.getSum();
//    }

    QueryWrapper<Return> queryWrapper2 = new QueryWrapper<>();
    queryWrapper2.between("order_date", startTime, endTime)
        .eq("creater",  map.get("creater"));
    List<Return> resReturn= returnMapper.selectList(queryWrapper2);
    res.put("reOrder",resReturn.size());  /*  新订笔数*/
    Double reMoney=0.0; /*  退订金额*/
    Long reNum=0L;/*  退订数量*/
    for (Return re : resReturn) {
      reMoney+=re.getSum()*re.getGoodPrice();
      reNum+=re.getSum();
    }

    res.put("creater",map.get("creater"));
    res.put("newMoney",newMoney);
    res.put("newNum",newNum);
    res.put("unsMoney",unsMoney);
//    res.put("unsNum",unsNum);
    res.put("reMoney",reMoney);
    res.put("reNum",reNum);
    return res;
  }

  @Override
  public PageInfo getAllOrder(Map<String, Object> map) {
    PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
        Integer.valueOf(String.valueOf(map.get("pageSize"))));
    List<Orders> res=orderMapper.selectList(null);
    PageInfo pageInfo = new PageInfo(res);
    return pageInfo;
  }
}
