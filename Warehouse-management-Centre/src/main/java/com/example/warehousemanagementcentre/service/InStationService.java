package com.example.warehousemanagementcentre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.warehousemanagementcentre.entity.InStation;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>
 * 库房出库 服务类
 * </p>
 *
 * @author hzn
 * @since 2023-06-21
 */
public interface InStationService extends IService<InStation> {

    PageInfo getInStation(Map<String, Object> map);

    int updatebyId(InStation inStation);
}
