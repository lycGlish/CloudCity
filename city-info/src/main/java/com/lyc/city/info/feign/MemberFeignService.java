package com.lyc.city.info.feign;

import com.lyc.city.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lyc
 * @date 2020/9/21 15:17
 */
@FeignClient("city-user")
public interface MemberFeignService {

    @GetMapping("/user/member/getMemberNameById")
    R getMemberNameById(@RequestParam("memberId") Long memberId);
}
