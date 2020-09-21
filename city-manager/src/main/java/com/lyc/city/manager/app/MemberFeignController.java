package com.lyc.city.manager.app;

import com.lyc.city.entity.MemberEntity;
import com.lyc.city.manager.feign.MemberFeignService;
import com.lyc.city.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lyc
 * @date 2020/9/14 20:49
 */
@RestController
@RequestMapping("member/feign")
@CrossOrigin
@Slf4j
public class MemberFeignController {

    @Autowired
    private MemberFeignService memberFeignService;

    @GetMapping("/deleteLogicMember/{memberId}")
    public R deleteLogicMember(@PathVariable("memberId") Long memberId) {
        R r = memberFeignService.deleteLogicMember(memberId);
        if (!r.getCode().equals(0)) {
            log.error("远程调用封禁用户接口失败！");
        }
        return r;
    }

    @PostMapping("/updateMember")
    public R updateMember(@RequestBody MemberEntity memberEntity) {
        R r = memberFeignService.updateMember(memberEntity);
        if (!r.getCode().equals(0)) {
            log.error("远程调用更新用户接口失败！");
        }
        return r;
    }

    @GetMapping("/getAllMembers")
    public R getAllMembers() {
        R r = memberFeignService.getAllMembers();
        if (!r.getCode().equals(0)) {
            log.error("远程调用查询用户接口失败！");
        }
        return r;
    }
}
