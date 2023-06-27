package com.example.warehousemanagementcentre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.warehousemanagementcentre.entity.InStation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 库房出库 Mapper 接口
 * </p>
 *
 * @author hzn
 * @since 2023-06-21
 */
@Mapper
public interface InStationMapper extends BaseMapper<InStation> {

    List<InStation> selectInStationByGoodName(String name);
}
