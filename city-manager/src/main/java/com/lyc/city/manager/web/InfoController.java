package com.lyc.city.manager.web;

import com.lyc.city.manager.entity.MenuEntity;
import com.lyc.city.manager.entity.TaskEntity;
import com.lyc.city.manager.service.MenuService;
import com.lyc.city.manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author lyc
 * @date 2020/8/27 10:16
 */
@Controller
public class InfoController {

    @Autowired
    private MenuService menuService;

    @GetMapping("hideInfo")
    public String hideInfo(Model model) {

        // 一级分类
        List<MenuEntity> firstMenus = menuService.getFirstMenu();
        // 二级分类
        List<MenuEntity> secondMenus = menuService.getSecondMenu();

        model.addAttribute("firstMenus", firstMenus);
        model.addAttribute("secondMenus", secondMenus);

        return "info/hideInfo";
    }

    @GetMapping("recordInfo")
    public String recordInfo(Model model) {

        // 一级分类
        List<MenuEntity> firstMenus = menuService.getFirstMenu();
        // 二级分类
        List<MenuEntity> secondMenus = menuService.getSecondMenu();

        model.addAttribute("firstMenus", firstMenus);
        model.addAttribute("secondMenus", secondMenus);

        return "info/recordInfo";
    }

    @GetMapping("dangerInfo")
    public String dangerInfo(Model model) {
        // 一级分类
        List<MenuEntity> firstMenus = menuService.getFirstMenu();
        // 二级分类
        List<MenuEntity> secondMenus = menuService.getSecondMenu();

        model.addAttribute("firstMenus", firstMenus);
        model.addAttribute("secondMenus", secondMenus);
        return "info/dangerInfo";
    }
}
