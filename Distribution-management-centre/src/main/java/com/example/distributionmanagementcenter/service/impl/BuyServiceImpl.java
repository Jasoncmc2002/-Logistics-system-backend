package com.example.distributionmanagementcenter.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.distributionmanagementcenter.entity.Buy;
import com.example.distributionmanagementcenter.entity.CentralStation;
import com.example.distributionmanagementcenter.entity.Good;
import com.example.distributionmanagementcenter.mapper.BuyMapper;
import com.example.distributionmanagementcenter.mapper.GoodMapper;
import com.example.distributionmanagementcenter.service.BuyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
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
@Transactional
public class BuyServiceImpl extends ServiceImpl<BuyMapper, Buy> implements BuyService {

}
