package com.example.distributionmanagementcenter.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.distributionmanagementcenter.entity.CentralStation;
import com.example.distributionmanagementcenter.entity.FirstCategory;
import com.example.distributionmanagementcenter.mapper.*;
import com.example.distributionmanagementcenter.service.CentralstationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 中心库房存量  服务实现类
 * </p>
 *
 * @author Jason_cai
 * @since 2023-06-19
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class CentralStationServiceImpl extends ServiceImpl<CentralStationMapper, CentralStation> implements CentralstationService {
   @Autowired
   private CentralStationMapper centralStationMapper;
   @Autowired
   private SupplyMapper supplyMapper;
   @Autowired
   private FirstCategoryMapper firstCategoryMapper;
   @Autowired
   private SecondaryCategoryMapper secondaryCategoryMapper;
   @Autowired
   private StationMapper stationMapper;

    @Override
    public PageInfo getListByCondition(Map<String, Object> map) throws ParseException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
                Integer.valueOf(String.valueOf(map.get("pageSize"))));
//        PageHelper.startPage((Integer)map.get("pageNum"),(Integer)map.get("pageSize"));
//        Integer goodClassId= Integer.valueOf(String.valueOf(map.get("goodClassId"));
//        Integer goodSubclassId= Integer.valueOf(String.valueOf(map.get("goodSubclassId")));
        QueryWrapper<CentralStation> queryWrapper = new QueryWrapper<>();
        if(map.get("goodClassId")!=null){
            queryWrapper.eq("good_class_id",map.get("goodClassId"));
        }
        if(map.get("goodSubclassId")!=null){
            queryWrapper.eq("good_subclass_id",map.get("goodSubclassId"));
        }
        if((String) map.get("keywords")!=null&& !Objects.equals((String) map.get("keywords"), "")){
            String pattern = (String) map.get("keywords");
            queryWrapper.like("good_name",pattern);
        }
       if(map.get("stationId")!=null){
           queryWrapper.eq("station_id",map.get("stationId"));
       }
        if(map.get("supplyId")!=null){
            queryWrapper.eq("supply_id",map.get("supplyId"));
        }
        List<CentralStation> records= centralStationMapper.selectList(queryWrapper);
        for(CentralStation centralStation :records){
            String goodClassName="";
            if(firstCategoryMapper.selectById(centralStation.getGoodClassId())==null){
                goodClassName="EMPTY";
            }
            else{
                goodClassName=firstCategoryMapper.selectById(centralStation.getGoodClassId()).getFName();
            }
            centralStation.setGoodClassName(goodClassName);
            String goodSubClassName ="";
            if(secondaryCategoryMapper.selectById(centralStation.getGoodSubclassId())==null){
                goodSubClassName="EMPTY";
            }
            else{
                goodSubClassName= secondaryCategoryMapper.selectById(centralStation.getGoodSubclassId()).getSName();
            }
            centralStation.setGoodSubClassName(goodSubClassName);
            String stationName ="";
            if(stationMapper.selectById(centralStation.getStationId())==null){
                stationName="EMPTY";
            }
            else{
                stationName= stationMapper.selectById(centralStation.getStationId()).getName();
            }
            centralStation.setStationName(stationName);
            String supplyName ="";
            if(supplyMapper.selectById(centralStation.getSupplyId())==null){
                supplyName="EMPTY";
            }
            else{
                supplyName= supplyMapper.selectById(centralStation.getSupplyId()).getName();
            }
            centralStation.setSupplyName(supplyName);
            if(centralStation.getIsReturn()==1){
                centralStation.setIsReturnName("是");
            }
            if(centralStation.getIsReturn()==0){
                centralStation.setIsReturnName("是");
            }
            if(centralStation.getIsChange()==1){
             centralStation.setIsChangeName("是");
            }
            if(centralStation.getIsChange()==0){
                centralStation.setIsChangeName("否");
            }
        }
        PageInfo pageInfo = new PageInfo(records);
        return pageInfo;
    }

    @Override
    public PageInfo getList(Map<String, Object> map) throws ParseException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
                Integer.valueOf(String.valueOf(map.get("pageSize"))));

        List<CentralStation> records= centralStationMapper.selectList(null);
        PageInfo pageInfo = new PageInfo(records);
        return pageInfo;
    }

    @Override
    public int updateList(Map<String, Object> map) throws ParseException {
        List<Integer> list = (List<Integer>) map.get("idList");
        for(Integer integer :list){
            CentralStation centralStation = new CentralStation();
            centralStation.setId(integer);
            centralStation.setMax(Long.valueOf((String)map.get("max")));
            centralStation.setWarn(Long.valueOf((String)map.get("warn")));
            centralStationMapper.updateById(centralStation);
        }
        return 0;
    }
}
