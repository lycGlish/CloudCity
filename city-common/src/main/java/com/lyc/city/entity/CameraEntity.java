package com.lyc.city.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author lyc
 * @email 708901735@qq.com
 * @date 2020-09-07 10:58:39
 */
@Data
@TableName("cms_camera")
public class CameraEntity implements Serializable {
	private static final long serialVersionUID = 1L;

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
	 * 摄像头地区id
	 */
	private Integer cameraArea;

}
