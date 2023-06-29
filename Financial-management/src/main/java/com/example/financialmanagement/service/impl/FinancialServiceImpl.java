package com.example.financialmanagement.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.financialmanagement.beans.HttpResponseEntity;
import com.example.financialmanagement.entity.Buy;
import com.example.financialmanagement.entity.Good;
import com.example.financialmanagement.entity.Orders;
import com.example.financialmanagement.entity.form.MoneyStation;
import com.example.financialmanagement.entity.vo.ResultStation;
import com.example.financialmanagement.feign.FeignApi;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.ArrayList;
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
   private FeignApi feignApi;

  public ResultStation settlementStation(Map<String, Object> map)  {
    PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
        Integer.valueOf(String.valueOf(map.get("pageSize"))));

    HttpResponseEntity res= feignApi.getOrderByStationFin(map);
    String jsonString1 = JSON.toJSONString(res.getData());  // 将对象转换成json格式数据
//    JSONObject jsonObject = JSON.parseObject(jsonString1); // 在转回去
//    System.out.println("test");
    List<Orders> orderList=JSON.parseArray(jsonString1, Orders.class);

    Map<String, Map<String, Object>> statistics = new HashMap<>();
    Double getMoney=0.0;
    Double returnMoney=0.0;
    for (Orders order : orderList) {

      HttpResponseEntity resgood= feignApi.getGoodByOrderId(Math.toIntExact(order.getId()));
      /*查询并统计结果*/
      List<Good> goodsList = (List<Good>) resgood.getData();

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
        switch (order.getOrderType()) {
          case "新订":
            categoryStats.put("新订(数量)",
                Integer.parseInt(String.valueOf(categoryStats.getOrDefault("新订(数量)", 0))) + quantity);
            categoryStats.put("新订(金额)",
                Double.parseDouble(String.valueOf(categoryStats.getOrDefault("新订(金额)", 0.0))) + price * quantity);
            getMoney+=price * quantity;
            break;
          case "退货":
            categoryStats.put("退货(数量)",
                Integer.parseInt(String.valueOf(categoryStats.getOrDefault("退货(数量)", 0))) + quantity);
            categoryStats.put("退货(金额)",
                Double.parseDouble(String.valueOf(categoryStats.getOrDefault("退货(金额)", 0.0))) + price * quantity);
            returnMoney+=price * quantity;
            break;

        }
      }
    }
    List<MoneyStation> reslist=getMoneyStationMap(statistics);
    PageInfo<MoneyStation> resinfo=new PageInfo<>(reslist);
    ResultStation resultStation =new ResultStation();
    resultStation.setPageInfo(resinfo);
    resultStation.setGetMoney(getMoney);
    resultStation.setReturnMoney(returnMoney);
    resultStation.setSumMoney(getMoney-returnMoney);
    return resultStation;
  }

  public List<MoneyStation> getMoneyStationMap(Map<String, Map<String, Object>> map){
    List<MoneyStation> moneyStationList =new ArrayList<>();
    int id=1;
    for(Map.Entry<String, Map<String, Object>> entry : map.entrySet()){
      MoneyStation moneyStation =new MoneyStation();
      moneyStation.setId(id);
      id++;
      String goodClass= entry.getKey().split("_")[0];
      String goodName= entry.getKey().split("_")[1];

      moneyStation.setGoodName(goodName);
      moneyStation.setGoodClass(goodClass);

      Map<String, Object> message=entry.getValue();
      moneyStation.setGoodGetNumber(Integer.parseInt(String.valueOf(message.getOrDefault("新订(数量)",0))));
      moneyStation.setGoodGetMoney(Double.parseDouble(String.valueOf(message.getOrDefault("新订(金额)"
          ,0.0))));

      moneyStation.setGoodReturnNumber(Integer.parseInt(String.valueOf(message.getOrDefault("退货(数量)",0))));
      moneyStation.setGoodReturnMoney(Double.parseDouble(String.valueOf(message.getOrDefault("退货(金额)",
          0.0))));
      moneyStationList.add(moneyStation);
    }
    return moneyStationList;
  }


  public ResultStation settlementSupply(Map<String, Object> map)  {
    PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
        Integer.valueOf(String.valueOf(map.get("pageSize"))));

    HttpResponseEntity res= feignApi.getBuyBySupplyFin(map);
    String jsonString1 = JSON.toJSONString(res.getData());  // 将对象转换成json格式数据
//    JSONObject jsonObject = JSON.parseObject(jsonString1); // 在转回去
//    System.out.println("test");
    List<Buy> buyList=JSON.parseArray(jsonString1, Buy.class);

    Map<String, Map<String, Object>> statistics = new HashMap<>();
    Double sumMoney=0.0;
    for (Buy buy : buyList) {

      HttpResponseEntity resgood= feignApi.getGoodByOrderId(Math.toIntExact(buy.getGoodId()));
      /*查询并统计结果*/
      List<Good> goodsList = (List<Good>) resgood.getData();

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
        switch (order.getOrderType()) {
          case "新订":
            categoryStats.put("新订(数量)",
                Integer.parseInt(String.valueOf(categoryStats.getOrDefault("新订(数量)", 0))) + quantity);
            categoryStats.put("新订(金额)",
                Double.parseDouble(String.valueOf(categoryStats.getOrDefault("新订(金额)", 0.0))) + price * quantity);
            getMoney+=price * quantity;
            break;
          case "退货":
            categoryStats.put("退货(数量)",
                Integer.parseInt(String.valueOf(categoryStats.getOrDefault("退货(数量)", 0))) + quantity);
            categoryStats.put("退货(金额)",
                Double.parseDouble(String.valueOf(categoryStats.getOrDefault("退货(金额)", 0.0))) + price * quantity);
            returnMoney+=price * quantity;
            break;

        }
      }
    }
    List<MoneyStation> reslist=getMoneyMap(statistics);
    PageInfo<MoneyStation> resinfo=new PageInfo<>(reslist);
    ResultStation resultStation =new ResultStation();
    resultStation.setPageInfo(resinfo);
    resultStation.setGetMoney(getMoney);
    resultStation.setReturnMoney(returnMoney);
    resultStation.setSumMoney(getMoney-returnMoney);
    return resultStation;
  }

}
