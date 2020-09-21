package com.lyc.city.to;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lyc.city.entity.AreaEntity;
import com.lyc.city.entity.CityEntity;
import com.lyc.city.entity.CoordinateEntity;
import com.lyc.city.entity.ProvinceEntity;
import lombok.Data;

/**
 * @author lyc
 * @date 2020/9/7 11:43
 */
@Data
public class AllCameraTo {

    /**
     * 摄像头id
     */
    @TableId
    private Long cameraId;
    /**
     * 摄像头名称
     */
    private String cameraName;
    /**
     * 摄像头描述
     */
    private String cameraDescription;
    /**
     * 摄像头坐标id
     */
    private Long cameraCoordinate;
    /**
     * 摄像头地区码
     */
    private Integer cameraArea;

    @TableField(exist = false)
    private AreaEntity areaEntity;

    @TableField(exist = false)
    private CityEntity cityEntity;

    @TableField(exist = false)
    private CoordinateEntity coordinateEntity;

    @TableField(exist = false)
    private ProvinceEntity provinceEntity;

    @TableField(exist = false)
    private String longitude;

    @TableField(exist = false)
    private String latitude;

    @TableField(exist = false)
    private String areaCode;

}
