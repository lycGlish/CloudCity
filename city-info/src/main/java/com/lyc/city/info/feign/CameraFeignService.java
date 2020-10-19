package com.lyc.city.info.feign;

import com.lyc.city.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lyc
 * @date 2020/10/16 16:37
 */
@FeignClient("city-camera")
public interface CameraFeignService {

    @GetMapping("/camera/camera/getCameraById")
    R getCameraById(@RequestParam("cameraId") Long cameraId);
}
