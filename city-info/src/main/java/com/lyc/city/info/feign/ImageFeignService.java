package com.lyc.city.info.feign;

import com.lyc.city.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lyc
 * @date 2020/8/27 10:41
 */
@FeignClient("city-third-party")
public interface ImageFeignService {

    @PostMapping(value = "/oss/policy", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R policy(@RequestPart("multipartFile") MultipartFile multipartFile);
}
