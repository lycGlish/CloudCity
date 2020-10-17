package com.lyc.city.camera.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.lyc.city.camera.service.CameraService;
import com.lyc.city.to.AllCameraTo;
import com.lyc.city.utils.PageUtils;
import com.lyc.city.utils.R;
import com.lyc.city.entity.CameraEntity;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author lyc
 * @email 708901735@qq.com
 * @date 2020-09-07 10:58:39
 */
@RestController
@RequestMapping("camera/camera")
@CrossOrigin
public class CameraController {
    @Autowired
    private CameraService cameraService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @GetMapping("/getCameraById")
    public R getCameraById(@RequestParam Long cameraId){
        CameraEntity cameraEntity = cameraService.getCameraById(cameraId);
        return R.ok().put("data",cameraEntity);
    }

    @PostMapping("/updateCamera")
    public R updateCamera(@RequestBody AllCameraTo cameraTo) {
        RLock cameraLock = redissonClient.getLock("camera-lock");
        cameraLock.lock();
        try {
            CameraEntity cameraEntity = new CameraEntity();
            // 属性对拷
            BeanUtils.copyProperties(cameraTo, cameraEntity);
            cameraService.updateById(cameraEntity);
            stringRedisTemplate.delete("cameras");
        } finally {
            cameraLock.unlock();
        }

        return R.ok();
    }

    @PostMapping("/addCamera")
    public R addCamera(@RequestBody AllCameraTo cameraTo) {
        cameraService.saveCamera(cameraTo);
        return R.ok();
    }

    @GetMapping("/allCamera")
    public R allCamera() {
        List<AllCameraTo> allCameraToList = cameraService.getAllCamera();
        return R.ok().put("data", allCameraToList);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("camera:camera:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = cameraService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{cameraId}")
    //@RequiresPermissions("camera:camera:info")
    public R info(@PathVariable("cameraId") Long cameraId) {
        CameraEntity camera = cameraService.getById(cameraId);
        return R.ok().put("camera", camera);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("camera:camera:delete")
    public R delete(@RequestBody Long[] cameraIds) {
        cameraService.removeByIds(Arrays.asList(cameraIds));
        return R.ok();
    }

}
