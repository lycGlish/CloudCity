package com.lyc.city.info.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.lyc.city.entity.MemberEntity;
import com.lyc.city.info.vo.InfoVo;
import com.lyc.city.to.AllInfoTo;
import com.lyc.city.utils.PageUtils;
import com.lyc.city.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lyc.city.entity.InfoEntity;
import com.lyc.city.info.service.InfoService;

import javax.servlet.http.HttpSession;

/**
 * @author lyc
 * @email 708901735@qq.com
 * @date 2020-08-20 15:15:31
 */
@RestController
@RequestMapping("info/info")
@CrossOrigin
public class InfoController {
    @Autowired
    private InfoService infoService;

    @GetMapping("/count")
    public R count() {
        int count = infoService.count();
        return R.ok().put("data", count);
    }

    @GetMapping("/deleteLogicInfoByInfoId/{infoId}")
    public R deleteLogicInfoByInfoId(@PathVariable("infoId") Long infoId) {
        infoService.deleteLogicInfoByInfoId(infoId);
        return R.ok();
    }

    @PostMapping("/updateInfo")
    public R updateInfo(@RequestBody InfoEntity infoEntity) {
        infoService.updateInfo(infoEntity);
        return R.ok();
    }


    @GetMapping("/deleteInfoByInfoId/{infoId}")
    public R deleteInfoByInfoId(@PathVariable("infoId") Long infoId) {
        infoService.deleteInfoByInfoId(infoId);
        return R.ok();
    }

    @GetMapping("/{status}/{flag}/info")
    public R statusInfo(@PathVariable("status") Integer status, @PathVariable("flag") Integer infoFlag) {
        List<AllInfoTo> allInfoVos = infoService.getStatusInfo(status, infoFlag);
        return R.ok().put("data", allInfoVos);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("info:info:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = infoService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{infoId}")
    //@RequiresPermissions("info:info:info")
    public R info(@PathVariable("infoId") Long infoId) {
        InfoEntity info = infoService.getById(infoId);
        return R.ok().put("info", info);
    }

    /**
     * 保存
     */
    @PostMapping("/saveInfoByUser")
    //@RequiresPermissions("info:info:save")
    public R saveInfoByUser(@RequestBody InfoVo infoVo, HttpSession session) {
        MemberEntity member = JSON.parseObject(JSON.toJSONString(session.getAttribute("member")),MemberEntity.class);
        infoVo.setInfoUploader(member.getId());
        infoService.saveInfoByUser(infoVo);
        return R.ok();

    }
}
