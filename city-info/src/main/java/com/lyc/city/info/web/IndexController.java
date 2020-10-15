package com.lyc.city.info.web;

import com.lyc.city.constant.FlagConstant;
import com.lyc.city.constant.IdentifyConstant;
import com.lyc.city.entity.ProvinceEntity;
import com.lyc.city.info.service.InfoService;
import com.lyc.city.info.service.ProvinceService;
import com.lyc.city.to.AllInfoTo;
import com.lyc.city.utils.Constant;
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
    private ProvinceService provinceService;

    @Autowired
    private InfoService infoService;

    @GetMapping({"/","/index"})
    public String indexPage(Model model){
        List<ProvinceEntity> allProvince = provinceService.getAllProvince();

        List<AllInfoTo> statusInfos = infoService.getStatusInfo(IdentifyConstant.IdentifyStatusEnum.IMAGE_IDENTIFY_ERROR.getCode()
                , FlagConstant.InfoEnum.INFO_UP.getCode());

        model.addAttribute("allProvince",allProvince);
        model.addAttribute("statusInfos",statusInfos);
        return "index";
    }
}
