package com.lyc.city.manager.feign;

import com.lyc.city.to.AllCameraTo;
import com.lyc.city.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author lyc
 * @date 2020/9/7 11:15
 */
@FeignClient("city-camera")
public interface CameraFeignService {

    @PostMapping("/camera/camera/updateCamera")
    R updateCamera(@RequestBody AllCameraTo cameraTo);

    @PostMapping("/camera/camera/addCamera")
    R addCamera(@RequestBody AllCameraTo cameraTo);

    @GetMapping("/camera/camera/allCamera")
    R allCamera();
}
