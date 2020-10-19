package com.lyc.city.info.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author lyc
 * @date 2020/8/21 17:41
 */
@Data
public class InfoVo {

    private Long infoId;
    /**
     * 消息名称
     */
    private String infoName;
    /**
     * 消息描述
     */
    private String infoDescription;
    /**
     * 消息附属图片
     */
    private Long infoImage;
    /**
     * 上传人
     */
    private Long infoUploader;
    /**
     * 上传来源（1用户2摄像头）
     */
    private Integer infoSource;
    /**
     * 消息地点状态（1无积水2积水3内涝4冰雪）
     */
    private Integer infoStatus;
    /**
     * 消息地区id
     */
    private Integer infoArea;
    /**
     * 消息坐标id
     */
    private Long infoCoordinate;
    /**
     * 1自动识别2人工修改
     */
    private Integer infoIdentify;
    /**
     * 消息创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date infoCreateTime;
    /**
     * 消息更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date infoUpdateTime;

    @TableField(exist = false)
    private String imgUrl;

    @TableField(exist = false)
    private String longitude;

    @TableField(exist = false)
    private String latitude;

    @TableField(exist = false)
    private String areaCode;
}
