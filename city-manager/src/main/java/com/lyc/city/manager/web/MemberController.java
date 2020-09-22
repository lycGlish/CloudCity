package com.lyc.city.manager.web;

import com.lyc.city.manager.entity.MenuEntity;
import com.lyc.city.manager.feign.MemberFeignService;
import com.lyc.city.manager.service.MenuService;
import com.lyc.city.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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
        return "redirect:http://localhost:7000";
    }

    @GetMapping("member")
    public String member(Model model) {

        // 一级分类
        List<MenuEntity> firstMenus = menuService.getFirstMenu();
        // 二级分类
        List<MenuEntity> secondMenus = menuService.getSecondMenu();

        model.addAttribute("firstMenus", firstMenus);
        model.addAttribute("secondMenus", secondMenus);

        return "member/member";
    }
}
