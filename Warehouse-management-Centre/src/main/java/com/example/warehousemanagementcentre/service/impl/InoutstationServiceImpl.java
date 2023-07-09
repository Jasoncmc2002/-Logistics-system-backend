package com.example.warehousemanagementcentre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.warehousemanagementcentre.entity.CentralStation;
import com.example.warehousemanagementcentre.entity.Inoutstation;
import com.example.warehousemanagementcentre.mapper.InoutstationMapper;
import com.example.warehousemanagementcentre.service.InoutstationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 库房出入库 服务实现类
 * </p>
 *
 * @author hzn
 * @since 2023-06-26
 */
@Service
public class InoutstationServiceImpl extends ServiceImpl<InoutstationMapper, Inoutstation> implements InoutstationService {

    @Autowired
    private InoutstationMapper inoutstationMapper;
    @Override
    public PageInfo selectByType(Map<String, Object> map) {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
                Integer.valueOf(String.valueOf(map.get("pageSize"))));
        //中心库存中对应的数据
        QueryWrapper<Inoutstation> inoutstationQueryWrapper = new QueryWrapper<>();
        if(!map.get("type").equals("")){
            inoutstationQueryWrapper.eq("type", map.get("type"));
        }


        List<Inoutstation> inoutstations = inoutstationMapper.selectList(inoutstationQueryWrapper);
        PageInfo pageInfo = new PageInfo(inoutstations);
        return pageInfo;
    }
}
