package com.example.distributionmanagementcenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.distributionmanagementcenter.entity.Buy;
import com.example.distributionmanagementcenter.entity.Good;
import com.example.distributionmanagementcenter.entity.StationInOut;
import com.example.distributionmanagementcenter.mapper.GoodMapper;
import com.example.distributionmanagementcenter.mapper.StationInOutMapper;
import com.example.distributionmanagementcenter.mapper.StationMapper;
import com.example.distributionmanagementcenter.service.StationInOutService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 库房出库 服务实现类
 * </p>
 *
 * @author Jason_Cai
 * @since 2023-06-20
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class StationInOutServiceImpl extends ServiceImpl<StationInOutMapper, StationInOut> implements StationInOutService {
    @Autowired
    private  StationInOutMapper stationInOutMapper;
    @Autowired
    private GoodMapper goodMapper;
    @Override
    public PageInfo getListByConditions(Map<String, Object> map) throws ParseException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
                Integer.valueOf(String.valueOf(map.get("pageSize"))));
        Integer stationType=Integer.valueOf(String.valueOf(map.get("stationType")));
        String goodName=(String)map.get("goodName");
        String outType=(String)map.get("outType");
        QueryWrapper<Good> queryWrapper1 = new QueryWrapper<>();
         queryWrapper1.eq("good_name",goodName);
        List<Good> records1= goodMapper.selectList(queryWrapper1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime =sdf.parse((String) map.get("startTime"));
        Date endTime = sdf.parse((String) map.get("endTime"));

        QueryWrapper<StationInOut> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("date", startTime, endTime)
                .eq("type",outType)
                .eq("station_class",stationType)
                .eq("type",outType);
        for(Good good :records1){
            queryWrapper.eq("good_id",good.getGoodId());
        }
        List<StationInOut> records= stationInOutMapper.selectList(queryWrapper);
        PageInfo pageInfo = new PageInfo(records);
        return pageInfo;
    }

    @Override
    public List<StationInOut> getListByConditions1(Map<String, Object> map) throws ParseException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
                Integer.valueOf(String.valueOf(map.get("pageSize"))));
        Integer stationType=Integer.valueOf(String.valueOf(map.get("stationType")));
        Integer goodId=(Integer)map.get("good_id");
        String outType=(String)map.get("outType");

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
        queryWrapper.between("date", startTime, endTime)
                .eq("type",outType)
                .eq("station_class",stationType)
                .eq("good_id",goodId);
        List<StationInOut> records= stationInOutMapper.selectList(queryWrapper);
        return records;
    }
}
