package com.lyc.city.manager.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * 
 * @author lyc
 * @email 708901735@qq.com
 * @date 2020-09-04 10:58:58
 */
@Data
@TableName("mms_task")
public class TaskEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 待做任务id
	 */
	@TableId
	private Integer taskId;
	/**
	 * 待做任务名
	 */
	private String taskName;
	/**
	 * 待做任务描述
	 */
	private String taskDescription;

	/**
	 * 是否展示(0不展示1展示)
	 */
	private int taskFlag;

}
