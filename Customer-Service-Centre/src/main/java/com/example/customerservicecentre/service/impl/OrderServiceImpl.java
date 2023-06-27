package com.example.customerservicecentre.service.impl;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.customerservicecentre.beans.HttpResponseEntity;
import com.example.customerservicecentre.common.utils.DateUtil;
import com.example.customerservicecentre.entity.Customer;
import com.example.customerservicecentre.entity.Good;
import com.example.customerservicecentre.entity.Orders;
import com.example.customerservicecentre.entity.Return;
import com.example.customerservicecentre.entity.Unsubscribe;
import com.example.customerservicecentre.entity.vo.CreaterWork;
import com.example.customerservicecentre.feign.FeignApi;
import com.example.customerservicecentre.mapper.GoodMapper;
import com.example.customerservicecentre.mapper.OrderMapper;
import com.example.customerservicecentre.mapper.ReturnMapper;
import com.example.customerservicecentre.mapper.UnsubscribeMapper;
import com.example.customerservicecentre.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
  private FeignApi feignApi;
  @Autowired
  private GoodMapper goodMapper;


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
    System.out.println(goods);
    for(Good good:goods){
      good.setKeyId(Math.toIntExact(orderId));
      HttpResponseEntity ss= feignApi.addGoods(good);
      System.out.println(ss);
    }
    return res;
  }

  public PageInfo getOrdersByCriteria(Map<String, Object> map) throws ParseException {
    PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
        Integer.valueOf(String.valueOf(map.get("pageSize"))));

    QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date startTime =null;
    Date endTime = null;
    Date now=DateUtil.getCreateTime();
    Date old=sdf.parse("1993-07-01 17:54:18");
    if(!map.get("startTime").equals("")){
      startTime =sdf.parse((String) map.get("startTime"));
    }
    if(!map.get("endTime").equals("")){
      endTime =sdf.parse((String) map.get("endTime"));
    }
    // 判断name属性是否为空，如果不为空则作为查询条件
    if (!map.get("customer_name").equals("")) {
      queryWrapper.like("customer_name", map.get("customer_name"));
    }
    if (startTime!=null&& endTime!=null) {
      queryWrapper.between("order_date", startTime, endTime);
    }
    else if (startTime==null&& endTime!=null) {
      queryWrapper.between("order_date", old, endTime);
    }
    else if (startTime!=null&& endTime==null) {
      queryWrapper.between("order_date", startTime, now);
    }
    if (!map.get("order_type").equals("")) {
      queryWrapper.eq("order_type", map.get("order_type"));
    }
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
    System.out.println(map);
    PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
        Integer.valueOf(String.valueOf(map.get("pageSize"))));
    List<Orders> res=orderMapper.selectList(null);
    PageInfo<Orders> pageInfo = new PageInfo<Orders>(res);
    return pageInfo;
  }

  @Override
  public PageInfo selectOrderbyCustomer(Map<String, Object> map) {
    PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
        Integer.valueOf(String.valueOf(map.get("pageSize"))));
    QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();

    // 判断name属性是否为空，如果不为空则作为查询条件
    if (!map.get("customerId").equals("")) {
      queryWrapper.eq("customer_id", map.get("customerId"));
    }
    List<Orders> res=orderMapper.selectList(queryWrapper);
    PageInfo<Orders> pageInfo = new PageInfo<>(res);
    return pageInfo;
  }

  @Override
  public int updatebyId(Orders orders) {
    int res =orderMapper.updateById(orders);
    return res;
  }

  @Override
  public PageInfo getWorkByid(Map<String, Object> map) throws ParseException {
    PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
        Integer.valueOf(String.valueOf(map.get("pageSize"))));

    QueryWrapper<Orders> orderWrapper = new QueryWrapper<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date startTime = null;
    Date endTime = null;
    Date now = DateUtil.getCreateTime();
    Date old = sdf.parse("1993-07-01 17:54:18");
    if (!map.get("startTime").equals("")) {
      startTime = sdf.parse((String) map.get("startTime"));
    }
    if (!map.get("endTime").equals("")) {
      endTime = sdf.parse((String) map.get("endTime"));
    }
    // 判断name属性是否为空，如果不为空则作为查询条件
    if (!map.get("creater").equals("")) {
      orderWrapper.eq("creater", map.get("creater"));
    }
    if (startTime != null && endTime != null) {
      orderWrapper.between("order_date", startTime, endTime);
    } else if (startTime == null && endTime != null) {
      orderWrapper.between("order_date", old, endTime);
    } else if (startTime != null && endTime == null) {
      orderWrapper.between("order_date", startTime, now);
    }
    /* 根据订单表查询结果获取对应的order_id列表*/
    orderWrapper.between("order_date", startTime, endTime);
    List<Orders> orderList = orderMapper.selectList(orderWrapper);
