package com.example.distributionmanagementcenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.distributionmanagementcenter.entity.SecondaryCategory;
import com.example.distributionmanagementcenter.mapper.SecondaryCategoryMapper;

import com.example.distributionmanagementcenter.service.SecondaryCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SecondaryCategoryServiceImpl extends ServiceImpl<SecondaryCategoryMapper, SecondaryCategory> implements SecondaryCategoryService {

}
