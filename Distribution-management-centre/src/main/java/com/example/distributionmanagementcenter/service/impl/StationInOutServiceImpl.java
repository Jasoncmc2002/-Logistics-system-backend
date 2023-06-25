package com.example.distributionmanagementcenter.service.impl;

import com.example.distributionmanagementcenter.entity.StationInOut;
import com.example.distributionmanagementcenter.mapper.StationInOutMapper;
import com.example.distributionmanagementcenter.service.StationInOutService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 库房出库 服务实现类
 * </p>
 *
 * @author Jason_Cai
 * @since 2023-06-20
 */
@Service
@Transactional
public class StationInOutServiceImpl extends ServiceImpl<StationInOutMapper, StationInOut> implements StationInOutService {

}
