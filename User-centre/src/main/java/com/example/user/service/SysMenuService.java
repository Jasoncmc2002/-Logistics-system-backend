package com.example.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.user.entity.RouteVO;
import com.example.user.entity.SysMenu;
import java.util.List;
import java.util.Set;

/**
 * 菜单业务接口
 *
 * @author haoxr
 * @since 2020/11/06
 */
public interface SysMenuService extends IService<SysMenu> {


    /**
     * 获取路由列表
     *
     * @return
     */
    List<RouteVO> listRoutes();


}
