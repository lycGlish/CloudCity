package com.lyc.city.manager.web;

import com.lyc.city.manager.entity.MenuEntity;
import com.lyc.city.manager.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author lyc
 * @date 2020/9/6 10:39
 */
@Controller
public class MenuWebController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/systemMenu")
    public String systemMenu(Model model){

        // 一级分类
        List<MenuEntity> firstMenus = menuService.getFirstMenu();
        // 二级分类
        List<MenuEntity> secondMenus = menuService.getSecondMenu();

        model.addAttribute("firstMenus", firstMenus);
        model.addAttribute("secondMenus", secondMenus);
        return "menu/systemMenu";
    }
}
