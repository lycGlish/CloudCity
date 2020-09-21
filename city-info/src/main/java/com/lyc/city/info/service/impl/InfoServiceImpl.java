package com.lyc.city.info.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.lyc.city.constant.FlagConstant;
import com.lyc.city.constant.HttpRemoteConstant;
import com.lyc.city.constant.IdentifyConstant;
import com.lyc.city.constant.SourceConstant;
import com.lyc.city.entity.*;
import com.lyc.city.info.feign.MemberFeignService;
import com.lyc.city.info.service.*;
import com.lyc.city.info.vo.InfoVo;
import com.lyc.city.to.AllInfoTo;
import com.lyc.city.utils.HttpRemoteUtil;
import com.lyc.city.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.city.utils.Query;

import com.lyc.city.info.dao.InfoDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("infoService")
@Slf4j
public class InfoServiceImpl extends ServiceImpl<InfoDao, InfoEntity> implements InfoService {

    @Autowired
    private ImageService imageService;

    @Autowired
    private CoordinateService coordinateService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private CityService cityService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private MemberFeignService memberFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InfoEntity> page = this.page(
                new Query<InfoEntity>().getPage(params),
                new QueryWrapper<InfoEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 用户渠道存入信息
     *
     * @param infoVo 信息扩展类
     */
    @Override
    public void saveInfoByUser(InfoVo infoVo) {
        RLock infoLock = redissonClient.getLock("info-lock");
        infoLock.lock();
        try {
            InfoEntity infoEntity = new InfoEntity();

            // 属性对拷
            BeanUtils.copyProperties(infoVo, infoEntity);

            // 调用图片service将图片存入数据库并返回图片id
            Long imageId = imageService.saveBackImageId(infoVo);
            infoEntity.setInfoImage(imageId);

            if (infoVo.getLongitude().length() == 0 && infoVo.getLatitude().length() == 0) {
                // 图片自带坐标为空,通过用户输入地址描述调用百度地图查询坐标位置
                // 根据区县码获取整个省市县信息
                AreaEntity areaEntity = areaService.getAreaEntityByAreaCode(infoVo.getAreaCode());
                infoEntity.setInfoArea(areaEntity.getAreaId());
                CityEntity cityEntity = cityService.getCityEntityByCityCode(areaEntity.getCityCode());
                ProvinceEntity provinceEntity = provinceService.getProvinceEntityByProvinceCode(cityEntity.getProvinceCode());
                // 调用百度地图远程api查询用户输入信息的坐标
                String coordinateApi = HttpRemoteUtil
                        .HttpRemotePackage(provinceEntity.getProvinceName() + cityEntity.getCityName()
                                        + areaEntity.getAreaName() + infoVo.getInfoDescription()
                                , HttpRemoteConstant.HttpEnum.HTTP_MAP_COORDINATE.getCode());
                if (coordinateApi != null) {
                    // 查询到坐标后,处理字符串得到json格式
                    coordinateApi = coordinateApi.substring(27);
                    coordinateApi = coordinateApi.substring(0, coordinateApi.length() - 1);
                    JSONObject json = JSONObject.parseObject(coordinateApi);
                    if (json.get("status").toString().equals("0")) {
                        log.info("百度地图查找到匹配的经纬度！");
                        infoVo.setLongitude(json.getJSONObject("result").getJSONObject("location").getString("lng"));
                        infoVo.setLatitude(json.getJSONObject("result").getJSONObject("location").getString("lat"));
                    } else {
                        log.info("百度地图查没有找到匹配的经纬度！");
                    }
                }
            }
            // 查询坐标是否存在
            Long coordinateId = null;
            CoordinateEntity coordinateEntity = coordinateService.
                    selectExistCoordinateByAll(infoVo.getLongitude(), infoVo.getLatitude());
            if (coordinateEntity == null) {
                // 坐标不存在插入坐标并返回坐标id
                coordinateId = coordinateService.saveBackCoordinateId(infoVo.getLongitude(), infoVo.getLatitude());
            }
            // 坐标存在，直接存入坐标id
            infoEntity.setInfoCoordinate(coordinateId);

            // 调用远程图像识别服务识别图片并返回状态值
            Integer imageStatus = imageService.identifyImage(infoVo.getImgUrl());
            if (imageStatus != null) {
                infoEntity.setInfoStatus(imageStatus);
            } else {
                infoEntity.setInfoStatus(0);
                log.error("远程识别图片识别结果失败！");
            }

            // 首次上传消息均为系统识别图像
            infoEntity.setInfoIdentify(IdentifyConstant.IdentifyEnum.IMAGE_IDENTIFY_SYSTEM.getCode());

            // 该路径上传图片均为用户上传
            infoEntity.setInfoSource(SourceConstant.ImageEnum.IMAGE_SOURCE_USER.getCode());

            // 封装消息时间
            infoEntity.setInfoCreateTime(new Date());
            infoEntity.setInfoUpdateTime(new Date());

            // 消息默认展示
            infoEntity.setInfoFlag(FlagConstant.InfoEnum.INFO_UP.getCode());

            baseMapper.insert(infoEntity);
            deleteRedisInfo();
        } finally {
            infoLock.unlock();
        }

    }

    /**
     * 查询不同状态的信息
     *
     * @param status   信息道路状态(0识别错误1无积水2积水3内涝4冰雪)
     * @param infoFlag 信息展示状态(0不展示1展示2全部)
     * @return 信息扩展类列表
     */
    @Override
    public List<AllInfoTo> getStatusInfo(Integer status, Integer infoFlag) {

        // TODO 目前分页采用客户端分页，后期更改为服务器端分页

        RLock infoLock = redissonClient.getLock("info-lock");
        infoLock.lock();
        // 加锁成功,执行业务
        try {
            List<AllInfoTo> allInfoTos;
            String statusInfo = stringRedisTemplate.opsForValue().get(status + "/" + infoFlag + "/info");
            if (StringUtils.isEmpty(statusInfo)) {
                // redis中未查到statusInfo，则查询数据库
                // 获取所有的消息
                List<InfoEntity> infoEntities = baseMapper.selectList(new QueryWrapper<InfoEntity>());

                // 基础属性查询后拷贝到完整展示属性中
                allInfoTos = infoEntities.stream().map(infoEntity -> {
                    AllInfoTo allInfoTo = new AllInfoTo();
                    BeanUtils.copyProperties(infoEntity, allInfoTo);
                    return allInfoTo;
                }).collect(Collectors.toList());

                // 当传入的需求不为正常状态则返回所有异常状态的消息
                if (status != IdentifyConstant.IdentifyStatusEnum.IMAGE_IDENTIFY_NORMAL.getCode()) {
                    allInfoTos = allInfoTos.stream().filter(infoEntity -> {
                        return infoEntity.getInfoStatus() != IdentifyConstant.IdentifyStatusEnum.IMAGE_IDENTIFY_NORMAL.getCode();
                    }).collect(Collectors.toList());
                }

                // 当传入的需求不为全部展示时则筛选消息
                if (infoFlag != FlagConstant.InfoEnum.INFO_ALL.getCode()) {
                    allInfoTos = allInfoTos.stream().filter(infoEntity -> {
                        return infoEntity.getInfoFlag().equals(infoFlag);
                    }).collect(Collectors.toList());
                }

                // 调用图片service获取所有消息里面的图片详情
                allInfoTos = allInfoTos.stream().peek(allInfoTo -> allInfoTo.setImageEntity(
                        imageService.selectImageEntityByImageId(allInfoTo.getInfoImage()))).collect(Collectors.toList());

                // 调用坐标service获取所有消息里面的坐标详情
                allInfoTos = allInfoTos.stream().peek(allInfoTo -> allInfoTo.setCoordinateEntity(
                        coordinateService.selectCoordinateById(allInfoTo.getInfoCoordinate()))).collect(Collectors.toList());

                // 调用区县service获取所有消息里面的县区详情
                allInfoTos = allInfoTos.stream().peek(allInfoTo -> allInfoTo.setAreaEntity(
                        areaService.getAreaByAreaId(allInfoTo.getInfoArea()))).collect(Collectors.toList());

                // 调用城市service获取所有消息里面的城市详情
                allInfoTos = allInfoTos.stream().peek(allInfoTo -> allInfoTo.setCityEntity(
                        cityService.getCityEntityByCityCode(allInfoTo.getAreaEntity().getCityCode()))).collect(Collectors.toList());

                // 调用省份service获取所有消息里面的省份详情
                allInfoTos = allInfoTos.stream().peek(allInfoTo -> allInfoTo.setProvinceEntity(provinceService.
                        getProvinceEntityByProvinceCode(allInfoTo.getCityEntity().getProvinceCode()))).collect(Collectors.toList());

                //TODO 调用远程用户service获取所有消息里面的用户详情信息(从session中获取用户id)
//                allInfoTos = allInfoTos.stream().peek(allInfoTo -> allInfoTo.setMemberEntity(
//                        JSONObject.parseObject(JSON.toJSONString(memberFeignService.
//                                getMemberById(allInfoTo.getMemberEntity().getId()).get("data")), MemberEntity.class)))
//                        .collect(Collectors.toList());

                stringRedisTemplate.opsForValue().set(status + "/" + infoFlag + "/info", JSON.toJSONString(allInfoTos),
                        1, TimeUnit.HOURS);
                return allInfoTos;
            }
            return JSON.parseArray(statusInfo, AllInfoTo.class);
        } finally {
            infoLock.unlock();
        }
    }

    /**
     * 物理删除(数据库表记录删除)
     *
     * @param infoId 消息id
     */
    @Override
    public void deleteInfoByInfoId(Long infoId) {
        RLock infoLock = redissonClient.getLock("info-lock");
        infoLock.lock();
        try {
            InfoEntity infoEntity = baseMapper.selectById(infoId);
            imageService.removeById(infoEntity.getInfoImage());
            baseMapper.deleteById(infoId);
            deleteRedisInfo();
        } finally {
            infoLock.unlock();
        }
    }

    /**
     * 更新消息类
     *
     * @param infoEntity 消息类
     */
    @Override
    @Transactional
    public void updateInfo(InfoEntity infoEntity) {
        RLock infoLock = redissonClient.getLock("info-lock");
        infoLock.lock();
        try {
            // 人工修改标识
            infoEntity.setInfoIdentify(IdentifyConstant.IdentifyEnum.IMAGE_IDENTIFY_HUMAN.getCode());
            // 更新时间
            infoEntity.setInfoUpdateTime(new Date());
            baseMapper.update(infoEntity, new QueryWrapper<InfoEntity>().eq("info_id", infoEntity.getInfoId()));
            deleteRedisInfo();
        } finally {
            infoLock.unlock();
        }

    }

    /**
     * 逻辑删除（不展示操作）,图片依旧可供后台程序分析
     *
     * @param infoId 消息id
     */
    @Override
    @Transactional
    public void deleteLogicInfoByInfoId(Long infoId) {
        RLock infoLock = redissonClient.getLock("info-lock");
        infoLock.lock();
        try {
            InfoEntity infoEntity = new InfoEntity();
            infoEntity.setInfoId(infoId);
            infoEntity.setInfoFlag(FlagConstant.InfoEnum.INFO_DOWN.getCode());
            baseMapper.updateById(infoEntity);
            deleteRedisInfo();
        } finally {
            infoLock.unlock();
        }
    }

    /**
     * 删除redis中所有状态的info缓存
     */
    public void deleteRedisInfo() {
        List<String> keys = new ArrayList<>();
        keys.add("0/0/info");
        keys.add("0/1/info");
        keys.add("1/0/info");
        keys.add("1/1/info");
        keys.add("2/0/info");
        keys.add("2/1/info");
        keys.add("3/0/info");
        keys.add("3/1/info");
        keys.add("4/0/info");
        keys.add("4/1/info");
        stringRedisTemplate.delete(keys);
    }
}