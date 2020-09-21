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
@TableName("ims_province")
public class ProvinceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 省份id
	 */
	@TableId
	private Integer provinceId;
	/**
	 * 省份代码
	 */
	private String provinceCode;
	/**
	 * 省份名
	 */
	private String provinceName;

}
