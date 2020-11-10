package com.lyc.city.thirdparty.fastdfs.service.impl;

import com.lyc.city.thirdparty.controller.UploadController;
import com.lyc.city.thirdparty.fastdfs.FastDFSClient;
import com.lyc.city.thirdparty.fastdfs.FastDFSFile;
import com.lyc.city.thirdparty.fastdfs.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class UploadServiceImpl implements UploadService {
    private static Logger logger = LoggerFactory.getLogger(UploadController.class);

    /**
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @Override
    public String saveFile(MultipartFile multipartFile) throws IOException {
        String[] fileAbsolutePath = {};
        String fileName = multipartFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        byte[] file_buff = null;
        InputStream inputStream = multipartFile.getInputStream();
        if (inputStream != null) {
            int len1 = inputStream.available();
            file_buff = new byte[len1];
            inputStream.read(file_buff);
        }
        inputStream.close();
        FastDFSFile file = new FastDFSFile(fileName, file_buff, ext);
        try {
            //upload to fastdfs
            fileAbsolutePath = FastDFSClient.upload(file);
        } catch (Exception e) {
            logger.error("upload file Exception!", e);
        }
        if (fileAbsolutePath == null) {
            logger.error("upload file failed,please upload again!");
        }
        return FastDFSClient.getTrackerUrl() + fileAbsolutePath[0] + "/" + fileAbsolutePath[1];
    }
}
