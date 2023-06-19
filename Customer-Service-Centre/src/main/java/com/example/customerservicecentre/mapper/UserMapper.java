package com.example.customerservicecentre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.customerservicecentre.entity.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author yangfuchao
 * @since 2023-06-19
 */@Mapper
public interface UserMapper extends BaseMapper<User> {

}
