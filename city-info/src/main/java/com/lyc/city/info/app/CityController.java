package com.lyc.city.info.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.lyc.city.entity.AreaEntity;
import com.lyc.city.utils.PageUtils;
import com.lyc.city.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lyc.city.entity.CityEntity;
import com.lyc.city.info.service.CityService;

/**
 * 
 *
 * @author lyc
 * @email 708901735@qq.com
 * @date 2020-08-20 15:15:31
 */
@RestController
@RequestMapping("info/city")
public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping("/getCityEntityByCityCode")
    public R getCityEntityByCityCode(String cityCode){

        CityEntity data = cityService.getCityEntityByCityCode(cityCode);
        return R.ok().put("data",data);
    }

    @GetMapping("/getCityByProvinceCode")
    public R getCityByProvinceCode(String provinceCode){

        List<CityEntity> data = cityService.getCityByProvinceCode(provinceCode);
        return R.ok().put("data",data);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("info:city:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cityService.queryPage(params);

        return R.ok().put("page", page);
    }
}
