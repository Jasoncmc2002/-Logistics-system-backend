package com.example.warehousemanagementcentre.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.warehousemanagementcentre.beans.HttpResponseEntity;
import com.example.warehousemanagementcentre.entity.*;
import com.example.warehousemanagementcentre.feign.FeignApi;
import com.example.warehousemanagementcentre.mapper.CentralStationMapper;
import com.example.warehousemanagementcentre.mapper.InoutstationMapper;
import com.example.warehousemanagementcentre.service.CentralstationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 中心库房存量  服务实现类
 * </p>
 *
 * @author hzn
 * @since 2023-06-25
 */
@Service
public class CentralStationServiceImpl extends ServiceImpl<CentralStationMapper, CentralStation> implements CentralstationService {

    @Autowired
    private CentralStationMapper centralStationMapper;

    @Autowired
    private InoutstationMapper inoutstationMapper;


    @Autowired
    private FeignApi feignApi;

    @Override
    public int updatebyId(CentralStation centralStation) {
        int res=centralStationMapper.updateById(centralStation);
        return res;
    }

    @Override
    public PageInfo selectBuy(Map<String, Object> map) {
        PageHelper.startPage(Integer.valueOf((String)map.get("pageNum")), Integer.valueOf((String)map.get("pageSize")));
        String id = String.valueOf(map.get("id"));
        HttpResponseEntity res= feignApi.selectBuy(id);
        String jsonString1 = JSON.toJSONString(res.getData());  // 将对象转换成json格式数据
        JSONObject jsonObject = JSON.parseObject(jsonString1); // 在转回去
        Buy buy = JSON.parseObject(String.valueOf(jsonObject), Buy.class);
        List<Buy> buys = new ArrayList<>();
        buys.add(buy);
        PageInfo pageInfo = new PageInfo(buys);
        return pageInfo;
    }

    @Override
    public int toInStation(Map<String, Object> map) {
        String jsonString1 = JSON.toJSONString(map.get("Buy"));  // 将对象转换成json格式数据
        JSONObject jsonObject = JSON.parseObject(jsonString1); // 在转回去
        Buy buy = JSON.parseObject(String.valueOf(jsonObject), Buy.class);

        String id = String.valueOf(buy.getId());
        HttpResponseEntity resBuy = feignApi.selectBuy(id);
//        System.out.println("2222222"+resBuy);
        String jsonString4 = JSON.toJSONString(resBuy.getData());  // 将对象转换成json格式数据
        JSONObject jsonObject4 = JSON.parseObject(jsonString4); // 在转回去
        Buy buy1 = JSON.parseObject(String.valueOf(jsonObject4), Buy.class);
        buy1.setGoodId(buy.getGoodId());
//        System.out.println("1111111"+buy1);


//        HttpResponseEntity res1= feignApi.getGood(buy.getGoodId());

        //通过buy获取good
        Map map1 = new HashMap<>();
        map1.put("goodId",buy.getGoodId());
        map1.put("pageNum",1);
        map1.put("pageSize",2);
//        System.out.println("feign之前");
        HttpResponseEntity res1= feignApi.getGood(map1);
//        System.out.println(res1);
//        System.out.println(res1.getData());
        String jsonString2 = JSON.toJSONString(res1.getData());  // 将对象转换成json格式数据
        JSONArray jsonArray2 = JSON.parseArray(jsonString2); // 在转回去
        List<Good> goods = JSON.parseArray(String.valueOf(jsonArray2), Good.class);

        Good good = goods.get(0);
        System.out.println(good);


        //根据good和number修改中心库房库存
        CentralStation centralStation = centralStationMapper.selectById(good.getId());
//        centralStation.setGoodClass(good.getGoodClass());
        centralStation.setGoodName(good.getGoodName());
        centralStation.setWaitAllo(centralStation.getWaitAllo()+(int)map.get("number"));
        centralStation.setStock(centralStation.getWithdrawal()+ centralStation.getDoneAllo()+ centralStation.getWaitAllo());
        int res = centralStationMapper.updateById(centralStation);


        Inoutstation inoutstation = new Inoutstation();
        inoutstation.setStationClass(1);
        inoutstation.setAlloId(buy.getId());
        inoutstation.setStationId(Long.valueOf("1"));//调货id设为进货单buy的id
        inoutstation.setTaskId(Long.valueOf("0"));//进货没有task，设为0
        inoutstation.setGoodId(good.getId());//从找到的good获取id
//        inoutstation.setGoodClass(good.getGoodClass());//从找到的good获取class
        inoutstation.setGoodPrice(good.getGoodPrice());
        inoutstation.setGoodName(good.getGoodName());
        inoutstation.setGoodUnit(good.getGoodUnit());
        inoutstation.setGoodFactory(good.getGoodFactory());//从找到的good获取factory


        Long number = Long.valueOf(String.valueOf(map.get("number")));
        inoutstation.setNumber(number);////从传入的map获取进货数量number
        Date date = new Date(System.currentTimeMillis());
        inoutstation.setDate(date);//获取现在的时间为进货时间
        inoutstation.setType("中心库房入库");//设入库类型为中心库房入库
        inoutstation.setRemark("0");//设备注为0
        inoutstation.setId(null);
        int res2 = inoutstationMapper.insert(inoutstation);

        //更新进货单状态
        buy1.setBuyType(Long.valueOf("1"));
        HttpResponseEntity res3 = feignApi.updateBuy(buy1);
        System.out.println(res3);

        return res2;
    }

