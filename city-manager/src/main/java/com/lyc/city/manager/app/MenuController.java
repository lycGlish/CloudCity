package com.lyc.city.manager.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.lyc.city.utils.PageUtils;
import com.lyc.city.utils.R;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.lyc.city.manager.entity.MenuEntity;
import com.lyc.city.manager.service.MenuService;

/**
 * @author lyc
 * @email 708901735@qq.com
 * @date 2020-09-04 15:41:20
 */
@RestController
@RequestMapping("manager/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @GetMapping("/deleteMenuById/{menuId}")
    public R deleteMenuById(@PathVariable("menuId") Long menuId) {
        RLock menuLock = redissonClient.getLock("menu-lock");
        menuLock.lock();
        try {
            menuService.removeById(menuId);
            deleteRedisMenu();
        } finally {
            menuLock.unlock();
        }
        return R.ok();
    }

    @PostMapping("/addOrUpdateMenu")
    @Transactional
    public R addOrUpdateMenu(@RequestBody MenuEntity menuEntity) {
        RLock menuLock = redissonClient.getLock("menu-lock");
        menuLock.lock();
        try {
            menuService.saveOrUpdate(menuEntity);
            deleteRedisMenu();
        } finally {
            menuLock.unlock();
        }
        return R.ok();
    }

    @GetMapping("/secondMenu")
    public R secondMenu() {
        List<MenuEntity> secondMenus = menuService.getSecondMenu();
        return R.ok().put("data", secondMenus);
    }

    @GetMapping("/firstMenu")
    public R firstMenu() {
        List<MenuEntity> firstMenus = menuService.getFirstMenu();
        return R.ok().put("data", firstMenus);
    }

    @GetMapping("/allMenu")
    public R allMenu() {
        List<MenuEntity> allMenus = menuService.getAllMenu();
        return R.ok().put("data", allMenus);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("manager:menu:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = menuService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{menuId}")
    //@RequiresPermissions("manager:menu:info")
    public R info(@PathVariable("menuId") Long menuId) {
        MenuEntity menu = menuService.getById(menuId);
        return R.ok().put("menu", menu);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("manager:menu:save")
    public R save(@RequestBody MenuEntity menu) {
        menuService.save(menu);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("manager:menu:update")
    public R update(@RequestBody MenuEntity menu) {
        menuService.updateById(menu);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("manager:menu:delete")
    public R delete(@RequestBody Long[] menuIds) {
        menuService.removeByIds(Arrays.asList(menuIds));
        return R.ok();
    }

    public void deleteRedisMenu() {
        List<String> keys = new ArrayList<>();
        keys.add("menus");
        keys.add("firstMenus");
        keys.add("secondMenus");
        stringRedisTemplate.delete(keys);
    }

}
