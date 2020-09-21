package com.lyc.city.camera.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.city.to.AllCameraTo;
import com.lyc.city.utils.PageUtils;
import com.lyc.city.entity.CameraEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author lyc
 * @email 708901735@qq.com
 * @date 2020-09-07 10:58:39
 */
public interface CameraService extends IService<CameraEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<AllCameraTo> getAllCamera();

    void saveCamera(AllCameraTo cameraTo);

}

