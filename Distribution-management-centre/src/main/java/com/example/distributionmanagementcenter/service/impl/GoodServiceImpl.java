package com.example.distributionmanagementcenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.distributionmanagementcenter.entity.Buy;
import com.example.distributionmanagementcenter.entity.Good;
import com.example.distributionmanagementcenter.mapper.BuyMapper;
import com.example.distributionmanagementcenter.mapper.GoodMapper;
import com.example.distributionmanagementcenter.service.GoodService;
;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author jason_cai
 * @since 2023-06-19
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements GoodService {
    @Autowired
    private GoodMapper goodMapper;
    @Autowired
    private BuyMapper buyMapper;
    @Override
    public PageInfo getListByOrderId(Map<String, Object> map) throws ParseException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
                Integer.valueOf(String.valueOf(map.get("pageSize"))));
        Integer keyId=Integer.valueOf(String.valueOf(map.get("keyId")));
        QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("key_id",keyId);
        List<Good> records= goodMapper.selectList(queryWrapper);
        PageInfo pageInfo = new PageInfo(records);
        return pageInfo;
    }
//功能同上
    @Override
    public List<Good> getGoodByOrderId(Integer id) {
        QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("key_id",id);
        List<Good> records= goodMapper.selectList(queryWrapper);
        return records;
    }

    @Override
    public PageInfo getListByGoodId(Map<String, Object> map) throws ParseException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
                Integer.valueOf(String.valueOf(map.get("pageSize"))));
        Integer goodId=Integer.valueOf(String.valueOf(map.get("goodId")));
        System.out.println(map);
        QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("good_id",goodId);
        List<Good> records= goodMapper.selectList(queryWrapper);
        PageInfo pageInfo = new PageInfo(records);
        return pageInfo;
    }
    @Override
    public  List<Good>  getListByGoodId1(Map<String, Object> map) throws ParseException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
                Integer.valueOf(String.valueOf(map.get("pageSize"))));
        Integer goodId=Integer.valueOf(String.valueOf(map.get("goodId")));
        System.out.println(map);
        QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("good_id",goodId);
        List<Good> records= goodMapper.selectList(queryWrapper);
        return records;
    }
}
