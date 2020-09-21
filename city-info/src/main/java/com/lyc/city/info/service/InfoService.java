package com.lyc.city.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.city.entity.InfoEntity;
import com.lyc.city.info.vo.InfoVo;
import com.lyc.city.to.AllInfoTo;
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
public interface InfoService extends IService<InfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveInfoByUser(InfoVo infoVo);

    List<AllInfoTo> getStatusInfo(Integer status,Integer infoFlag);

    void deleteInfoByInfoId(Long infoId);

    void updateInfo(InfoEntity infoEntity);

    void deleteLogicInfoByInfoId(Long infoId);
}

