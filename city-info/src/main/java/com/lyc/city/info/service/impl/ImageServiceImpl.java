package com.lyc.city.info.service.impl;

import com.lyc.city.constant.HttpRemoteConstant;
import com.lyc.city.info.vo.InfoVo;
import com.lyc.city.utils.HttpRemoteUtil;
import com.lyc.city.utils.ImageStatusJsonToString;
import com.lyc.city.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.city.utils.Query;

import com.lyc.city.info.dao.ImageDao;
import com.lyc.city.entity.ImageEntity;
import com.lyc.city.info.service.ImageService;


@Service("imageService")
@Slf4j
public class ImageServiceImpl extends ServiceImpl<ImageDao, ImageEntity> implements ImageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ImageEntity> page = this.page(
                new Query<ImageEntity>().getPage(params),
                new QueryWrapper<ImageEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 从info扩展类中获取图片url并保存返回图片id
     *
     * @param infoVo info扩展类
     * @return 图片id
     */
    @Override
    public Long saveBackImageId(InfoVo infoVo) {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setImageUrl(infoVo.getImgUrl());
        baseMapper.saveBackImageId(imageEntity);
        return imageEntity.getImageId();
    }

    /**
     * 调用远程接口识别图片状态
     *
     * @param imgUrl 图片地址
     * @return 图片状态
     */
    @Override
    public Integer identifyImage(String imgUrl) {
        String stringImageResult = HttpRemoteUtil
                .HttpRemotePackage(imgUrl, HttpRemoteConstant.HttpEnum.HTTP_IMAGE_IDENTIFY.getCode());
        if (stringImageResult != null) {
            return Integer.parseInt(ImageStatusJsonToString.getJsonToString(stringImageResult));
        } else {
            return null;
        }
    }

    /**
     * 根据图片id获取图片类
     *
     * @param infoImage 图片id
     * @return 图片类
     */
    @Override
    public ImageEntity selectImageEntityByImageId(Long infoImage) {
        return baseMapper.selectById(infoImage);
    }
}