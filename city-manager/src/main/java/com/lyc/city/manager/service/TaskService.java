package com.lyc.city.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.city.manager.entity.TaskEntity;
import com.lyc.city.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author lyc
 * @email 708901735@qq.com
 * @date 2020-09-04 10:58:58
 */
public interface TaskService extends IService<TaskEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<TaskEntity> getAllTasks();

    void deleteLogicTaskById(Integer taskId);
}

