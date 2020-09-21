package com.lyc.city.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.city.manager.entity.MenuEntity;
import com.lyc.city.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author lyc
 * @email 708901735@qq.com
 * @date 2020-09-04 15:41:20
 */
public interface MenuService extends IService<MenuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<MenuEntity> getFirstMenu();

    List<MenuEntity> getSecondMenu();

    List<MenuEntity> getAllMenu();
}

