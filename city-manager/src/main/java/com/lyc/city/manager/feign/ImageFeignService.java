package com.lyc.city.manager.feign;

import com.lyc.city.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lyc
 * @date 2020/11/18 11:21
 */
@FeignClient("city-third-party")
public interface ImageFeignService {

    @PostMapping(value = "/fastDFS/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R fastDFS(@RequestPart("multipartFile") MultipartFile multipartFile);
}
