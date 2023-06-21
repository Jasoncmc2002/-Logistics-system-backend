package com.example.distributionmanagementcenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.distributionmanagementcenter.entity.Station;
import com.example.distributionmanagementcenter.entity.StationInOut;
import com.example.distributionmanagementcenter.mapper.StationInOutMapper;
import com.example.distributionmanagementcenter.mapper.StationMapper;
import com.example.distributionmanagementcenter.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 库房 服务实现类
 * </p>
 *
 * @author Jason_cai
 * @since 2023-06-19
 */
@Service
public class StationServiceImpl extends ServiceImpl<StationMapper, Station> implements StationService {
@Autowired
private StationInOutMapper stationInOutMapper;

    @Override
    public Map<String, Object> stationInOutQueryService(Map<String, Object> map) throws ParseException {
        HashMap<String, Object> res=new HashMap<String, Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime =sdf.parse((String) map.get("startTime"));
        Date endTime = sdf.parse((String) map.get("endTime"));
        QueryWrapper<StationInOut> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("date", startTime, endTime);
        List<StationInOut> records= stationInOutMapper.selectList(queryWrapper);
        res.put("records",records);
        return res;
    }

    @Override
    public Map<String, Object> withdrawalService(Map<String, Object> map) throws ParseException {
        HashMap<String, Object> res=new HashMap<String, Object>();
        String supplyName=(String)map.get("supplyName");
        int goodId=(int)map.get("goodId");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime =sdf.parse((String) map.get("startTime"));
        Date endTime = sdf.parse((String) map.get("endTime"));
        QueryWrapper<StationInOut> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("date", startTime, endTime)
                .eq("good_id",goodId)
                .eq("supply",supplyName);
        //List<StationInOut> records= stationInOutMapper.selectList(queryWrapper);
        return res;
    }
}
