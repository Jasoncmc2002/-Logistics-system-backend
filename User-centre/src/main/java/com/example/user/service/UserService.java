package com.example.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.user.entity.User;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yang
 * @since 2023-06-13
 */
@Service
public interface UserService extends IService<User> {
  List<User> getAllUser();
  List<User> selectbyName(User user);


}
