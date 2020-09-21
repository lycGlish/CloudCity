package com.lyc.city.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.city.entity.CoordinateEntity;
import com.lyc.city.info.vo.InfoVo;
import com.lyc.city.utils.PageUtils;

import java.util.Map;

/**
 * 
 *
 * @author lyc
 * @email 708901735@qq.com
 * @date 2020-08-20 15:15:31
 */
public interface CoordinateService extends IService<CoordinateEntity> {

    PageUtils queryPage(Map<String, Object> params);

    CoordinateEntity selectExistCoordinateByAll(String longitude,String latitude);

    Long saveBackCoordinateId(String longitude,String latitude);

    CoordinateEntity selectCoordinateById(Long coordinateId);
}

