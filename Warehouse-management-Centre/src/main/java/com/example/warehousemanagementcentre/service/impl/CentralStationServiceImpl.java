package com.example.warehousemanagementcentre.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.warehousemanagementcentre.beans.HttpResponseEntity;
import com.example.warehousemanagementcentre.entity.*;
import com.example.warehousemanagementcentre.entity.vo.ResultInCentral;
import com.example.warehousemanagementcentre.feign.FeignApi;
import com.example.warehousemanagementcentre.mapper.AllocationMapper;
import com.example.warehousemanagementcentre.mapper.CentralStationMapper;
import com.example.warehousemanagementcentre.mapper.InoutstationMapper;
import com.example.warehousemanagementcentre.service.CentralstationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
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
    private AllocationMapper allocationMapper;


    @Autowired
    private FeignApi feignApi;


    //有无buyid-->有就返回一个buy没有就全体buy-->遍历前面得到的buy-->每个buy找到对应的goodId然后找到对应的central和对应的第一和第二类别-->构造给前端的返回值
    @Override
    public PageInfo searchInCentral(Map<String, Object> map) {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
                Integer.valueOf(String.valueOf(map.get("pageSize"))));

        List<ResultInCentral> resultInCentrals = new ArrayList<>();
        List<Buy> buys = new ArrayList<>();
        if (!map.get("id").equals("")){
            System.out.println("map1"+map);
            //通过buy_id得到购物单信息
            HttpResponseEntity resBuy1 = feignApi.selectBuy(String.valueOf(map.get("id")));
            if (resBuy1.getData() != null){
                String jsonString1 = JSON.toJSONString(resBuy1.getData());  // 将对象转换成json格式数据
                JSONObject jsonObject1 = JSON.parseObject(jsonString1); // 在转回去
                Buy buy = JSON.parseObject(String.valueOf(jsonObject1), Buy.class);
                buys.add(buy);
            }

        }else {
            //没传buyId
            HttpResponseEntity resBuys = feignApi.getListByConditions1(map);
            String jsonString2 = JSON.toJSONString(resBuys.getData());  // 将对象转换成json格式数据
            System.out.println(jsonString2);
            JSONArray jsonArray2 = JSON.parseArray(jsonString2); // 在转回去
            buys = JSON.parseArray(String.valueOf(jsonArray2), Buy.class);
            System.out.println(buys);
        }

        for (Buy buy:buys){
            CentralStation centralStation = centralStationMapper.selectById(buy.getGoodId());
            System.out.println("centralStation"+centralStation);
            if (centralStation != null){
                ResultInCentral resultInCentral = new ResultInCentral();
                resultInCentral.setCentralGoodId(centralStation.getId());
                resultInCentral.setSupply(centralStation.getSupplyId());
                //buy-购货日期
                resultInCentral.setBuyDate(buy.getDate());
//                Date date = new Date();
//                date.setTime(System.currentTimeMillis());
//                resultInCentral.setReceiptDate(date);
                resultInCentral.setGoodName(centralStation.getGoodName());

                HttpResponseEntity resFirst = feignApi.getById1(String.valueOf(centralStation.getGoodClassId()));
                String jsonString1 = JSON.toJSONString(resFirst.getData());  // 将对象转换成json格式数据
                JSONObject jsonObject1 = JSON.parseObject(jsonString1); // 在转回去
                FirstCategory firstCategory = JSON.parseObject(String.valueOf(jsonObject1), FirstCategory.class);
                HttpResponseEntity resSecond = feignApi.getById2(String.valueOf(centralStation.getGoodSubclassId()));
                String jsonString2 = JSON.toJSONString(resSecond.getData());  // 将对象转换成json格式数据
                JSONObject jsonObject2 = JSON.parseObject(jsonString2); // 在转回去
                SecondaryCategory secondaryCategory = JSON.parseObject(String.valueOf(jsonObject2), SecondaryCategory.class);

                resultInCentral.setGoodClass(firstCategory.getFName());
                resultInCentral.setGoodSubclass(secondaryCategory.getSName());
                resultInCentral.setGoodUnit(centralStation.getGoodUnit());
                resultInCentral.setBuyNumber(buy.getNumber());
                resultInCentrals.add(resultInCentral);
                System.out.println(resultInCentral);
            }

        }

        //设置给前端的返回值
        PageInfo pageInfo = new PageInfo(resultInCentrals);


        return pageInfo;
    }

    //给对应的buyId找对应的central-->改库存-->得到对应good(goodFactory)-->形成出入库单（”调拨出库“）
    @Override
    public int inCentral(Map<String, Object> map) {
//        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
//                Integer.valueOf(String.valueOf(map.get("pageSize"))));

        ZoneId chinaZoneId = ZoneId.of("Asia/Shanghai");
        // 格式化中国时区时间为指定格式的字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //入库改库存
        CentralStation centralStation = centralStationMapper.selectById((int)map.get("goodId"));
        centralStation.setWaitAllo(centralStation.getWaitAllo()+(int)map.get("number"));
        centralStation.setStock(centralStation.getWaitAllo()+centralStation.getWithdrawal()+centralStation.getDoneAllo());
        int res1 = centralStationMapper.updateById(centralStation);
        System.out.println(res1);

        //得到good
        Map map1 = new HashMap<>();
        map1.put("goodId",map.get("goodId"));
        map1.put("pageNum",1);
        map1.put("pageSize",2);
        HttpResponseEntity res2= feignApi.getGood(map1);
        String jsonString2 = JSON.toJSONString(res2.getData());  // 将对象转换成json格式数据
        System.out.println(jsonString2);
        JSONArray jsonArray2 = JSON.parseArray(jsonString2); // 在转回去
        List<Good> goods = JSON.parseArray(String.valueOf(jsonArray2), Good.class);
        Good good = goods.get(goods.size()-1);

        //入库加入库表
        Inoutstation inoutstation = new Inoutstation();
        inoutstation.setStationClass(1);
        inoutstation.setAlloId(Long.valueOf(0));
        inoutstation.setStationId(Long.valueOf(1));
        inoutstation.setStationName("中心库房");
        inoutstation.setTaskId(Long.valueOf(0));
        inoutstation.setGoodId(Long.valueOf(centralStation.getId()));
        inoutstation.setGoodPrice(centralStation.getGoodPrice());
        inoutstation.setGoodName(centralStation.getGoodName());
        inoutstation.setGoodUnit(centralStation.getGoodUnit());
        inoutstation.setGoodFactory(good.getGoodFactory());
        inoutstation.setSigner(String.valueOf(map.get("signer")));
        inoutstation.setNumber(Long.valueOf(String.valueOf(map.get("number"))));


        String date = LocalDateTime.parse(String.valueOf(map.get("date")), DateTimeFormatter.ISO_DATE_TIME).atZone(
                ZoneOffset.UTC).withZoneSameInstant(chinaZoneId).format(formatter);
        try {
            inoutstation.setDate(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        inoutstation.setType("调拨出库");
        inoutstation.setRemark((String) map.get("remark"));
        int res3 = inoutstationMapper.insert(inoutstation);

        return res3;
    }

    //转化传入的goods列表和得到传入的调拨单-->遍历good,通过goodName得到对应的central-->如果库存足够就出库-->形成中心出库单（”调拨入库“）-->改任务单订单和调拨单状态
    @Override
    public int outCentral(Map<String, Object> map) {
//        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
//                Integer.valueOf(String.valueOf(map.get("pageSize"))));
        System.out.println("map!"+map);
        ZoneId chinaZoneId = ZoneId.of("Asia/Shanghai");
        // 格式化中国时区时间为指定格式的字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //通过传入货物表信息
        String jsonString1 = JSON.toJSONString(map.get("goods"));  // 将对象转换成json格式数据
        System.out.println(jsonString1);
        JSONArray jsonArray2 = JSON.parseArray(jsonString1); // 在转回去
        List<Good> goods = JSON.parseArray(String.valueOf(jsonArray2), Good.class);
        System.out.println("goods###"+goods);
//        Good good = goods.get(goods.size()-1);

        QueryWrapper<Allocation> allocationQueryWrapper = new QueryWrapper<>();
        allocationQueryWrapper.eq("id", map.get("alloId"));
        List<Allocation> allocations = allocationMapper.selectList(allocationQueryWrapper);
        Allocation allocation = allocations.get(0);

        //goodlist依次入库
        int res = 0;
        for(Good good:goods){
            //中心库存中对应的数据
            QueryWrapper<CentralStation> centralStationQueryWrapper = new QueryWrapper<>();
            centralStationQueryWrapper.eq("id", good.getGoodId());
            List<CentralStation> centralStations = centralStationMapper.selectList(centralStationQueryWrapper);
            CentralStation centralStation = centralStations.get(0);
            if(centralStation == null){
                res = 3;
            }else {
                System.out.println("good???"+good);
                System.out.println("good.getGoodNumber()!!"+good.getGoodNumber());
                if(centralStation.getWaitAllo() >= good.getGoodNumber()){
                    //可分配库存足够，依次出库
                    centralStation.setDoneAllo(centralStation.getDoneAllo()+good.getGoodNumber());
                    centralStation.setWaitAllo(centralStation.getWaitAllo()-good.getGoodNumber());
                    centralStation.setStock(centralStation.getWaitAllo()+centralStation.getWithdrawal()+centralStation.getDoneAllo());
                    res = centralStationMapper.updateById(centralStation);
                    System.out.println("res"+res);



                    //出库加出库表
                    Inoutstation inoutstation = new Inoutstation();
                    inoutstation.setStationClass(1);
                    inoutstation.setAlloId(Long.valueOf(String.valueOf(map.get("alloId"))));
                    inoutstation.setStationId(allocation.getOutStationId());
                    inoutstation.setStationName(allocation.getOutStationName());
                    inoutstation.setTaskId(allocation.getTaskId());
                    inoutstation.setGoodId(Long.valueOf(centralStation.getId()));
                    inoutstation.setGoodPrice(centralStation.getGoodPrice());
                    inoutstation.setGoodName(centralStation.getGoodName());
                    inoutstation.setGoodUnit(centralStation.getGoodUnit());
                    inoutstation.setGoodFactory(good.getGoodFactory());
                    System.out.println("good.getGoodNumber()!!"+good.getGoodNumber());
                    inoutstation.setNumber(Long.valueOf(good.getGoodNumber()));

                    //设置时间
                    String dateTime = LocalDateTime.parse(String.valueOf(map.get("date")), DateTimeFormatter.ISO_DATE_TIME).atZone(
                            ZoneOffset.UTC).withZoneSameInstant(chinaZoneId).format(formatter);
                    Date date = null;
                    try {
                        date = simpleDateFormat.parse(dateTime);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    inoutstation.setDate(date);
                    //设置状态
                    inoutstation.setType("调拨入库");
                    inoutstation.setSigner(String.valueOf(map.get("signer")));
                    inoutstation.setDistributor(String.valueOf(map.get("distributor")));
                    inoutstation.setRemark(String.valueOf(map.get("remark")));
                    res = inoutstationMapper.insert(inoutstation);



                    //改订单状态
                    Map map1 =new HashMap<String,Object>();
                    map1.put("id",inoutstation.getTaskId());
                    System.out.println(inoutstation.getTaskId());
                    map1.put("task_status","中心库房出库");
                    map1.put("order_status",null);
                    HttpResponseEntity res2= feignApi.changeTaskOrderType(map1);

                    //改调拨单状态
                    Map map2 =new HashMap<String,Object>();
                    map2.put("id",map.get("alloId"));
                    System.out.println(map.get("alloId"));
                    map2.put("allo_type","2");
                    HttpResponseEntity res1 = feignApi.updateAllocationbyId(map2);

//                if(res && res1.getData() && res2.getData())

                }else {
                    //库存不够
                    res = 2;
                }

            }
        }


        return res;
    }



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
        inoutstation.setStationName("中心库房");//调货id设为进货单buy的id
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
        inoutstation.setType("调拨出库");//设入库类型为中心库房入库后需要出库
        inoutstation.setRemark("0");//设备注为0
        inoutstation.setId(null);
        int res2 = inoutstationMapper.insert(inoutstation);

        //修改订单状态
        Map map2 =new HashMap<String,Object>();
        map2.put("id",inoutstation.getTaskId());
        System.out.println(inoutstation.getTaskId());
        map2.put("task_status",null);
        map2.put("order_status","中心库房出库");

        HttpResponseEntity res4= feignApi.changeTaskOrderType(map1);

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
        centralStation.setWaitAllo(Long.valueOf(centralStation.getWaitAllo()-inoutstation.getNumber()));
        centralStation.setDoneAllo(Long.valueOf(centralStation.getDoneAllo()+inoutstation.getNumber()));
        int res = centralStationMapper.updateById(centralStation);

        //传入的出入库表里面的出入库类型应改为调拨出库
        inoutstation.setType("调拨入库");
//        int res1 = inoutstationMapper.updateById(inoutstation);
        inoutstation.setId(null);
        int res1 = inoutstationMapper.insert(inoutstation);

        return res1;
    }


    //得到要进行入库的goodlist-->遍历good，通过good_id得到对应的Central-->形成分站入单("调拨出库“)-->改任务单订单和调拨单状态
    @Override
    public int toInSubstation(Map<String, Object> map) {
//        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
//                Integer.valueOf(String.valueOf(map.get("pageSize"))));

        ZoneId chinaZoneId = ZoneId.of("Asia/Shanghai");
        // 格式化中国时区时间为指定格式的字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //得到要进行分站入库的good列表
        List<Good> goods = new ArrayList<>();
        String jsonString2 = JSON.toJSONString(map.get("goods"));

        JSONArray jsonArray2 = JSON.parseArray(jsonString2);
        goods = JSON.parseArray(String.valueOf(jsonArray2),Good.class);
        System.out.println("goodList"+goods);

        QueryWrapper<Allocation> allocationQueryWrapper = new QueryWrapper<>();
        allocationQueryWrapper.eq("id", map.get("alloId"));
        List<Allocation> allocations = allocationMapper.selectList(allocationQueryWrapper);
        Allocation allocation = allocations.get(0);
        System.out.println("allo!!!"+allocation);


        int res = 0;
        //按列表依次入库
        for (Good good:goods){
//            //通过传入的goodId列表获取对应good
//            Map map1 = new HashMap<>();
//            map1.put("goodId",good.getGoodId());
//            map1.put("pageNum",1);
//            map1.put("pageSize",2);
//            HttpResponseEntity res1= feignApi.getGood(map1);
//            String jsonString3 = JSON.toJSONString(res1.getData());  // 将对象转换成json格式数据
//            JSONArray jsonArray3 = JSON.parseArray(jsonString3); // 在转回去
//            List<Good> goods1 = JSON.parseArray(String.valueOf(jsonArray3), Good.class);
//            good = goods1.get(goods1.size()-1);
//            System.out.println("for");
//            System.out.println("good!!!!"+good);



            //得到对应商品信息
            QueryWrapper<CentralStation> centralStationQueryWrapper =new QueryWrapper<>();
            centralStationQueryWrapper.eq("id",good.getGoodId());
            System.out.println("good.getGoodName()"+good.getGoodName());
            List<CentralStation> centralStations = centralStationMapper.selectList(centralStationQueryWrapper);
            CentralStation centralStation = centralStations.get(0);
            System.out.println(centralStation);

            //入分站库加入库表
            Inoutstation inoutstation = new Inoutstation();
            inoutstation.setStationClass(2);
//            inoutstation.setAlloId(Long.valueOf(String.valueOf(map.get("alloId"))));
//            inoutstation.setStationName(String.valueOf(map.get("stationName")));
//            inoutstation.setStationId(Long.valueOf(String.valueOf(map.get("stationId"))));
//            inoutstation.setTaskId(Long.valueOf(String.valueOf(map.get("taskId"))));
            inoutstation.setAlloId(allocation.getId());
            inoutstation.setStationName(allocation.getInStationName());
            inoutstation.setStationId(allocation.getInStationId());
            inoutstation.setTaskId(allocation.getTaskId());
            inoutstation.setGoodId(Long.valueOf(centralStation.getId()));
            inoutstation.setGoodPrice(centralStation.getGoodPrice());
            inoutstation.setGoodName(centralStation.getGoodName());
            inoutstation.setGoodUnit(centralStation.getGoodUnit());
            inoutstation.setGoodFactory(good.getGoodFactory());
            inoutstation.setNumber(good.getGoodNumber());
            //设置时间
            String dateTime = LocalDateTime.parse(String.valueOf(map.get("date")), DateTimeFormatter.ISO_DATE_TIME).atZone(
                    ZoneOffset.UTC).withZoneSameInstant(chinaZoneId).format(formatter);
            Date date = null;
            try {
                date = simpleDateFormat.parse(dateTime);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            inoutstation.setDate(date);
//        inoutstation.setType("购货入库");
            inoutstation.setType("调拨出库");
            inoutstation.setRemark(good.getRemark());
            inoutstation.setSigner(String.valueOf(map.get("signer")));
            inoutstation.setDistributor(String.valueOf(map.get("distributor")));
            System.out.println(inoutstation);
            res = inoutstationMapper.insert(inoutstation);

        }


        //根据传入的中心库房出库单号得到出库单对象，之后相当于复制这个中心库房的出库单里面的其他信息，add一个分站库房入库单
//        String jsonString1 = JSON.toJSONString(map.get("InOutStation"));  // 将对象转换成json格式数据
//        JSONObject jsonObject = JSON.parseObject(jsonString1); // 在转回去
//        Inoutstation inoutstation = JSON.parseObject(String.valueOf(jsonObject), Inoutstation.class);
//        Inoutstation inoutstation1 = inoutstationMapper.selectById(inoutstation.getId());
//        inoutstation1.setId(null);
//
//        inoutstation1.setStationClass(2);
//        inoutstation1.setType("分站入库");
//        System.out.println(inoutstation.getStationId());



        //修改任务单状态
        Map map1 =new HashMap<String,Object>();
        map1.put("id",allocation.getTaskId());
        map1.put("task_status","可分配");
        map1.put("order_status","配送站到货");
        HttpResponseEntity res3= feignApi.changeTaskOrderType(map1);
        System.out.println("res3"+res3.getData());

        //修改配送单状态
        Map map2 =new HashMap<String,Object>();
        map2.put("id",map.get("alloId"));
        System.out.println(map.get("alloId"));
        map2.put("allo_type","3");
        HttpResponseEntity res1 = feignApi.updateAllocationbyId(map2);

        int res2= (int) res1.getData();  // 将对象转换成json格式数据
        return res2;
    }


    //传入goods转为list-->遍历list的good_id得到每个的central-->分站出库表（”已领货“）-->改任务单和订单状态
    @Override
    public int takeGoods(Map<String, Object> map) {
        ZoneId chinaZoneId = ZoneId.of("Asia/Shanghai");
        // 格式化中国时区时间为指定格式的字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//        //得到分站库房入库单，知道它要从分站出去，也就是领货（按单领，而不是按货领）
//        Map map2 =new HashMap<String,Object>();
//        map2.put("id",map.get("taskId"));
//        String jsonString1 = JSON.toJSONString(map.get("taskId"));  // 将对象转换成json格式数据
//        JSONObject jsonObject = JSON.parseObject(jsonString1); // 在转回去
//        Task task = JSON.parseObject(String.valueOf(jsonObject), Task.class);

//        inoutstation.setType("分站领货出库");


        //得到要进行分站入库的good列表
        List<Good> goods = new ArrayList<>();
        String jsonString2 = JSON.toJSONString(map.get("goods"));
        JSONArray jsonArray2 = JSON.parseArray(jsonString2);
        goods = JSON.parseArray(String.valueOf(jsonArray2),Good.class);
        System.out.println("goodList"+goods);

        int res3 = 0;
        //按列表依次入库
        for (Good good:goods){
            //通过传入的goodId列表获取对应good
//            Map map1 = new HashMap<>();
//            map1.put("goodId",good.getGoodId());
//            map1.put("pageNum",1);
//            map1.put("pageSize",2);
//            HttpResponseEntity res1= feignApi.getGood(map1);
//            String jsonString3 = JSON.toJSONString(res1.getData());  // 将对象转换成json格式数据
//            JSONArray jsonArray3 = JSON.parseArray(jsonString3); // 在转回去
//            List<Good> goods1 = JSON.parseArray(String.valueOf(jsonArray3), Good.class);
//            good = goods1.get(goods1.size()-1);
//            System.out.println("for");
//            System.out.println("good!!!!"+good);



            //得到对应商品信息
            QueryWrapper<CentralStation> centralStationQueryWrapper =new QueryWrapper<>();
            centralStationQueryWrapper.eq("id",good.getGoodId());
            System.out.println("good.getGoodName()"+good.getGoodName());
            List<CentralStation> centralStations = centralStationMapper.selectList(centralStationQueryWrapper);
            CentralStation centralStation = centralStations.get(0);
            System.out.println(centralStation);

            //入分站库加入库表
            Inoutstation inoutstation = new Inoutstation();
            inoutstation.setStationClass(2);
            inoutstation.setAlloId(Long.valueOf(0));
            inoutstation.setStationName(String.valueOf(map.get("stationName")));
            inoutstation.setStationId(Long.valueOf(String.valueOf(map.get("stationId"))));
            inoutstation.setTaskId(Long.valueOf(String.valueOf(map.get("taskId"))));
            inoutstation.setGoodId(Long.valueOf(centralStation.getId()));
            inoutstation.setGoodPrice(centralStation.getGoodPrice());
            inoutstation.setGoodName(centralStation.getGoodName());
            inoutstation.setGoodUnit(centralStation.getGoodUnit());
            inoutstation.setDistributor(String.valueOf(map.get("distributor")));
            inoutstation.setGoodFactory(good.getGoodFactory());
            inoutstation.setNumber(good.getGoodNumber());
            //设置时间
            String dateTime = LocalDateTime.parse(String.valueOf(map.get("date")), DateTimeFormatter.ISO_DATE_TIME).atZone(
                    ZoneOffset.UTC).withZoneSameInstant(chinaZoneId).format(formatter);
            Date date = null;
            try {
                date = simpleDateFormat.parse(dateTime);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            inoutstation.setDate(date);
//        inoutstation.setType("购货入库");
            inoutstation.setType("分站已领货出库");
            inoutstation.setRemark(String.valueOf(map.get("remark")));
            inoutstation.setSigner(String.valueOf(map.get("signer")));
            inoutstation.setDistributor(String.valueOf(map.get("distributor")));
            System.out.println(inoutstation);
            res3 = inoutstationMapper.insert(inoutstation);

        }


        //修改任务单和订单状态
        Map map1 =new HashMap<String,Object>();
        map1.put("id",map.get("taskId"));
        map1.put("task_status","已领货");
        map1.put("order_status","已领货");

        HttpResponseEntity res= feignApi.changeTaskOrderType(map1);

        int res1= (int) res.getData();  // 将对象转换成json格式数据
//        inoutstation.setId(null);
//        inoutstationMapper.insert(inoutstation);
        return res1;
    }

    //先查task得到goodlist,
    @Override
    public int returnGoodsToSub(Map<String, Object> map) {
        ZoneId chinaZoneId = ZoneId.of("Asia/Shanghai");
        // 格式化中国时区时间为指定格式的字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //得到要进行分站入库的good列表
        List<Good> goods = new ArrayList<>();
        String jsonString2 = JSON.toJSONString(map.get("goods"));
        JSONArray jsonArray2 = JSON.parseArray(jsonString2);
        goods = JSON.parseArray(String.valueOf(jsonArray2),Good.class);
        System.out.println("goodList"+goods);

        int res = 0;
        for(Good good:goods){
            //得到对应商品信息,能否退货
            QueryWrapper<CentralStation> centralStationQueryWrapper =new QueryWrapper<>();
            centralStationQueryWrapper.eq("id",good.getGoodId());
            List<CentralStation> centralStations = centralStationMapper.selectList(centralStationQueryWrapper);
            CentralStation centralStation = centralStations.get(0);
            System.out.println("centralStation"+centralStation);

//            //传入要退货的good_id得到分站出库表的对应inoutstation
//            Map map1 = new HashMap<>();
//            map1.put("goodId", map.get("goodId"));
//            map1.put("pageNum", 1);
//            map1.put("pageSize", 2);
//            HttpResponseEntity res1 = feignApi.getGood(map1);


            //判断有无货物
            if (centralStation == null) {
                res = 3;

            } else {
//                String jsonString2 = JSON.toJSONString(res1.getData());  // 将对象转换成json格式数据
//                JSONArray jsonArray2 = JSON.parseArray(jsonString2); // 在转回去
//                List<Good> goods = JSON.parseArray(String.valueOf(jsonArray2), Good.class);
//
//                Good good = goods.get(0);
//                System.out.println("?????" + good);

                //判断该商品是否允许退货
                System.out.println(centralStation.getIsReturn());
                if (centralStation.getIsReturn() == 1) {
                    System.out.println("!!!!!");
                    //不得大于中心库房已出库数目
                    if(centralStation.getDoneAllo() >= good.getGoodNumber()){

                        System.out.println(centralStation);
                        System.out.println("centralStation!!!" + centralStation);


                        //入分站库加入库表
                        Inoutstation inoutstation = new Inoutstation();
                        inoutstation.setStationClass(2);
                        inoutstation.setAlloId(Long.valueOf(0));
                        inoutstation.setStationName(String.valueOf(map.get("stationName")));
                        inoutstation.setStationId(Long.valueOf(String.valueOf(map.get("stationId"))));
                        inoutstation.setTaskId(Long.valueOf(String.valueOf(map.get("taskId"))));
                        inoutstation.setGoodId(Long.valueOf(centralStation.getId()));
                        inoutstation.setGoodPrice(centralStation.getGoodPrice());
                        inoutstation.setGoodName(centralStation.getGoodName());
                        inoutstation.setGoodUnit(centralStation.getGoodUnit());
                        inoutstation.setGoodFactory(good.getGoodFactory());
                        inoutstation.setNumber(good.getGoodNumber());
                        //设置时间
                        String dateTime = LocalDateTime.parse(String.valueOf(map.get("date")), DateTimeFormatter.ISO_DATE_TIME).atZone(
                                ZoneOffset.UTC).withZoneSameInstant(chinaZoneId).format(formatter);
                        Date date = null;
                        try {
                            date = simpleDateFormat.parse(dateTime);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        inoutstation.setDate(date);
                        inoutstation.setType("退货出库");
                        inoutstation.setRemark(good.getRemark());
                        inoutstation.setSigner(String.valueOf(map.get("signer")));
                        inoutstation.setDistributor(String.valueOf(map.get("distributor")));
                        System.out.println("inoutstation!"+inoutstation);
                        res = inoutstationMapper.insert(inoutstation);
                    }
                }else {
                    //不允许退货
                    res = 2;
                    break;
                }
            }
        }



        return res;
    }

    @Override
    public int returnGoodsOutSub(Map<String, Object> map) {
        ZoneId chinaZoneId = ZoneId.of("Asia/Shanghai");
        // 格式化中国时区时间为指定格式的字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //得到要进行分站入库的good列表
        List<Good> goods = new ArrayList<>();
        String jsonString2 = JSON.toJSONString(map.get("goods"));
        JSONArray jsonArray2 = JSON.parseArray(jsonString2);
        goods = JSON.parseArray(String.valueOf(jsonArray2),Good.class);
        System.out.println("goodList"+goods);

        QueryWrapper<Allocation> allocationQueryWrapper = new QueryWrapper<>();
        allocationQueryWrapper.eq("id", map.get("alloId"));
        List<Allocation> allocations = allocationMapper.selectList(allocationQueryWrapper);
        Allocation allocation = allocations.get(0);

//        //传入要退货的good_id得到分站出库表的对应inoutstation
//        Map map1 = new HashMap<>();
//        map1.put("goodId",map.get("goodId"));
//        map1.put("pageNum",1);
//        map1.put("pageSize",2);
//        HttpResponseEntity res1= feignApi.getGood(map1);
//        System.out.println(res1);
//        System.out.println("res1.getData()"+res1.getData());

        int res = 0;
        for(Good good:goods){
            //得到对应商品信息,能否退货
            QueryWrapper<CentralStation> centralStationQueryWrapper =new QueryWrapper<>();
            centralStationQueryWrapper.eq("id",good.getGoodId());
            List<CentralStation> centralStations = centralStationMapper.selectList(centralStationQueryWrapper);
            CentralStation centralStation = centralStations.get(0);
            System.out.println("centralStation"+centralStation);

            if(centralStation == null){
                res = 3;

            }else {

                if(centralStation.getIsReturn() == 1){
                    //入分站库加入库表
                    Inoutstation inoutstation = new Inoutstation();
                    inoutstation.setStationClass(2);
                    inoutstation.setAlloId(allocation.getId());
                    inoutstation.setStationName(allocation.getOutStationName());
                    inoutstation.setStationId(allocation.getOutStationId());
                    inoutstation.setTaskId(allocation.getTaskId());
                    inoutstation.setGoodId(Long.valueOf(centralStation.getId()));
                    inoutstation.setGoodPrice(centralStation.getGoodPrice());
                    inoutstation.setGoodName(centralStation.getGoodName());
                    inoutstation.setGoodUnit(centralStation.getGoodUnit());
                    inoutstation.setGoodFactory(good.getGoodFactory());
                    inoutstation.setNumber(good.getGoodNumber());
                    //设置时间
                    String dateTime = LocalDateTime.parse(String.valueOf(map.get("date")), DateTimeFormatter.ISO_DATE_TIME).atZone(
                            ZoneOffset.UTC).withZoneSameInstant(chinaZoneId).format(formatter);
                    Date date = null;
                    try {
                        date = simpleDateFormat.parse(dateTime);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    inoutstation.setDate(date);
                    inoutstation.setType("退货入库");
                    inoutstation.setRemark(good.getRemark());
                    inoutstation.setSigner(String.valueOf(map.get("signer")));
                    inoutstation.setDistributor(String.valueOf(map.get("distributor")));
                    System.out.println("inoutstation!"+inoutstation);
                    res = inoutstationMapper.insert(inoutstation);

                }else {
                    res = 2;
                }
            }
        }
        //修改配送单状态
        Map map2 =new HashMap<String,Object>();
        map2.put("id",map.get("alloId"));
        System.out.println(map.get("alloId"));
        map2.put("allo_type","5");
        HttpResponseEntity res1 = feignApi.updateAllocationbyId(map2);

        int res2= (int) res1.getData();  // 将对象转换成json格式数据

        return res;
    }




    @Override
    public int returnGoodsToCenter(Map<String, Object> map) {
        ZoneId chinaZoneId = ZoneId.of("Asia/Shanghai");
        // 格式化中国时区时间为指定格式的字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //得到要进行分站入库的good列表
        List<Good> goods = new ArrayList<>();
        String jsonString2 = JSON.toJSONString(map.get("goods"));
        JSONArray jsonArray2 = JSON.parseArray(jsonString2);
        goods = JSON.parseArray(String.valueOf(jsonArray2),Good.class);
        System.out.println("goodList"+goods);

        QueryWrapper<Allocation> allocationQueryWrapper = new QueryWrapper<>();
        allocationQueryWrapper.eq("id", map.get("alloId"));
        List<Allocation> allocations = allocationMapper.selectList(allocationQueryWrapper);
        Allocation allocation = allocations.get(0);

//        //传入要退货的good_id得到分站出库表的对应inoutstation
//        Map map1 = new HashMap<>();
//        map1.put("goodId",map.get("goodId"));
//        map1.put("pageNum",1);
//        map1.put("pageSize",2);
//        HttpResponseEntity res1= feignApi.getGood(map1);
//        System.out.println(res1);
//        System.out.println("res1.getData()"+res1.getData());

        int res = 0;
        for(Good good:goods){
            //得到对应商品信息,能否退货
            QueryWrapper<CentralStation> centralStationQueryWrapper =new QueryWrapper<>();
            centralStationQueryWrapper.eq("id",good.getGoodId());
            List<CentralStation> centralStations = centralStationMapper.selectList(centralStationQueryWrapper);
            CentralStation centralStation = centralStations.get(0);
            System.out.println("centralStation"+centralStation);

            //仓库里有没有这种商品
            if(centralStation == null){
                //没有
                res = 3;
            }else {
                if(centralStation.getIsReturn() == 1){
                    //入分站库加入库表
                    Inoutstation inoutstation = new Inoutstation();
                    inoutstation.setStationClass(1);
                    inoutstation.setAlloId(allocation.getId());
                    inoutstation.setStationName(allocation.getInStationName());
                    inoutstation.setStationId(allocation.getInStationId());
                    inoutstation.setTaskId(allocation.getTaskId());
                    inoutstation.setGoodId(Long.valueOf(centralStation.getId()));
                    inoutstation.setGoodPrice(centralStation.getGoodPrice());
                    inoutstation.setGoodName(centralStation.getGoodName());
                    inoutstation.setGoodUnit(centralStation.getGoodUnit());
                    inoutstation.setGoodFactory(good.getGoodFactory());
                    inoutstation.setNumber(good.getGoodNumber());
                    //设置时间
                    String dateTime = LocalDateTime.parse(String.valueOf(map.get("date")), DateTimeFormatter.ISO_DATE_TIME).atZone(
                            ZoneOffset.UTC).withZoneSameInstant(chinaZoneId).format(formatter);
                    Date date = null;
                    try {
                        date = simpleDateFormat.parse(dateTime);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    inoutstation.setDate(date);
                    inoutstation.setType("中心退货入库完成");
                    inoutstation.setRemark(good.getRemark());
                    inoutstation.setSigner(String.valueOf(map.get("signer")));
                    inoutstation.setDistributor(String.valueOf(map.get("distributor")));
                    System.out.println("inoutstation!"+inoutstation);
                    res = inoutstationMapper.insert(inoutstation);

                    //入库改库存
                    centralStation.setWithdrawal(centralStation.getWithdrawal()+good.getGoodNumber());
                    centralStation.setDoneAllo(centralStation.getDoneAllo()-good.getGoodNumber());
                    centralStation.setStock(centralStation.getWaitAllo()+centralStation.getWithdrawal()+centralStation.getDoneAllo());
                    int res1 = centralStationMapper.updateById(centralStation);
                    System.out.println(res1);

                }else {
                    res = 2;
                }
            }
        }

        //修改配送单状态
        Map map2 =new HashMap<String,Object>();
        map2.put("id",map.get("alloId"));
        System.out.println(map.get("alloId"));
        map2.put("allo_type","6");
        HttpResponseEntity res4 = feignApi.updateAllocationbyId(map2);

        int res2= (int) res4.getData();  // 将对象转换成json格式数据

        return res;
    }


    @Override
    public int centralStationReturn(Map<String, Object> map) {
        ZoneId chinaZoneId = ZoneId.of("Asia/Shanghai");
        // 格式化中国时区时间为指定格式的字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//        //得到要进行分站入库的good列表
//        List<Good> goods = new ArrayList<>();
//        String jsonString2 = JSON.toJSONString(map.get("goods"));
//        JSONArray jsonArray2 = JSON.parseArray(jsonString2);
//        goods = JSON.parseArray(String.valueOf(jsonArray2),Good.class);
//        System.out.println("goodList"+goods);

        int res = 0;
        int res1 = 0;

            QueryWrapper<Inoutstation> inoutstationQueryWrapper =new QueryWrapper<>();
            inoutstationQueryWrapper.eq("id",map.get("inoutStationId"));
            List<Inoutstation> inoutstations = inoutstationMapper.selectList(inoutstationQueryWrapper);
            Inoutstation inoutstation = inoutstations.get(0);
            System.out.println("inoutstation!!1"+inoutstation);

            //得到对应商品信息,能否退货
            QueryWrapper<CentralStation> centralStationQueryWrapper =new QueryWrapper<>();
            centralStationQueryWrapper.eq("id",inoutstation.getId());
            List<CentralStation> centralStations = centralStationMapper.selectList(centralStationQueryWrapper);
            CentralStation centralStation = centralStations.get(0);
            System.out.println("centralStation"+centralStation);

            if(inoutstation.getType() != null &&inoutstation.getType().equals("中心退货")){
                centralStation.setWithdrawal(centralStation.getWithdrawal()- inoutstation.getNumber());
                centralStation.setStock(centralStation.getWaitAllo()+centralStation.getWithdrawal()+centralStation.getDoneAllo());
                res = centralStationMapper.updateById(centralStation);
                inoutstation.setType("中心退货(已完成)");
                inoutstationMapper.updateById(inoutstation);


                inoutstation.setId(null);
                //设置时间
                String dateTime = LocalDateTime.parse(String.valueOf(map.get("date")), DateTimeFormatter.ISO_DATE_TIME).atZone(
                        ZoneOffset.UTC).withZoneSameInstant(chinaZoneId).format(formatter);
                Date date = null;
                try {
                    date = simpleDateFormat.parse(dateTime);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                inoutstation.setDate(date);
                inoutstation.setType("中心已退回供应商");
                inoutstation.setRemark(String.valueOf(map.get("remark")));
                inoutstation.setSigner(String.valueOf(map.get("signer")));
                inoutstation.setDistributor(String.valueOf(map.get("distributor")));
                res1 = inoutstationMapper.insert(inoutstation);
            }



        return res;
    }



}
