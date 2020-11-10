package com.lyc.city.info.app;

import java.util.Arrays;
import java.util.Map;

import com.lyc.city.to.AllCameraTo;
import com.lyc.city.utils.PageUtils;
import com.lyc.city.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lyc.city.entity.CoordinateEntity;
import com.lyc.city.info.service.CoordinateService;

/**
 * @author lyc
 * @email 708901735@qq.com
 * @date 2020-08-20 15:15:31
 */
@RestController
@RequestMapping("info/coordinate")
public class CoordinateController {
    @Autowired
    private CoordinateService coordinateService;

    @GetMapping("/saveBackCoordinateId")
    public R saveBackCoordinateId(String longitude, String latitude){
        Long coordinateId = coordinateService.saveBackCoordinateId(longitude, latitude);
        return R.ok().put("data",coordinateId);
    }

    @GetMapping("/selectExistCoordinateByAll")
    public R selectExistCoordinateByAll(String longitude, String latitude) {
        CoordinateEntity coordinateEntity = coordinateService.selectExistCoordinateByAll(longitude, latitude);
        return R.ok().put("data",coordinateEntity);
    }

    @GetMapping("/getCoordinateByCoordinateId")
    public R getCoordinateByCoordinateId(Long coordinateId) {
        CoordinateEntity data = coordinateService.selectCoordinateById(coordinateId);
        return R.ok().put("data", data);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("info:coordinate:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = coordinateService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{coordinateId}")
    //@RequiresPermissions("info:coordinate:info")
    public R info(@PathVariable("coordinateId") Long coordinateId) {
        CoordinateEntity coordinate = coordinateService.getById(coordinateId);

        return R.ok().put("coordinate", coordinate);
    }
}
