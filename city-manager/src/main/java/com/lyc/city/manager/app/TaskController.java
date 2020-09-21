package com.lyc.city.manager.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.lyc.city.constant.FlagConstant;
import com.lyc.city.utils.PageUtils;
import com.lyc.city.utils.R;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import com.lyc.city.manager.entity.TaskEntity;
import com.lyc.city.manager.service.TaskService;

/**
 * @author lyc
 * @email 708901735@qq.com
 * @date 2020-09-04 10:58:58
 */
@RestController
@RequestMapping("manager/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;


    @GetMapping("/deleteLogicTaskById/{taskId}")
    public R deleteLogicTaskById(@PathVariable("taskId") Integer taskId) {
        taskService.deleteLogicTaskById(taskId);
        return R.ok();
    }

    @GetMapping("/getAllTasks")
    public R getAllTasks() {
        List<TaskEntity> allTasks = taskService.getAllTasks();
        return R.ok().put("data", allTasks);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("manager:task:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = taskService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{taskId}")
    //@RequiresPermissions("manager:task:info")
    public R info(@PathVariable("taskId") Integer taskId) {
        TaskEntity task = taskService.getById(taskId);
        return R.ok().put("task", task);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("manager:task:save")
    public R save(@RequestBody TaskEntity task) {
        RLock taskLock = redissonClient.getLock("task");
        taskLock.lock();
        try {
            task.setTaskFlag(FlagConstant.TaskEnum.TASK_TODO.getCode());
            taskService.save(task);
            stringRedisTemplate.delete("task");
        } finally {
            taskLock.unlock();
        }

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("manager:task:update")
    public R update(@RequestBody TaskEntity task) {
        taskService.updateById(task);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("manager:task:delete")
    public R delete(@RequestBody Integer[] taskIds) {
        taskService.removeByIds(Arrays.asList(taskIds));
        return R.ok();
    }

}
