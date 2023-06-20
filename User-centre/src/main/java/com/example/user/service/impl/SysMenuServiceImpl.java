package com.example.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.user.common.memu.MenuTypeEnum;
import com.example.user.common.memu.StatusEnum;
import com.example.user.common.memu.SystemConstants;
import com.example.user.entity.RouteBO;
import com.example.user.entity.RouteVO;
import com.example.user.entity.SysMenu;
import com.example.user.mapper.SysMenuMapper;
import com.example.user.service.SysMenuService;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 菜单业务实现类
 *
 * @author haoxr
 * @since 2020/11/06
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements
    SysMenuService {

    /**
     * 路由列表
     */
    @Override
    @Cacheable(cacheNames = "system", key = "'routes'")
    public List<RouteVO> listRoutes() {
        List<RouteBO> menuList = this.baseMapper.listRoutes();
        return recurRoutes(SystemConstants.ROOT_NODE_ID, menuList);
    }

    /**
     * 递归生成菜单路由层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private List<RouteVO> recurRoutes(Long parentId, List<RouteBO> menuList) {
        return CollectionUtil.emptyIfNull(menuList).stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> {
                    RouteVO routeVO = new RouteVO();
                    MenuTypeEnum menuTypeEnum = menu.getType();
                    if (MenuTypeEnum.MENU.equals(menuTypeEnum)) {
                        String routeName = StringUtils.capitalize(StrUtil.toCamelCase(menu.getPath(), '-')); // 路由 name 需要驼峰，首字母大写
                        routeVO.setName(routeName); //  根据name路由跳转 this.$router.push({name:xxx})
                    }
                    routeVO.setPath(menu.getPath()); // 根据path路由跳转 this.$router.push({path:xxx})
                    routeVO.setRedirect(menu.getRedirect());
                    routeVO.setComponent(menu.getComponent());

                    RouteVO.Meta meta = new RouteVO.Meta();
                    meta.setTitle(menu.getName());
                    meta.setIcon(menu.getIcon());
                    meta.setRoles(menu.getRoles());
                    meta.setHidden(StatusEnum.DISABLE.getValue().equals(menu.getVisible()));
                    meta.setKeepAlive(true);
                    routeVO.setMeta(meta);

                    List<RouteVO> children = recurRoutes(menu.getId(), menuList);
                    routeVO.setChildren(children);
                    return routeVO;
                }).toList();
    }

}
