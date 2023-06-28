package com.example.financialmanagement.service.impl;

import com.example.financialmanagement.beans.HttpResponseEntity;
import com.example.financialmanagement.entity.Good;
import com.example.financialmanagement.entity.Orders;
import com.example.financialmanagement.entity.form.Money;
import com.example.financialmanagement.entity.vo.Result;
import com.example.financialmanagement.feign.FeignApi;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.text.ParseException;
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

  public Result settlementSupply(Map<String, Object> map) throws ParseException {
    PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
        Integer.valueOf(String.valueOf(map.get("pageSize"))));

    HttpResponseEntity res= feignApi.getOrderByStationFin(map);

    List<Orders> orderList = (List<Orders>) res.getData();

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

//        if (!categoryStats.containsKey(name)) {
//          categoryStats.put(name, new HashMap<>());
//        }
//
//        Map<String, Object> productStats = (Map<String, Object>) categoryStats.get(name);

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
    List<Money> reslist=getMoneyMap(statistics);
    PageInfo<Money> resinfo=new PageInfo<>(reslist);
    Result result=new Result();
    result.setPageInfo(resinfo);
    result.setGetMoney(getMoney);
    result.setReturnMoney(returnMoney);
    result.setSumMoney(getMoney-returnMoney);
    return result;
  }

  public List<Money> getMoneyMap(Map<String, Map<String, Object>> map){
    List<Money> moneyList=new ArrayList<>();
    for(Map.Entry<String, Map<String, Object>> entry : map.entrySet()){
      Money money=new Money();
      String goodClass= entry.getKey().split("_")[0];
      String goodName= entry.getKey().split("_")[1];

      money.setGoodName(goodName);
      money.setGoodClass(goodClass);

      Map<String, Object> message=entry.getValue();
      money.setGoodGetNumber(Integer.parseInt(String.valueOf(message.getOrDefault("新订(数量)",0))));
      money.setGoodGetMoney(Double.parseDouble(String.valueOf(message.getOrDefault("新订(金额)"
          ,0.0))));

      money.setGoodReturnNumber(Integer.parseInt(String.valueOf(message.getOrDefault("退货(数量)",0))));
      money.setGoodReturnMoney(Double.parseDouble(String.valueOf(message.getOrDefault("退货(金额)",
          0.0))));
      moneyList.add(money);
    }
    return moneyList;
  }

}
