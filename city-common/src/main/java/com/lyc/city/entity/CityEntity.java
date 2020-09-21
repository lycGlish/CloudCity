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
@TableName("ims_city")
public class CityEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 城市id
	 */
	@TableId
	private Integer cityId;
	/**
	 * 城市代码
	 */
	private String cityCode;
	/**
	 * 城市名
	 */
	private String cityName;
	/**
	 * 省份代码
	 */
	private String provinceCode;

}
