package com.lyc.city.manager.web;

import com.lyc.city.manager.entity.MenuEntity;
import com.lyc.city.manager.entity.TaskEntity;
import com.lyc.city.manager.feign.InfoFeignService;
import com.lyc.city.manager.service.MenuService;
import com.lyc.city.manager.service.TaskService;
import com.lyc.city.utils.R;
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
public class IndexController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private InfoFeignService infoFeignService;

    @GetMapping({"/", "/index"})
    public String indexPage(Model model) {

        // 图片总数
        R imageCount = infoFeignService.count();

        // 待做任务
        List<TaskEntity> taskEntityList = taskService.getAllTasks();

        // 一级分类
        List<MenuEntity> firstMenus = menuService.getFirstMenu();
        // 二级分类
        List<MenuEntity> secondMenus = menuService.getSecondMenu();

        model.addAttribute("imageCount", imageCount.get("data"));
        model.addAttribute("allTask", taskEntityList);
        model.addAttribute("firstMenus", firstMenus);
        model.addAttribute("secondMenus", secondMenus);
        return "index";
    }
}
