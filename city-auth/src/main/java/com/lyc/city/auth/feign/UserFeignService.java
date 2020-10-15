package com.lyc.city.auth.feign;

import com.lyc.city.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lyc
 * @date 2020/10/12 16:22
 */
@FeignClient("city-user")
public interface UserFeignService {

    @GetMapping("/user/member/getMemberByPhone")
    R getMemberByPhone(@RequestParam("phone") String phone);
}
