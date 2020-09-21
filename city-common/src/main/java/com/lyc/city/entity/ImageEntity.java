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
@TableName("ims_image")
public class ImageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 图片id
	 */
	@TableId
	private Long imageId;
	/**
	 * 图片名
	 */
	private String imageName;
	/**
	 * 图片url
	 */
	private String imageUrl;

}
