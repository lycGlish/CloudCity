package com.lyc.city.info.service.impl;

import com.alibaba.fastjson.JSON;
import com.lyc.city.entity.CityEntity;
import com.lyc.city.to.AllInfoTo;
import com.lyc.city.utils.PageUtils;
import com.lyc.city.utils.Query;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lyc.city.info.dao.CityDao;
import com.lyc.city.info.service.CityService;
import org.springframework.util.StringUtils;


@Service("cityService")
public class CityServiceImpl extends ServiceImpl<CityDao, CityEntity> implements CityService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CityEntity> page = this.page(
                new Query<CityEntity>().getPage(params),
                new QueryWrapper<CityEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据省份码获取所有的城市信息
     *
     * @param provinceCode 省份码
     * @return 城市类列表
     */
    @Override
    public List<CityEntity> getCityByProvinceCode(String provinceCode) {
        RLock cityLock = redissonClient.getLock("city-lock");
        cityLock.lock();
        try {
            String cities = stringRedisTemplate.opsForValue().get("city_" + provinceCode);
            if (StringUtils.isEmpty(cities)) {
                List<CityEntity> citiesProvinceCode = baseMapper.selectList(
                        new QueryWrapper<CityEntity>().eq("province_code", provinceCode));
                stringRedisTemplate.opsForValue().set("city_" + provinceCode, JSON.toJSONString(citiesProvinceCode), 1, TimeUnit.DAYS);
                return citiesProvinceCode;
            }
            return JSON.parseArray(cities, CityEntity.class);
        } finally {
            cityLock.unlock();
        }
    }

    /**
     * 根据城市码获取城市信息
     *
     * @param cityCode 城市码
     * @return 城市类
     */
    @Override
    public CityEntity getCityEntityByCityCode(String cityCode) {
        return baseMapper.selectOne(new QueryWrapper<CityEntity>().eq("city_code", cityCode));
    }

}