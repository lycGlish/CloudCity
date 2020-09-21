package com.lyc.city.info.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.lyc.city.utils.PageUtils;
import com.lyc.city.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lyc.city.entity.AreaEntity;
import com.lyc.city.info.service.AreaService;

/**
 * 
 *
 * @author lyc
 * @email 708901735@qq.com
 * @date 2020-08-20 15:15:31
 */
@RestController
@RequestMapping("info/area")
public class AreaController {
    @Autowired
    private AreaService areaService;

    @GetMapping("/getAreaByAreaCode")
    public R getAreaByAreaId(String areaCode){
        AreaEntity areaEntity = areaService.getAreaEntityByAreaCode(areaCode);
        return R.ok().put("data",areaEntity);
    }

    @GetMapping("/getAreaByAreaId")
    public R getAreaByAreaId(Integer areaId){
        AreaEntity areaEntity = areaService.getAreaByAreaId(areaId);
        return R.ok().put("data",areaEntity);
    }

    /**
     * 查询城市码下的所有区县
     * @param cityCode 城市码
     * @return 区县类
     */
    @GetMapping("/getAreaByCityCode")
    public R getAreaByCityCode(String cityCode){
        List<AreaEntity> data = areaService.getAreaByCityCode(cityCode);
        return R.ok().put("data",data);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("info:area:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = areaService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{areaId}")
    //@RequiresPermissions("info:area:info")
    public R info(@PathVariable("areaId") Integer areaId){
		AreaEntity area = areaService.getById(areaId);
        return R.ok().put("area", area);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("info:area:save")
    public R save(@RequestBody AreaEntity area){
		areaService.save(area);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("info:area:update")
    public R update(@RequestBody AreaEntity area){
		areaService.updateById(area);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("info:area:delete")
    public R delete(@RequestBody Integer[] areaIds){
		areaService.removeByIds(Arrays.asList(areaIds));
        return R.ok();
    }

}
