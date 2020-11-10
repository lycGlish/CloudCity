package com.lyc.city.thirdparty.fastdfs.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author lyc
 * @date 2020/10/26 14:55
 */
public interface UploadService {

    String saveFile(MultipartFile multipartFile) throws IOException;
}
