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
@TableName("ims_area")
public class AreaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 地区id
	 */
	@TableId
	private Integer areaId;
	/**
	 * 地区代码
	 */
	private String areaCode;
	/**
	 * 地区名称
	 */
	private String areaName;
	/**
	 * 城市代码
	 */
	private String cityCode;

}
