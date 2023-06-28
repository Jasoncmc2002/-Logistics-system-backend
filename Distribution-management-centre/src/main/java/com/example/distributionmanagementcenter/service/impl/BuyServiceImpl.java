package com.example.distributionmanagementcenter.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.distributionmanagementcenter.entity.Buy;
import com.example.distributionmanagementcenter.mapper.BuyMapper;
import com.example.distributionmanagementcenter.service.BuyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 进货单 服务实现类
 * </p>
 *
 * @author Jason_Cai
 * @since 2023-06-20
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class BuyServiceImpl extends ServiceImpl<BuyMapper, Buy> implements BuyService {
      @Autowired
      private  BuyMapper buyMapper;
    @Override
    public PageInfo getListByDateSupply(Map<String, Object> map) throws ParseException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
                Integer.valueOf(String.valueOf(map.get("pageSize"))));
        String supplyName=(String)map.get("supplyName");
        Integer goodId=Integer.valueOf(String.valueOf(map.get("goodId")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime =sdf.parse((String) map.get("startTime"));
        Date endTime = sdf.parse((String) map.get("endTime"));
        QueryWrapper<Buy> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("date", startTime, endTime)
                .eq("supply",supplyName);
        List<Buy> records= buyMapper.selectList(queryWrapper);
        PageInfo pageInfo = new PageInfo(records);
        return pageInfo;
    }

    @Override
    public int deleteBuyByIds(Map<String, Object> map) throws ParseException {
//        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
//                Integer.valueOf(String.valueOf(map.get("pageSize"))));
        Integer goodId=Integer.valueOf(String.valueOf(map.get("good_id")));
        Integer orderId=Integer.valueOf(String.valueOf(map.get("order_id")));
        Integer buyType=Integer.valueOf(String.valueOf(map.get("buy_type")));
        QueryWrapper<Buy> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("good_id",goodId).eq("order_id",orderId).eq("buy_type",buyType);
        return buyMapper.delete(queryWrapper);
    }

    @Override
    public int updateBuyByIds(Map<String, Object> map) throws ParseException {
        int flag=1;
        Integer goodId=Integer.valueOf(String.valueOf(map.get("good_id")));
        Integer orderId=Integer.valueOf(String.valueOf(map.get("order_id")));
        Integer number=Integer.valueOf(String.valueOf(map.get("number")));
        QueryWrapper<Buy> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("good_id",goodId)
                .eq("order_id",orderId);
        List<Buy> records = buyMapper.selectList(queryWrapper);
        for (Buy buy:records){
            buy.setNumber(number);
           flag=flag* buyMapper.updateById(buy);
        }
        return flag;
    }
}
