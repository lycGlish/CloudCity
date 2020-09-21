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
 * @date 2020-08-20 15:15:31
 */
@Data
@TableName("ims_coordinate")
public class CoordinateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 坐标id
	 */
	@TableId
	private Long coordinateId;
	/**
	 * 经度
	 */
	private String longitude;
	/**
	 * 纬度
	 */
	private String latitude;

}
