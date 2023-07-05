package com.example.distributionmanagementcenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.distributionmanagementcenter.entity.Buy;
import com.example.distributionmanagementcenter.entity.SecondaryCategory;
import com.example.distributionmanagementcenter.entity.Station;
import com.example.distributionmanagementcenter.entity.StationInOut;
import com.example.distributionmanagementcenter.mapper.BuyMapper;
import com.example.distributionmanagementcenter.mapper.CentralStationMapper;
import com.example.distributionmanagementcenter.mapper.StationInOutMapper;
import com.example.distributionmanagementcenter.mapper.StationMapper;
import com.example.distributionmanagementcenter.service.StationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 * 库房 服务实现类
 * </p>
 *
 * @author Jason_cai
 * @since 2023-06-19
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class StationServiceImpl extends ServiceImpl<StationMapper, Station> implements StationService {
@Autowired
private StationInOutMapper stationInOutMapper;
@Autowired
private BuyMapper buyMapper;
@Autowired
private CentralStationMapper centralStationMapper;
@Autowired
private StationMapper stationMapper;
    @Override
    public PageInfo stationInOutQueryService(Map<String, Object> map) throws ParseException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
                Integer.valueOf(String.valueOf(map.get("pageSize"))));
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date startTime =sdf.parse((String) map.get("startTime"));
//        Date endTime = sdf.parse((String) map.get("endTime"));
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        ZonedDateTime startTime = ZonedDateTime.parse((String) map.get("startTime"), inputFormatter);
        ZonedDateTime endTime = ZonedDateTime.parse((String) map.get("endTime"), inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startDate = outputFormatter.format(startTime);
        String endDate = outputFormatter.format(endTime);
        QueryWrapper<StationInOut> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("date", startDate, endDate);
        List<StationInOut> records= stationInOutMapper.selectList(queryWrapper);

        PageInfo pageInfo = new PageInfo(records);
        return pageInfo;
    }

    @Override
    public PageInfo withdrawalQueryBuyService(Map<String, Object> map) throws ParseException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
                Integer.valueOf(String.valueOf(map.get("pageSize"))));
        HashMap<String, Object> res=new HashMap<String, Object>();
        String supplyName=(String)map.get("supplyName");

        Integer goodId=Integer.valueOf(String.valueOf(map.get("goodId")));
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date startTime =sdf.parse((String) map.get("startTime"));
//        Date endTime = sdf.parse((String) map.get("endTime"));
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        ZonedDateTime startTime = ZonedDateTime.parse((String) map.get("startTime"), inputFormatter);
        ZonedDateTime endTime = ZonedDateTime.parse((String) map.get("endTime"), inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startDate = outputFormatter.format(startTime);
        String endDate = outputFormatter.format(endTime);
        QueryWrapper<Buy> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("date", startDate, endDate)
                .eq("good_id",goodId)
                .eq("supply",supplyName);
        //得到buy列表
        List<Buy> records= buyMapper.selectList(queryWrapper);
        //res.put("buyList",records);
        //得到对应的订单库存
//        for(Buy buy:records){
//           CentralStation centralStation= centralStationMapper.selectById(buy.getGoodId());
//           res.put("withdrawal"+centralStation.getGoodName(),centralStation.getWithdrawal());
//           res.put("WaitAllocation"+centralStation.getGoodName(),centralStation.getWaitAllo());
//        }
        PageInfo pageInfo = new PageInfo(records);
        return pageInfo;
    }

    @Override
    public PageInfo getList(Map<String, Object> map) throws ParseException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
                Integer.valueOf(String.valueOf(map.get("pageSize"))));

        QueryWrapper<Station> queryWrapper = new QueryWrapper<>();
        if((String) map.get("nameKeyword")!=null&& !Objects.equals((String) map.get("nameKeyword"), "")){
            String pattern = (String) map.get("nameKeyword");
            queryWrapper.like("name",pattern);
        }
        if((String) map.get("addrKeyword")!=null&& !Objects.equals((String) map.get("addrKeyword"), "")){
            String pattern1 = (String) map.get("addrKeyword");
            queryWrapper.like("address",pattern1);
        }
        if(map.get("stationClass")!=null){
            queryWrapper.eq("station_class",map.get("stationClass"));
        }
        List<Station> records= stationMapper.selectList(queryWrapper);
        for(Station station :records){
            String className="";
            if(station.getStationClass()==1){
                className="中心库房";
            }
            else {
                if(station.getStationClass()==2){
                    className="分站库房";
                }
                else{
                    className="未设置";
                }
            }
            station.setStationClassName(className);
        }
        PageInfo pageInfo = new PageInfo(records);
//        pageInfo.setPageSize(Integer.valueOf(String.valueOf(map.get("pageSize"))));
        return pageInfo;
    }
}
