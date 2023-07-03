package com.example.warehousemanagementcentre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.warehousemanagementcentre.entity.CentralStation;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>
 * 中心库房存量  服务类
 * </p>
 *
 * @author hzn
 * @since 2023-06-25
 */
public interface CentralstationService extends IService<CentralStation> {
    int updatebyId(CentralStation centralStation);

    PageInfo selectBuy(Map<String,Object> map);

    int toInStation(Map<String,Object> map);

    int toOutStation(Map<String,Object> map);

    int toInSubstation(Map<String,Object> map);

    int takeGoods(Map<String, Object> map);

    int returnGoodsToSub(Map<String, Object> map);

    int returnGoodsToCenter(Map<String,Object> map);



    PageInfo searchInCentral(Map<String,Object> map);
}
