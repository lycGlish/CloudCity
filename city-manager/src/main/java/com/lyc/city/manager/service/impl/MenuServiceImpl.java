package com.lyc.city.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.city.manager.dao.MenuDao;
import com.lyc.city.to.AllInfoTo;
import com.lyc.city.utils.PageUtils;
import com.lyc.city.utils.Query;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.lyc.city.manager.entity.MenuEntity;
import com.lyc.city.manager.service.MenuService;
import org.springframework.util.StringUtils;


@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuDao, MenuEntity> implements MenuService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MenuEntity> page = this.page(
                new Query<MenuEntity>().getPage(params),
                new QueryWrapper<MenuEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 获取一级菜单
     *
     * @return 菜单列表
     */
    @Override
    public List<MenuEntity> getFirstMenu() {
        RLock menuLock = redissonClient.getLock("menu-lock");
        menuLock.lock();
        try {
            String firstMenus = stringRedisTemplate.opsForValue().get("firstMenus");
            if (StringUtils.isEmpty(firstMenus)) {
                List<MenuEntity> firstMenuEntities = baseMapper.selectList(
                        new QueryWrapper<MenuEntity>().eq("parent_id", 0));
                stringRedisTemplate.opsForValue().set("firstMenus", JSON.toJSONString(firstMenuEntities), 1, TimeUnit.DAYS);
                return firstMenuEntities;
            }
            return JSON.parseArray(firstMenus, MenuEntity.class);
        } finally {
            menuLock.unlock();
        }
    }

    /**
     * 获取二级菜单
     *
     * @return 菜单列表
     */
    @Override
    public List<MenuEntity> getSecondMenu() {
        RLock menuLock = redissonClient.getLock("menu-lock");
        menuLock.lock();
        try {
            String secondMenus = stringRedisTemplate.opsForValue().get("secondMenus");
            if (StringUtils.isEmpty(secondMenus)) {
                List<MenuEntity> secondMenuEntities = baseMapper.selectList(
                        new QueryWrapper<MenuEntity>().ne("parent_id", 0));
                stringRedisTemplate.opsForValue().set("secondMenus", JSON.toJSONString(secondMenuEntities), 1, TimeUnit.DAYS);
                return secondMenuEntities;
            }
            return JSON.parseArray(secondMenus, MenuEntity.class);
        } finally {
            menuLock.unlock();
        }
    }

    /**
     * 获取所有菜单
     *
     * @return 菜单列表
     */
    @Override
    public List<MenuEntity> getAllMenu() {
        RLock menuLock = redissonClient.getLock("menu-lock");
        menuLock.lock();
        try {
            String menus = stringRedisTemplate.opsForValue().get("menus");
            if (StringUtils.isEmpty(menus)) {
                List<MenuEntity> menuEntities = baseMapper.selectList(new QueryWrapper<>());
                stringRedisTemplate.opsForValue().set("menus", JSON.toJSONString(menuEntities), 1, TimeUnit.DAYS);
                return menuEntities;
            }
            return JSON.parseArray(menus, MenuEntity.class);
        } finally {
            menuLock.unlock();
        }
    }
}