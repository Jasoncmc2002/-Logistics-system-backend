package com.example.distributionmanagementcenter.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.distributionmanagementcenter.entity.CentralStation;
import com.example.distributionmanagementcenter.entity.FirstCategory;
import com.example.distributionmanagementcenter.mapper.CentralStationMapper;
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

    @Override
    public PageInfo getListByCondition(Map<String, Object> map) throws ParseException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
                Integer.valueOf(String.valueOf(map.get("pageSize"))));
        Integer goodClassId= (Integer) map.get("goodClassId");
        Integer goodSubclassId= (Integer) map.get("goodSubclassId");
        QueryWrapper<CentralStation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("good_class_id",goodClassId);
        queryWrapper.eq("good_subclass_id",goodSubclassId);
        if((String) map.get("keywords")!=null&& !Objects.equals((String) map.get("keywords"), "")){
            String pattern = (String) map.get("keywords");
            queryWrapper.like("good_name",pattern);
        }
        Integer supplyId= (Integer) map.get("supplyId");
        queryWrapper.eq("supply_id",supplyId);
        List<CentralStation> records= centralStationMapper.selectList(queryWrapper);
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
}
