package com.example.substationmanagementcenter.sevice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.substationmanagementcenter.entity.Postman;
import com.example.substationmanagementcenter.mapper.PostmanMapper;
import com.example.substationmanagementcenter.sevice.PostmanService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 配送员 服务实现类
 * </p>
 *
 * @author hzn
 * @since 2023-06-21
 */
@Service
public class PostmanServiceImpl extends ServiceImpl<PostmanMapper, Postman> implements PostmanService {

    @Autowired
    private PostmanMapper postmanMapper;





}
