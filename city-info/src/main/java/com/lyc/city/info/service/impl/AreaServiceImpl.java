package com.lyc.city.info.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.city.entity.CityEntity;
import com.lyc.city.info.dao.AreaDao;
import com.lyc.city.entity.AreaEntity;
import com.lyc.city.info.service.AreaService;
import com.lyc.city.utils.PageUtils;
import com.lyc.city.utils.Query;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service("areaService")
public class AreaServiceImpl extends ServiceImpl<AreaDao, AreaEntity> implements AreaService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AreaEntity> page = this.page(
                new Query<AreaEntity>().getPage(params),
                new QueryWrapper<AreaEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据城市码获取对应的所有区县信息
     *
     * @param cityCode 城市码
     * @return 区县类列表
     */
    @Override
    public List<AreaEntity> getAreaByCityCode(String cityCode) {
        RLock areaLock = redissonClient.getLock("area-lock");
        areaLock.lock();
        try {
            String areas = stringRedisTemplate.opsForValue().get("area" + cityCode);
            if (StringUtils.isEmpty(areas)) {
                List<AreaEntity> areaCityCode = baseMapper.selectList(
                        new QueryWrapper<AreaEntity>().eq("city_code", cityCode));
                stringRedisTemplate.opsForValue().set("area" + cityCode, JSON.toJSONString(areaCityCode), 1, TimeUnit.DAYS);
                return areaCityCode;
            }
            return JSON.parseArray(areas, AreaEntity.class);
        } finally {
            areaLock.unlock();
        }
    }

    /**
     * 根据区县码获取区县信息
     *
     * @param areaCode 区县码
     * @return 区县类
     */
    @Override
    public AreaEntity getAreaEntityByAreaCode(String areaCode) {
        return baseMapper.selectOne(new QueryWrapper<AreaEntity>().eq("area_code", areaCode));
    }

    /**
     * 根据区县id获取区县信息
     *
     * @param areaId 区县id
     * @return 区县类
     */
    @Override
    public AreaEntity getAreaByAreaId(Integer areaId) {
        return baseMapper.selectById(areaId);
    }
}