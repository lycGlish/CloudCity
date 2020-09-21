package com.lyc.city.user.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author lyc
 * @date 2020/9/14 20:45
 */
@Controller
public class RegisterController {

    @GetMapping("/register")
    public String register(){
        return "register";
    }
}