    @Override
    public int toOutStation(Map<String, Object> map) {
        String jsonString1 = JSON.toJSONString(map.get("InOutStation"));  // 将对象转换成json格式数据
        JSONObject jsonObject = JSON.parseObject(jsonString1); // 在转回去
        Inoutstation inoutstation = JSON.parseObject(String.valueOf(jsonObject), Inoutstation.class);



        //根据good和number修改中心库房库存
        CentralStation centralStation = centralStationMapper.selectById(inoutstation.getGoodId());
        centralStation.setGoodName(inoutstation.getGoodName());
        centralStation.setWaitAllo((int) (centralStation.getWaitAllo()-inoutstation.getNumber()));
        centralStation.setDoneAllo((int) (centralStation.getDoneAllo()+inoutstation.getNumber()));
        int res = centralStationMapper.updateById(centralStation);

        //传入的出入库表里面的出入库类型应改为调拨出库
        inoutstation.setType("中心库房出库");
//        int res1 = inoutstationMapper.updateById(inoutstation);
        inoutstation.setId(null);
        int res1 = inoutstationMapper.insert(inoutstation);

        return res1;
    }

    @Override
    public int toInSubstation(Map<String, Object> map) {
        //根据传入的中心库房出库单号得到出库单对象，之后相当于复制这个中心库房的出库单里面的其他信息，add一个分站库房入库单
        String jsonString1 = JSON.toJSONString(map.get("InOutStation"));  // 将对象转换成json格式数据
        JSONObject jsonObject = JSON.parseObject(jsonString1); // 在转回去
        Inoutstation inoutstation = JSON.parseObject(String.valueOf(jsonObject), Inoutstation.class);
        Inoutstation inoutstation1 = inoutstationMapper.selectById(inoutstation.getId());
        inoutstation1.setId(null);

        inoutstation1.setStationClass(2);
        inoutstation1.setType("分站入库");
//        System.out.println(inoutstation.getStationId());



        Map map1 =new HashMap<String,Object>();
        map1.put("id",inoutstation.getTaskId());
        System.out.println(inoutstation.getTaskId());
        map1.put("task_status","可分配");
        map1.put("order_status","配送站到货");

        HttpResponseEntity res= feignApi.changeTaskOrderType(map1);
        inoutstation1.setId(null);
        inoutstationMapper.insert(inoutstation1);
        int res1= (int) res.getData();  // 将对象转换成json格式数据
        return res1;
    }
    @Override
    public int takeGoods(Map<String, Object> map) {
        //得到分站库房入库单，知道它要从分站出去，也就是领货（按单领，而不是按货领）
        String jsonString1 = JSON.toJSONString(map.get("InOutStation"));  // 将对象转换成json格式数据
        JSONObject jsonObject = JSON.parseObject(jsonString1); // 在转回去
        Inoutstation inoutstation = JSON.parseObject(String.valueOf(jsonObject), Inoutstation.class);

        inoutstation.setType("分站领货出库");

        Map map1 =new HashMap<String,Object>();
        map1.put("id",inoutstation.getTaskId());
        map1.put("task_status","已领货");
        map1.put("order_status","已领货");

        HttpResponseEntity res= feignApi.changeTaskOrderType(map1);

        int res1= (int) res.getData();  // 将对象转换成json格式数据
        inoutstation.setId(null);
        inoutstationMapper.insert(inoutstation);
        return res1;
    }

