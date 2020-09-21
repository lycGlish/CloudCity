package com.lyc.city.camera.feign;

import com.lyc.city.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lyc
 * @date 2020/9/7 11:53
 */
@FeignClient("city-info")
public interface InfoFeignService {

    @GetMapping("/info/coordinate/saveBackCoordinateId")
    R saveBackCoordinateId(@RequestParam("longitude") String longitude, @RequestParam("latitude") String latitude);

    @GetMapping("/info/coordinate/selectExistCoordinateByAll")
    R selectExistCoordinateByAll(@RequestParam("longitude") String longitude, @RequestParam("latitude") String latitude);

    @GetMapping("/info/coordinate/getCoordinateByCoordinateId")
    R getCoordinateByCoordinateId(@RequestParam("coordinateId") Long coordinateId);

    @GetMapping("/info/area/getAreaByAreaCode")
    R getAreaByAreaCode(@RequestParam("areaCode") String areaCode);

    @GetMapping("/info/area/getAreaByAreaId")
    R getAreaByAreaId(@RequestParam("areaId") Integer areaId);

    @GetMapping("/info/city/getCityEntityByCityCode")
    R getCityEntityByCityCode(@RequestParam("cityCode") String cityCode);

    @GetMapping("/info/province/getProvinceByProvinceCode")
    R getProvinceByProvinceCode(@RequestParam("provinceCode") String provinceCode);
}
