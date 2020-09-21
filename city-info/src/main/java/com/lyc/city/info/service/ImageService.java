package com.lyc.city.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.city.entity.ImageEntity;
import com.lyc.city.info.vo.InfoVo;
import com.lyc.city.utils.PageUtils;

import java.util.Map;

/**
 * @author lyc
 * @email 708901735@qq.com
 * @date 2020-08-20 15:15:31
 */
public interface ImageService extends IService<ImageEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Long saveBackImageId(InfoVo infoVo);

    Integer identifyImage(String imgUrl);

    ImageEntity selectImageEntityByImageId(Long infoImage);
}