    @Override
    public int returnGoodsToSub(Map<String, Object> map) {
//        String jsonString1 = JSON.toJSONString(map.get("Good"));  // 将对象转换成json格式数据
//        JSONObject jsonObject = JSON.parseObject(jsonString1); // 在转回去
//        Good good = JSON.parseObject(String.valueOf(jsonObject), Good.class);

        //传入要退货的good_id得到分站出库表的对应inoutstation
        Map map1 = new HashMap<>();
        map1.put("goodId", map.get("goodId"));
        map1.put("pageNum", 1);
        map1.put("pageSize", 2);
        HttpResponseEntity res1 = feignApi.getGood(map1);

        int res = 0;
        //判断有无货物
        if (res1.getData() == null) {
            res = 3;

        } else {
            String jsonString2 = JSON.toJSONString(res1.getData());  // 将对象转换成json格式数据
            JSONArray jsonArray2 = JSON.parseArray(jsonString2); // 在转回去
            List<Good> goods = JSON.parseArray(String.valueOf(jsonArray2), Good.class);

            Good good = goods.get(0);
            System.out.println("?????" + good);

//
//        HttpResponseEntity goodRes = feignApi.getGoodById(String.valueOf(map.get("goodId")));
//        String jsonString1 = JSON.toJSONString(goodRes.getData());  // 将对象转换成json格式数据
//        Good good = JSON.parseObject(jsonString1, Good.class);


            //判断该商品是否允许退货
            System.out.println(good.getIsChange());
            if (good.getIsChange() == 1) {
                System.out.println("!!!!!");
                QueryWrapper<Inoutstation> inoutstationQueryWrapper = new QueryWrapper<>();
                inoutstationQueryWrapper.eq("good_id", good.getId());
                List<Inoutstation> inoutstations = inoutstationMapper.selectList(inoutstationQueryWrapper);
                Inoutstation inoutstation = inoutstations.get(0);
                System.out.println(inoutstations);
                System.out.println("Inoutstation!!!" + inoutstation);
//            System.out.println(inoutstationMapper.selectList(inoutstationQueryWrapper));
//            Inoutstation inoutstation = (Inoutstation) inoutstationMapper.selectList(inoutstationQueryWrapper);
                //改对应inoutstation的出入库状态，改为入库（退回分站库房）

                inoutstation.setType("分站退货入库");
                inoutstation.setId(null);
                res = inoutstationMapper.insert(inoutstation);
            }else {
                res = 2;
            }
        }
        return res;
    }

    @Override
    public int returnGoodsToCenter(Map<String, Object> map) {
        //传入要退货的good_id得到分站出库表的对应inoutstation
        Map map1 = new HashMap<>();
        map1.put("goodId",map.get("goodId"));
        map1.put("pageNum",1);
        map1.put("pageSize",2);
        HttpResponseEntity res1= feignApi.getGood(map1);
        System.out.println(res1);
        System.out.println("res1.getData()"+res1.getData());

//        //传入要退货的good_id得到出入库表的对应商品，可知是否可以退货
//        HttpResponseEntity goodRes = feignApi.getGoodById(String.valueOf(map.get("goodId")));
//        String jsonString1 = JSON.toJSONString(goodRes.getData());  // 将对象转换成json格式数据
//        Good good = JSON.parseObject(jsonString1, Good.class);

        int res = 0;
        if(res1.getData() == null){
            res = 3;

        }else {
            String jsonString2 = JSON.toJSONString(res1.getData());  // 将对象转换成json格式数据
            System.out.println(jsonString2);
            JSONArray jsonArray2 = JSON.parseArray(jsonString2); // 在转回去
            List<Good> goods = JSON.parseArray(String.valueOf(jsonArray2), Good.class);

            Good good = goods.get(0);

            if(good.getIsChange() == 1){
                //判断该商品是否允许退货
                QueryWrapper<Inoutstation> inoutstationQueryWrapper = new QueryWrapper<>();
                inoutstationQueryWrapper.eq("good_id",good.getId());
//            Inoutstation inoutstation = (Inoutstation) inoutstationMapper.selectList(inoutstationQueryWrapper);
                List<Inoutstation> inoutstations = inoutstationMapper.selectList(inoutstationQueryWrapper);
                Inoutstation inoutstation = inoutstations.get(0);
                System.out.println(inoutstations);
                System.out.println("Inoutstation!!!"+inoutstation);
                //改对应inoutstation的出入库状态，改为入库（退回分站库房）
                inoutstation.setType("分站退回中心库房");
                Long number = Long.valueOf(String.valueOf(map.get("number")));
                inoutstation.setNumber(number);


                //根据good修改中心库房库存
                CentralStation centralStation = centralStationMapper.selectById(inoutstation.getGoodId());
                centralStation.setGoodName(inoutstation.getGoodName());
                centralStation.setWithdrawal((int) (centralStation.getWithdrawal()+inoutstation.getNumber()));
                centralStation.setDoneAllo((int) (centralStation.getDoneAllo()-inoutstation.getNumber()));
                int res2 = centralStationMapper.updateById(centralStation);


                inoutstation.setId(null);
                res = inoutstationMapper.insert(inoutstation);
            }else {
                res = 2;
            }
        }


        return res;
    }


}
