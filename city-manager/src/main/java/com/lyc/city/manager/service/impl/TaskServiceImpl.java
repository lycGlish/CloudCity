package com.lyc.city.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lyc.city.constant.FlagConstant;
import com.lyc.city.manager.entity.MenuEntity;
import com.lyc.city.utils.PageUtils;
import com.lyc.city.utils.Query;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lyc.city.manager.dao.TaskDao;
import com.lyc.city.manager.entity.TaskEntity;
import com.lyc.city.manager.service.TaskService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("taskService")
public class TaskServiceImpl extends ServiceImpl<TaskDao, TaskEntity> implements TaskService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TaskEntity> page = this.page(
                new Query<TaskEntity>().getPage(params),
                new QueryWrapper<TaskEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 获取所有任务信息
     *
     * @return 任务列表
     */
    @Override
    public List<TaskEntity> getAllTasks() {
        RLock taskLock = redissonClient.getLock("task-lock");
        taskLock.lock();
        try {
            String task = stringRedisTemplate.opsForValue().get("task");
            if (StringUtils.isEmpty(task)) {
                List<TaskEntity> tasks = baseMapper.selectList(new QueryWrapper<TaskEntity>()
                        .eq("task_flag", FlagConstant.TaskEnum.TASK_TODO.getCode()));
                stringRedisTemplate.opsForValue().set("task", JSON.toJSONString(tasks), 1, TimeUnit.DAYS);
                return tasks;
            }
            return JSON.parseArray(task, TaskEntity.class);
        } finally {
            taskLock.unlock();
        }
    }

    /**
     * 根据任务id逻辑删除任务（完成任务）
     *
     * @param taskId 任务id
     */
    @Override
    @Transactional
    public void deleteLogicTaskById(Integer taskId) {
        RLock taskLock = redissonClient.getLock("task");
        taskLock.lock();
        try {
            // 逻辑删除
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setTaskId(taskId);
            taskEntity.setTaskFlag(FlagConstant.TaskEnum.TASK_DO.getCode());
            baseMapper.updateById(taskEntity);
            stringRedisTemplate.delete("task");
        } finally {
            taskLock.unlock();
        }
    }

}