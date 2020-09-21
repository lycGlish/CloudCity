package com.lyc.city.camera.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lyc.city.camera.feign.InfoFeignService;
import com.lyc.city.camera.service.CameraService;
import com.lyc.city.constant.HttpRemoteConstant;
import com.lyc.city.entity.AreaEntity;
import com.lyc.city.entity.CityEntity;
import com.lyc.city.entity.CoordinateEntity;
import com.lyc.city.entity.ProvinceEntity;
import com.lyc.city.to.AllCameraTo;
import com.lyc.city.utils.HttpRemoteUtil;
import com.lyc.city.utils.PageUtils;
import com.lyc.city.utils.Query;
import com.lyc.city.camera.dao.CameraDao;
import com.lyc.city.entity.CameraEntity;
import com.lyc.city.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.StringUtils;

@Service("cameraService")
@Slf4j
public class CameraServiceImpl extends ServiceImpl<CameraDao, CameraEntity> implements CameraService {

    @Autowired
    private InfoFeignService infoFeignService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CameraEntity> page = this.page(
                new Query<CameraEntity>().getPage(params),
                new QueryWrapper<CameraEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 获取所有的摄像头信息
     *
     * @return 摄像头扩展类
     */
    @Override
    public List<AllCameraTo> getAllCamera() {
        RLock cameraLock = redissonClient.getLock("camera-lock");
        cameraLock.lock();
        try {
            String cameras = stringRedisTemplate.opsForValue().get("cameras");
            if (StringUtils.isEmpty(cameras)) {
                log.info("从redis中取出的camera信息" + cameras);
                List<AllCameraTo> allCameraToList;

                // 获取所有的摄像头信息
                List<CameraEntity> cameraEntities = baseMapper.selectList(new QueryWrapper<CameraEntity>());

                // 基础属性查询后拷贝到完整展示属性中
                allCameraToList = cameraEntities.stream().map(cameraEntity -> {
                    AllCameraTo allCameraTo = new AllCameraTo();
                    BeanUtils.copyProperties(cameraEntity, allCameraTo);
                    return allCameraTo;
                }).collect(Collectors.toList());

                // 远程调用areaService获取整个area对象
                allCameraToList = allCameraToList.stream().peek(allCameraTo -> {
                    R r = infoFeignService.getAreaByAreaId(allCameraTo.getCameraArea());
                    AreaEntity data = JSONObject.parseObject(JSON.toJSONString(r.get("data")), AreaEntity.class);
                    allCameraTo.setAreaEntity(data);
                }).collect(Collectors.toList());

                // 远程调用cityService获取整个city对象
                allCameraToList = allCameraToList.stream().peek(allCameraTo -> {
                    R r = infoFeignService.getCityEntityByCityCode(allCameraTo.getAreaEntity().getCityCode());
                    CityEntity data = JSONObject.parseObject(JSON.toJSONString(r.get("data")), CityEntity.class);
                    allCameraTo.setCityEntity(data);
                }).collect(Collectors.toList());

                // 远程调用provinceService获取整个province对象
                allCameraToList = allCameraToList.stream().peek(allCameraTo -> {
                    R r = infoFeignService.getProvinceByProvinceCode(allCameraTo.getCityEntity().getProvinceCode());
                    ProvinceEntity data = JSONObject.parseObject(JSON.toJSONString(r.get("data")), ProvinceEntity.class);
                    allCameraTo.setProvinceEntity(data);
                }).collect(Collectors.toList());

                // 远程调用coordinateService获取整个coordinate对象
                allCameraToList = allCameraToList.stream().peek(allCameraTo -> {
                    R r = infoFeignService.getCoordinateByCoordinateId(allCameraTo.getCameraId());
                    CoordinateEntity data = JSONObject.parseObject(JSON.toJSONString(r.get("data")), CoordinateEntity.class);
                    allCameraTo.setCoordinateEntity(data);
                }).collect(Collectors.toList());
                log.info("从DB中取出的camera信息" + allCameraToList);
                stringRedisTemplate.opsForValue().set("cameras", JSON.toJSONString(allCameraToList), 1, TimeUnit.DAYS);
                return allCameraToList;
            }
            return JSON.parseArray(cameras, AllCameraTo.class);
        } finally {
            cameraLock.unlock();
        }
    }

    /**
     * 存入新摄像头信息
     *
     * @param cameraTo 摄像头扩展类
     */
    @Override
    public void saveCamera(AllCameraTo cameraTo) {
        RLock cameraLock = redissonClient.getLock("camera-lock");
        cameraLock.lock();
        try {
            CameraEntity cameraEntity = new CameraEntity();
            // 属性对拷
            BeanUtils.copyProperties(cameraTo, cameraEntity);

            // 根据区县码获取整个省市县信息
            R area = infoFeignService.getAreaByAreaCode(cameraTo.getAreaCode());
            AreaEntity areaEntity = JSONObject.parseObject(JSON.toJSONString(area.get("data")), AreaEntity.class);
            cameraEntity.setCameraArea(areaEntity.getAreaId());
            R city = infoFeignService.getCityEntityByCityCode(areaEntity.getCityCode());
            CityEntity cityEntity = JSONObject.parseObject(JSON.toJSONString(city.get("data")), CityEntity.class);
            R province = infoFeignService.getProvinceByProvinceCode(cityEntity.getProvinceCode());
            ProvinceEntity provinceEntity = JSONObject.parseObject(JSON.toJSONString(province.get("data")), ProvinceEntity.class);
            String coordinateApi = HttpRemoteUtil
                    .HttpRemotePackage(provinceEntity.getProvinceName() + cityEntity.getCityName()
                                    + areaEntity.getAreaName() + cameraTo.getCameraDescription()
                            , HttpRemoteConstant.HttpEnum.HTTP_MAP_COORDINATE.getCode());
            if (coordinateApi != null) {
                // 查询到坐标后,处理字符串得到json格式
                coordinateApi = coordinateApi.substring(27);
                coordinateApi = coordinateApi.substring(0, coordinateApi.length() - 1);
                JSONObject json = JSONObject.parseObject(coordinateApi);
                if (json.get("status").toString().equals("0")) {
                    log.info("百度地图查找到匹配的经纬度！");
                    cameraTo.setLongitude(json.getJSONObject("result").getJSONObject("location").getString("lng"));
                    cameraTo.setLatitude(json.getJSONObject("result").getJSONObject("location").getString("lat"));
                } else {
                    log.info("百度地图查没有找到匹配的经纬度！");
                }
            }
            // 查询坐标是否存在
            R data = infoFeignService.selectExistCoordinateByAll(cameraTo.getLongitude(), cameraTo.getLatitude());
            CoordinateEntity coordinateEntity = JSONObject.parseObject(JSON.toJSONString(data.get("data")), CoordinateEntity.class);
            Long coordinateId = null;
            if (coordinateEntity == null) {
                // 坐标不存在,保存为新坐标并返回坐标id
                R r = infoFeignService.saveBackCoordinateId(cameraTo.getLongitude(), cameraTo.getLatitude());
                coordinateId = JSONObject.parseObject(JSON.toJSONString(r.get("data")), Long.class);
            }
            // 坐标存在，直接存入坐标id
            cameraEntity.setCameraCoordinate(coordinateId);

            baseMapper.insert(cameraEntity);
            stringRedisTemplate.delete("cameras");
        } finally {
            cameraLock.unlock();
        }
    }

}