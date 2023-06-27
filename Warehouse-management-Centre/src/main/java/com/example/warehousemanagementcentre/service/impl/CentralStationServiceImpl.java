package com.example.warehousemanagementcentre.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.warehousemanagementcentre.beans.HttpResponseEntity;
import com.example.warehousemanagementcentre.entity.*;
import com.example.warehousemanagementcentre.feign.DistributionFeign;
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
    private DistributionFeign distributionFeign;

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




        //通过buy获取good
        HttpResponseEntity res1= feignApi.getGood(buy.getGoodId());
        String jsonString2 = JSON.toJSONString(res1.getData());  // 将对象转换成json格式数据
        JSONObject jsonObject1 = JSON.parseObject(jsonString2); // 在转回去
        Good good = JSON.parseObject(String.valueOf(jsonObject1), Good.class);
//        System.out.println("3333"+good);


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
        inoutstation.setType(Long.valueOf("1"));//设入库类型为1
        inoutstation.setRemark("0");//设备注为0
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

        int res1 = inoutstationMapper.insert(inoutstation);

        return res1;
    }

    @Override
    public int toInSubstation(Map<String, Object> map) {
        String jsonString1 = JSON.toJSONString(map.get("InOutStation"));  // 将对象转换成json格式数据
        JSONObject jsonObject = JSON.parseObject(jsonString1); // 在转回去
        Inoutstation inoutstation = JSON.parseObject(String.valueOf(jsonObject), Inoutstation.class);


        Map map1 =new HashMap<String,Object>();
        map1.put("id",inoutstation.getTaskId());
        map1.put("task_status","可分配");
        map1.put("order_status","配送站到货");

        HttpResponseEntity res= feignApi.changeTaskOrderType(map1);
        int res1= (int) res.getData();  // 将对象转换成json格式数据
        return res1;
    }


}
