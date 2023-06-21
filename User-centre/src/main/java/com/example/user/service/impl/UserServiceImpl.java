package com.example.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.user.entity.RouteVO;
import com.example.user.entity.User;
import com.example.user.mapper.UserMapper;
import com.example.user.service.UserService;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yang
 * @since 2023-06-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

  @Autowired
  private UserMapper userMapper;

//  @Override
//  public List<User> getAllUser() {
//
//    return userMapper.getAllUser();
//  }
  @Override
  public List<User> selectbyName(User user){
    HashMap<String, Object> map = new HashMap<>();
    map.put("username",user.getUsername());
    map.put("password",user.getPassword());
    return userMapper.selectByMap(map);
  }

  @Override
  public List<RouteVO> listRoutes() {
    return userMapper.listRoutes();
  }
}
