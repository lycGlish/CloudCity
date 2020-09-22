package com.lyc.city.manager.feign;

import com.lyc.city.entity.MemberEntity;
import com.lyc.city.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lyc
 * @date 2020/9/14 20:48
 */
@FeignClient("city-user")
public interface MemberFeignService {

    @GetMapping("/user/member/count")
    R count();

    @GetMapping("/user/member/deleteLogicMember/{memberId}")
    R deleteLogicMember(@PathVariable("memberId") Long memberId);

    @PostMapping("/user/member/updateMember")
    R updateMember(@RequestBody MemberEntity memberEntity);

    @GetMapping("/user/member/getAllMembers")
    R getAllMembers();
}
