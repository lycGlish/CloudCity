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


    /**
     * 信息
     */
    @RequestMapping("/info/{provinceId}")
    //@RequiresPermissions("info:province:info")
    public R info(@PathVariable("provinceId") Integer provinceId){
		ProvinceEntity province = provinceService.getById(provinceId);

        return R.ok().put("province", province);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("info:province:save")
    public R save(@RequestBody ProvinceEntity province){
		provinceService.save(province);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("info:province:update")
    public R update(@RequestBody ProvinceEntity province){
		provinceService.updateById(province);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("info:province:delete")
    public R delete(@RequestBody Integer[] provinceIds){
		provinceService.removeByIds(Arrays.asList(provinceIds));

        return R.ok();
    }

}
