package com.lyc.city.info.dao;

import com.lyc.city.entity.CoordinateEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author lyc
 * @email 708901735@qq.com
 * @date 2020-08-20 15:15:31
 */
@Mapper
public interface CoordinateDao extends BaseMapper<CoordinateEntity> {

    CoordinateEntity selectExistCoordinateByAll(@Param("longitude") String longitude,@Param("latitude") String latitude);

    Long insertReturnLong(CoordinateEntity coordinateEntity);
}
