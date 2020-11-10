package com.lyc.city.info.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.lyc.city.utils.PageUtils;
import com.lyc.city.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lyc.city.entity.ProvinceEntity;
import com.lyc.city.info.service.ProvinceService;

/**
 * 
 *
 * @author lyc
 * @email 708901735@qq.com
 * @date 2020-08-20 15:15:31
 */
@RestController
@RequestMapping("info/province")
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;

    @GetMapping("/getProvinceByProvinceCode")
    public R getProvinceByProvinceCode(String provinceCode){
        ProvinceEntity data = provinceService.getProvinceEntityByProvinceCode(provinceCode);
        return R.ok().put("data",data);
    }

    @GetMapping("/getAllProvince")
    public R getAllProvince(){

        List<ProvinceEntity> data = provinceService.getAllProvince();
        return R.ok().put("data",data);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("info:province:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = provinceService.queryPage(params);

        return R.ok().put("page", page);
    }
}
