package com.lyc.city.manager.feign;

import com.lyc.city.entity.InfoEntity;
import com.lyc.city.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author lyc
 * @date 2020/9/2 16:37
 */
@FeignClient("city-info")
public interface InfoFeignService {

    @GetMapping("/info/info/count")
    R count();

    @GetMapping("/info/area/getAreaByCityCode")
    R getAreaByCityCode(@RequestParam String cityCode);

    @GetMapping("/info/city/getCityByProvinceCode")
    R getCityByProvinceCode(@RequestParam String provinceCode);

    @GetMapping("/info/province/getAllProvince")
    R getAllProvince();

    @GetMapping("/info/info/deleteLogicInfoByInfoId/{infoId}")
    R deleteLogicInfoByInfoId(@PathVariable("infoId") Long infoId);

    @PostMapping("/info/info/updateInfo")
    R updateInfo(@RequestBody InfoEntity infoEntity);

    @GetMapping("/info/info/deleteInfoByInfoId/{infoId}")
    R deleteInfoByInfoId(@PathVariable("infoId") Long infoId);

    @GetMapping("/info/info/{status}/{flag}/info")
    R statusInfo(@PathVariable("status") Integer status,@PathVariable("flag") Integer infoFlag);
}
