package com.lyc.city.manager.web;

import com.lyc.city.manager.entity.MenuEntity;
import com.lyc.city.manager.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author lyc
 * @date 2020/9/14 20:54
 */
@Controller
public class MemberController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("member");
        return "redirect:http://localhost:88/city-info/";
    }

    @GetMapping("member")
    public String member(Model model) {

        // 一级分类
        List<MenuEntity> firstMenus = menuService.getFirstMenu();
        // 二级分类
        List<MenuEntity> secondMenus = menuService.getSecondMenu();

        model.addAttribute("firstMenus", firstMenus);
        model.addAttribute("secondMenus", secondMenus);

        return "member/memberList";
    }
}
