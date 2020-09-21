package com.lyc.city.manager.web;

import com.lyc.city.manager.entity.MenuEntity;
import com.lyc.city.manager.feign.InfoFeignService;
import com.lyc.city.manager.service.MenuService;
import com.lyc.city.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author lyc
 * @date 2020/9/7 11:24
 */
@Controller
public class CameraController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private InfoFeignService infoFeignService;

    @GetMapping("camera")
    public String camera(Model model) {

        // 获取所有省份信息
        R r = infoFeignService.getAllProvince();

        // 一级分类
        List<MenuEntity> firstMenus = menuService.getFirstMenu();
        // 二级分类
        List<MenuEntity> secondMenus = menuService.getSecondMenu();

        model.addAttribute("allProvince",r.get("data"));
        model.addAttribute("firstMenus", firstMenus);
        model.addAttribute("secondMenus", secondMenus);

        return "camera/camera";
    }
}
