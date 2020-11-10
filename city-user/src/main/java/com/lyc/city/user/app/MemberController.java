package com.lyc.city.user.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.lyc.city.entity.MemberEntity;
import com.lyc.city.user.service.MemberService;
import com.lyc.city.utils.PageUtils;
import com.lyc.city.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author lyc
 * @email 708901735@qq.com
 * @date 2020-09-14 15:15:19
 */
@RestController
@RequestMapping("user/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/getMemberByPhone")
    public R getMemberByPhone(@RequestParam("phone") String phone){
        MemberEntity memberEntity = memberService.getMemberByPhone(phone);
        return R.ok().put("data",memberEntity);
    }

    @GetMapping("/count")
    public R count() {
        int count = memberService.count();
        return R.ok().put("data", count);
    }

    @GetMapping("/getMemberNameById")
    public R getMemberNameById(@RequestParam("memberId") Long memberId) {
        MemberEntity memberEntity = memberService.getMemberNameById(memberId);
        return R.ok().put("data", memberEntity);
    }

    @GetMapping("/getMemberById")
    public R getMemberById(@RequestParam("memberId") Long memberId) {
        MemberEntity memberEntity = memberService.getMemberById(memberId);
        return R.ok().put("data", memberEntity);
    }

    @PostMapping("/doRegister")
    public R doRegister(@RequestBody MemberEntity memberEntity) {
        memberService.doRegister(memberEntity);
        return R.ok();
    }

    @PostMapping("/doLogin")
    public R doLogin(@RequestBody MemberEntity memberEntity, HttpServletRequest request) {
        MemberEntity memberInfo = memberService.doLogin(memberEntity);
        if (memberInfo != null) {
            HttpSession session = request.getSession();
            session.setAttribute("member", memberInfo);
        }
        return R.ok().put("data", memberInfo);
    }

    @GetMapping("/deleteLogicMember/{memberId}")
    public R deleteLogicMember(@PathVariable("memberId") Long memberId) {
        memberService.deleteLogicMember(memberId);
        return R.ok();
    }

    @PostMapping("/updateMember")
    public R updateMember(@RequestBody MemberEntity memberEntity) {
        memberService.updateById(memberEntity);
        return R.ok();
    }

    @GetMapping("/getAllMembers")
    public R getAllMembers() {
        List<MemberEntity> memberEntities = memberService.getAllMembers();
        return R.ok().put("data", memberEntities);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("camera:member:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("camera:member:info")
    public R info(@PathVariable("id") Long id) {
        MemberEntity member = memberService.getById(id);
        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("camera:member:save")
    public R save(@RequestBody MemberEntity member) {
        memberService.save(member);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("camera:member:update")
    public R update(@RequestBody MemberEntity member) {
        memberService.updateById(member);
        return R.ok();
    }

}
