package com.lyc.city.manager.app;

import com.lyc.city.manager.feign.CameraFeignService;
import com.lyc.city.to.AllCameraTo;
import com.lyc.city.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lyc
 * @date 2020/9/7 11:18
 */
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("camera/feign")
public class CameraFeignController {

    @Autowired
    private CameraFeignService cameraFeignService;

    @PostMapping("/updateCamera")
    public R updateCamera(@RequestBody AllCameraTo cameraTo) {
        R r = cameraFeignService.updateCamera(cameraTo);
        if (!r.getCode().equals(0)) {
            log.error("远程调用查询摄像头接口失败！");
        }
        return r;
    }

    @PostMapping("/addCamera")
    public R addCamera(@RequestBody AllCameraTo cameraTo) {
        R r = cameraFeignService.addCamera(cameraTo);
        if (!r.getCode().equals(0)) {
            log.error("远程调用查询摄像头接口失败！");
        }
        return r;
    }

    @GetMapping("/allCamera")
    public R allCamera() {
        R r = cameraFeignService.allCamera();
        if (!r.getCode().equals(0)) {
            log.error("远程调用查询摄像头接口失败！");
        }
        return r;
    }
}
