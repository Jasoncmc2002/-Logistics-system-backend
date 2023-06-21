package com.example.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.user.beans.HttpResponseEntity;
import com.example.user.beans.result.Result;
import com.example.user.common.Constans;
import com.example.user.entity.RouteVO;
import com.example.user.entity.User;
import com.example.user.entity.me;
import com.example.user.service.SysMenuService;
import com.example.user.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yang
 * @since 2023-06-13
 */
@RestController
@RequestMapping("/user")
public class UserAction {

    private final Logger logger = LoggerFactory.getLogger(UserAction.class);

    @Autowired
    private  SysMenuService menuService;
    @Autowired
    private UserService userService;

    @RequestMapping(value="/userLoginByName",method= RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity userLoginByName(@RequestBody User userEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            List<User> hasUser = userService.selectbyName(userEntity);
            if(CollectionUtils.isEmpty(hasUser) ) {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setData(null);
                httpResponseEntity.setMessage(Constans.LOGIN_USERNAME_MESSAGE);
            }else {
                if(userEntity.getPassword().equals(hasUser.get(0).getPassword())){
                    httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                    httpResponseEntity.setData(hasUser.get(0));
                    httpResponseEntity.setMessage(Constans.LOGIN_MESSAGE);
                }
                else {
                    httpResponseEntity.setCode(Constans.EXIST_CODE);
                    httpResponseEntity.setData(null);
                    httpResponseEntity.setMessage(Constans.LOGIN_PASSWORD_MESSAGE);
                }
            }

        } catch (Exception e) {
            logger.info("userLoginByName 通过账号登录>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    @GetMapping("/getroutes")
    public Result<List<RouteVO>> listRoutes() {
        List<RouteVO> routeList = menuService.listRoutes();
        return Result.success(routeList);
    }

}
