package com.lyc.city.info.service.impl;

import com.lyc.city.info.vo.InfoVo;
import com.lyc.city.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.city.utils.Query;

import com.lyc.city.info.dao.CoordinateDao;
import com.lyc.city.entity.CoordinateEntity;
import com.lyc.city.info.service.CoordinateService;


@Service("coordinateService")
public class CoordinateServiceImpl extends ServiceImpl<CoordinateDao, CoordinateEntity> implements CoordinateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CoordinateEntity> page = this.page(
                new Query<CoordinateEntity>().getPage(params),
                new QueryWrapper<CoordinateEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据经纬度信息查询坐标
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @return 坐标类
     */
    @Override
    public CoordinateEntity selectExistCoordinateByAll(String longitude, String latitude) {
        return baseMapper.selectExistCoordinateByAll(longitude, latitude);
    }

    /**
     * 存入新坐标
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @return 新存入的坐标id
     */
    @Override
    public Long saveBackCoordinateId(String longitude, String latitude) {
        // TODO 对保存的坐标合理性进行校验
        CoordinateEntity coordinateEntity = new CoordinateEntity();
        coordinateEntity.setLongitude(longitude);
        coordinateEntity.setLatitude(latitude);
        baseMapper.insertReturnLong(coordinateEntity);
        return coordinateEntity.getCoordinateId();
    }

    /**
     * 根据坐标id查询坐标
     *
     * @param coordinateId 坐标id
     * @return 坐标类
     */
    @Override
    public CoordinateEntity selectCoordinateById(Long coordinateId) {
        return baseMapper.selectById(coordinateId);
    }

}