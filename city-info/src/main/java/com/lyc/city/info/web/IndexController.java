package com.lyc.city.info.web;

import com.lyc.city.entity.ProvinceEntity;
import com.lyc.city.info.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author lyc
 * @date 2020/8/20 16:18
 */
@Controller
public class IndexController {

    @Autowired
    ProvinceService provinceService;

    @GetMapping({"/","/index"})
    public String indexPage(Model model){
        List<ProvinceEntity> allProvince = provinceService.getAllProvince();
        model.addAttribute("allProvince",allProvince);
        return "index";
    }
}
