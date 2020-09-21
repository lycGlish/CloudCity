package com.lyc.city.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.city.entity.CityEntity;
import com.lyc.city.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author lyc
 * @email 708901735@qq.com
 * @date 2020-08-20 15:15:31
 */
public interface CityService extends IService<CityEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CityEntity> getCityByProvinceCode(String provinceCode);

    CityEntity getCityEntityByCityCode(String cityCode);
}

