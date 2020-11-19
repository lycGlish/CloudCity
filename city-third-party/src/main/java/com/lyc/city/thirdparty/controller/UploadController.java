package com.lyc.city.thirdparty.controller;

import com.lyc.city.thirdparty.fastdfs.service.impl.UploadServiceImpl;
import com.lyc.city.thirdparty.utils.FileCoordinateUtil;
import com.lyc.city.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author lyc
 * @date 2020/10/26 14:52
 */
@RestController
@CrossOrigin
@Slf4j
public class UploadController {

    @Autowired
    private UploadServiceImpl uploadService;

    @PostMapping("/fastDFS/image")
    public R fastDFS(@RequestParam("multipartFile") MultipartFile multipartFile) {
        String url = null;
        try {
            // 图片存入服务器
            url = uploadService.saveFile(multipartFile);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        // 获取图片自带经纬度
        Map<String, String> fileCoordinate = FileCoordinateUtil.fileCoordinateCheck(multipartFile);
        String longitude = fileCoordinate.get("longitude");
        String latitude = fileCoordinate.get("latitude");

        Map<String, String> data = new LinkedHashMap<String, String>();

        data.put("url", url);
        data.put("longitude", longitude);
        data.put("latitude", latitude);
        return R.ok().put("data", data);
    }

}