//    List<Integer> orderIdList = new ArrayList<>();
    /*    结果表*/
    Map<String, Map<String, Object>> statistics = new HashMap<>();

    for (Orders order : orderList) {
//      orderIdList.add(Math.toIntExact(order.getId()));
//    }
      /*    构建商品表查询条件*/
      QueryWrapper<Good> goodsWrapper = new QueryWrapper<>();
      goodsWrapper.eq("key_id", Math.toIntExact(order.getId()));
      /*查询并统计结果*/
      List<Good> goodsList = goodMapper.selectList(goodsWrapper);

      for (Good goods : goodsList) {
        String category = goods.getGoodSubclass();
        String name = goods.getGoodName();
        double price = goods.getGoodPrice();
        Long quantity = goods.getGoodNumber();

        String category_name = category + "_" + name;
        /*  结果是否包含*/
        if (!statistics.containsKey(category_name)) {
          statistics.put(category_name, new HashMap<>());
        }

        Map<String, Object> categoryStats = statistics.get(category_name);
        if (!categoryStats.containsKey("原价")) {
          categoryStats.put("原价",
              Integer.parseInt(String.valueOf(categoryStats.getOrDefault("原价", 0) ))+ goods.getGoodCost());
        }

//        if (!categoryStats.containsKey(name)) {
//          categoryStats.put(name, new HashMap<>());
//        }
//
//        Map<String, Object> productStats = (Map<String, Object>) categoryStats.get(name);

        switch (order.getOrderType()) {
          case "新订":
            categoryStats.put("新订(笔数)",
                Integer.parseInt(String.valueOf(categoryStats.getOrDefault("新订(笔数)", 0) ))+ 1);
            categoryStats.put("新订(数量)",
                Integer.parseInt(String.valueOf(categoryStats.getOrDefault("新订(数量)", 0))) + quantity);
            categoryStats.put("新订(金额)",
                Double.parseDouble(String.valueOf(categoryStats.getOrDefault("新订(金额)", 0.0))) + price * quantity);
            break;
          case "退货":
            categoryStats.put("退货(笔数)",
                Integer.parseInt(String.valueOf(categoryStats.getOrDefault("退货(笔数)", 0))) + 1);
            categoryStats.put("退货(数量)",
                Integer.parseInt(String.valueOf(categoryStats.getOrDefault("退货(数量)", 0))) + quantity);
            categoryStats.put("退货(金额)",
                Double.parseDouble(String.valueOf(categoryStats.getOrDefault("退货(金额)", 0.0))) + price * quantity);
            break;
          case "换货":
            categoryStats.put("换货(笔数)",
                Integer.parseInt(String.valueOf(categoryStats.getOrDefault("换货(笔数)", 0))) + 1);
            categoryStats.put("换货(数量)",
                Integer.parseInt(String.valueOf(categoryStats.getOrDefault("换货(数量)", 0))) + quantity);
            categoryStats.put("换货(金额)",
                Double.parseDouble(String.valueOf(categoryStats.getOrDefault("换货(金额)", 0.0))) + price * quantity);

            break;
          case "退订":
            categoryStats.put("退订(笔数)",
                Integer.parseInt(String.valueOf(categoryStats.getOrDefault("退订(笔数)", 0))) + 1);
            categoryStats.put("退订(数量)",
                Integer.parseInt(String.valueOf(categoryStats.getOrDefault("退订(数量)", 0))) + quantity);
            categoryStats.put("退订(金额)",
                Double.parseDouble(String.valueOf(categoryStats.getOrDefault("退订(金额)", 0.0))) + price * quantity);
            break;
        }
      }
    }
    List<CreaterWork> res=getCreaterWorkMap(statistics, String.valueOf(map.get("creater")));
    PageInfo<CreaterWork> resinfo=new PageInfo<>(res);
    return resinfo;
  }

  @Override
  public Orders getOrderByid(Map<String, Object> map) {
    return orderMapper.selectById(Long.valueOf(String.valueOf(map.get("id"))));
  }


  public List<CreaterWork> getCreaterWorkMap(Map<String, Map<String, Object>> map,String creater){
    List<CreaterWork> createrWorkList=new ArrayList<>();
    for(Map.Entry<String, Map<String, Object>> entry : map.entrySet()){
      CreaterWork createrWork=new CreaterWork();
      String goodClass= entry.getKey().split("_")[0];
      String goodName= entry.getKey().split("_")[1];
      createrWork.setCreater(creater);
      createrWork.setGoodName(goodName);
      createrWork.setGoodSubclass(goodClass);

      Map<String, Object> message=entry.getValue();
      createrWork.setNewOrderNum(Integer.parseInt(String.valueOf(message.getOrDefault("新订(笔数)",0))));
      createrWork.setNewGoodNum(Integer.parseInt(String.valueOf(message.getOrDefault("新订(数量)",0))));
      createrWork.setNewOrderMoney(Double.parseDouble(String.valueOf(message.getOrDefault("新订(金额)"
          ,0.0))));

      createrWork.setReturnOrderNum(Integer.parseInt(String.valueOf(message.getOrDefault("退货(笔数)"
          ,0))));
      createrWork.setReturnGoodNum(Integer.parseInt(String.valueOf(message.getOrDefault("退货(数量)",0))));
      createrWork.setReturnOrderMoney(Double.parseDouble(String.valueOf(message.getOrDefault("退货(金额)",0.0))));

      createrWork.setChangeOrderNum(Integer.parseInt(String.valueOf(message.getOrDefault("换货(笔数)"
          ,0))));
      createrWork.setChangeGoodNum(Integer.parseInt(String.valueOf(message.getOrDefault("换货(数量)",0))));
      createrWork.setChangeOrderMoney(Double.parseDouble(String.valueOf(message.getOrDefault("换货(金额)",0.0))));

      createrWork.setUnsubOrderNum(Integer.parseInt(String.valueOf(message.getOrDefault("退订(笔数)"
          ,0))));
      createrWork.setUnsubGoodNum(Integer.parseInt(String.valueOf(message.getOrDefault("退订(数量)",0))));
      createrWork.setUnsubOrderMoney(Double.parseDouble(String.valueOf(message.getOrDefault("退订"
          + "(金额)",0.0))));

      double money=
          createrWork.getNewGoodNum()*Double.parseDouble(String.valueOf(message.getOrDefault(
              "单价",0.0)));
      createrWork.setIncome(money);
      createrWorkList.add(createrWork);
    }
    return createrWorkList;
  }

}
