package com.lyc.city.manager.app;

import com.lyc.city.entity.InfoEntity;
import com.lyc.city.manager.feign.InfoFeignService;
import com.lyc.city.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lyc
 * @date 2020/9/2 16:26
 */
@RestController
@RequestMapping("info/feign")
@CrossOrigin
@Slf4j
public class InfoFeignController {

    @Autowired
    private InfoFeignService infoFeignService;

    @GetMapping("/getAreaByCityCode")
    public R getAreaByCityCode(String cityCode) {
        R r = infoFeignService.getAreaByCityCode(cityCode);
        if (!r.getCode().equals(0)) {
            log.error("远程调用查询区县接口失败！");
        }
        return r;
    }

    @GetMapping("/getCityByProvinceCode")
    public R getCityByProvinceCode(String provinceCode) {
        R r = infoFeignService.getCityByProvinceCode(provinceCode);
        if (!r.getCode().equals(0)) {
            log.error("远程调用查询城市接口失败！");
        }
        return r;
    }

    @PostMapping("/updateInfo")
    public R updateInfo(@RequestBody InfoEntity infoEntity) {
        R r = infoFeignService.updateInfo(infoEntity);
        if (!r.getCode().equals(0)) {
            log.error("远程调用修改消息接口失败！");
        }
        return r;
    }

    @GetMapping("/deleteLogicInfoByInfoId/{infoId}")
    public R deleteLogicInfoByInfoId(@PathVariable("infoId") Long infoId) {
        R r = infoFeignService.deleteLogicInfoByInfoId(infoId);
        if (!r.getCode().equals(0)) {
            log.error("远程调用逻辑删除消息接口失败！");
        }
        return r;
    }

    @GetMapping("/deleteInfoByInfoId/{infoId}")
    public R deleteInfoByInfoId(@PathVariable("infoId") Long infoId) {
        R r = infoFeignService.deleteInfoByInfoId(infoId);
        if (!r.getCode().equals(0)) {
            log.error("远程调用物理删除消息接口失败！");
        }
        return r;
    }

    @GetMapping("/{status}/{flag}/info")
    public R statusInfo(@PathVariable("status") Integer status, @PathVariable("flag") Integer infoFlag) {
        R r = infoFeignService.statusInfo(status, infoFlag);
        if (!r.getCode().equals(0)) {
            log.error("远程调用查询消息接口失败！");
        }
        return r;
    }
}
