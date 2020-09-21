package com.lyc.city.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * 
 * @author lyc
 * @email 708901735@qq.com
 * @date 2020-08-20 15:15:31
 */
@Data
@TableName("ims_info")
public class InfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 消息id
	 */
	@TableId
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
	 * 消息地点状态（0识别错误1无积水2积水3内涝4冰雪）
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
	 * 是否展示(0不展示1展示)
	 */
	private Integer infoFlag;
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

}
