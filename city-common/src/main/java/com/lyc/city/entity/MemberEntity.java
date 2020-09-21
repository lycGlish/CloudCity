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
 * @date 2020-09-14 15:15:19
 */
@Data
@TableName("ums_member")
public class MemberEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@TableId
	private Long id;
	/**
	 * 用户名字
	 */
	private String name;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 用户等级(0普通用户1管理员2超级管理员)
	 */
	private Integer memberLevel;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 启用状态(0未启用1启用2封禁)
	 */
	private Integer status;
	/**
	 * 注册时间
	 */
	private Date createTime;

}
