package com.lyc.city.manager.app;

import com.lyc.city.manager.feign.ImageFeignService;
import com.lyc.city.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lyc
 * @date 2020/11/18 11:24
 */
@RestController
@RequestMapping("manager/image")
public class ImageFeignController {
    @Autowired
    private ImageFeignService imageFeignService;

    @PostMapping("/fast/upload")
    public R fastUploadImage(@RequestParam("multipartFile") MultipartFile multipartFile){
        return imageFeignService.fastDFS(multipartFile);
    }
}
