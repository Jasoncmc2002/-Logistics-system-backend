package com.example.distributionmanagementcenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.distributionmanagementcenter.entity.Station;

import java.text.ParseException;
import java.util.Map;

/**
 * <p>
 * 库房 服务类
 * </p>
 *
 * @author Jason_cai
 * @since 2023-06-19
 */
public interface StationService extends IService<Station> {
    Map<String,Object> stationInOutQueryService(Map<String, Object> map) throws ParseException;

}
