package com.example.warehousemanagementcentre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.warehousemanagementcentre.entity.Station;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 库房出库 Mapper 接口
 * </p>
 *
 * @author hzn
 * @since 2023-06-21
 */
@Mapper
public interface StationMapper extends BaseMapper<Station> {

}
