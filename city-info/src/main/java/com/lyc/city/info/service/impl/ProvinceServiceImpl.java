package com.lyc.city.info.service.impl;

import com.alibaba.fastjson.JSON;
import com.lyc.city.utils.PageUtils;
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
import com.lyc.city.utils.Query;

import com.lyc.city.info.dao.ProvinceDao;
import com.lyc.city.entity.ProvinceEntity;
import com.lyc.city.info.service.ProvinceService;


@Service("provinceService")
public class ProvinceServiceImpl extends ServiceImpl<ProvinceDao, ProvinceEntity> implements ProvinceService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProvinceEntity> page = this.page(
                new Query<ProvinceEntity>().getPage(params),
                new QueryWrapper<ProvinceEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 获取所有省份
     *
     * @return 省份类列表
     */
    @Override
    public List<ProvinceEntity> getAllProvince() {
        RLock provinceLock = redissonClient.getLock("province-lock");
        provinceLock.lock();
        try {
            List<ProvinceEntity> provinces = baseMapper.selectList(new QueryWrapper<>());
            stringRedisTemplate.opsForValue().set("provinces", JSON.toJSONString(provinces), 1, TimeUnit.DAYS);
            return provinces;
        } finally {
            provinceLock.unlock();
        }

    }

    /**
     * 根据省份码获取省份信息
     *
     * @param provinceCode 省份码
     * @return 省份类
     */
    @Override
    public ProvinceEntity getProvinceEntityByProvinceCode(String provinceCode) {
        return baseMapper.selectOne(new QueryWrapper<ProvinceEntity>().eq("province_code", provinceCode));
    }

}