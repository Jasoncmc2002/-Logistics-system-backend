package com.example.distributionmanagementcenter.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.distributionmanagementcenter.entity.Supply;
import com.example.distributionmanagementcenter.mapper.SupplyMapper;
import com.example.distributionmanagementcenter.service.SupplyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 供应商 服务实现类
 * </p>
 *
 * @author jason_cai
 * @since 2023-06-19
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class SupplyServiceImpl extends ServiceImpl<SupplyMapper, Supply> implements SupplyService {

}
