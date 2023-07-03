package com.example.distributionmanagementcenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.distributionmanagementcenter.entity.FirstCategory;
import com.example.distributionmanagementcenter.entity.SecondaryCategory;
import com.example.distributionmanagementcenter.mapper.FirstCategoryMapper;
import com.example.distributionmanagementcenter.mapper.SecondaryCategoryMapper;

import com.example.distributionmanagementcenter.service.SecondaryCategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional(rollbackFor=Exception.class)
public class SecondaryCategoryServiceImpl extends ServiceImpl<SecondaryCategoryMapper, SecondaryCategory> implements SecondaryCategoryService {
    @Autowired
    private SecondaryCategoryMapper secondaryCategoryMapper;
    @Override
    public PageInfo getList(Map<String, Object> map) throws ParseException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNum"))),
                Integer.valueOf(String.valueOf(map.get("pageSize"))));

        QueryWrapper<SecondaryCategory> queryWrapper = new QueryWrapper<>();
        if((String) map.get("keywords")!=null&& !Objects.equals((String) map.get("keywords"), "")){
            String pattern = (String) map.get("keywords");
            queryWrapper.like("sname",pattern);
        }
        List<SecondaryCategory> records= secondaryCategoryMapper.selectList(queryWrapper);
        PageInfo pageInfo = new PageInfo(records);
//        pageInfo.setPageSize(Integer.valueOf(String.valueOf(map.get("pageSize"))));
        System.out.println(pageInfo.getPageSize());
        return pageInfo;
    }
}
