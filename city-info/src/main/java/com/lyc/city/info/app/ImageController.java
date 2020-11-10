package com.lyc.city.info.app;

import com.lyc.city.info.feign.ImageFeignService;
import com.lyc.city.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lyc.city.info.service.ImageService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 *
 * @author lyc
 * @email 708901735@qq.com
 * @date 2020-08-20 15:15:31
 */
@RestController
@RequestMapping("info/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageFeignService imageFeignService;

    @PostMapping("/fast/upload")
    public R fastUploadImage(@RequestParam("multipartFile") MultipartFile multipartFile){
        return imageFeignService.fastDFS(multipartFile);
    }

    @PostMapping("/upload/image")
    public R uploadImage(@RequestParam("multipartFile") MultipartFile multipartFile){
        return imageFeignService.policy(multipartFile);
    }
}
