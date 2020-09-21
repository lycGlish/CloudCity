package com.lyc.city.thirdparty.controller;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.lyc.city.thirdparty.utils.FileCoordinateUtil;
import com.lyc.city.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;


/**
 * @author lyc
 * @date 2020/8/3 17:44
 */
@RestController
@CrossOrigin
@Slf4j
public class OssController {

    @Value("${spring.cloud.alicloud.oss.endpoint}")
    private String endpoint;

    @Value("${spring.cloud.alicloud.access-key}")
    private String accessId;

    @Value("${spring.cloud.alicloud.secret-key}")
    private String accessSecret;

    /**
     * 上传图片进入oss
     * @param multipartFile 图片
     * @return R
     */
    @PostMapping("/oss/policy")
    public R policy(@RequestParam("multipartFile") MultipartFile multipartFile) {
        OSS ossClient = null;
        try {
            //创建oss实例
            ossClient = new OSSClientBuilder().build(endpoint, accessId, accessSecret);

            InputStream inputStream = multipartFile.getInputStream();

            //获取文件名称(使用uuid避免重名文件覆盖问题)
            String originalFilename = multipartFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String fileName = uuid + originalFilename;
            //把文件按照日期进行分类(日期工具类使用的是joda-time，也可以使用java自带的工具类)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dataPath = sdf.format(new Date());
            //日期构成的文件夹路径和文件名称合并
            fileName = dataPath + "/" + fileName;

            //调用oss方法实现上传
            //第一个参数 Bucket名称
            //第二个参数 上传到oss文件路径和文件名称 a/b/c/123.jpg
            //第三个参数 上传文件输入流
            String bucketName = "market-lyc";
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream);
            ossClient.putObject(putObjectRequest);

            //设置返回的url有效期为100年
            Date expiration = new Date(new Date().getTime() + 1000L * 3600L * 24L * 365L * 100L);
            GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, fileName, HttpMethod.GET);
            req.setExpiration(expiration);

            //回显url
            URL url = ossClient.generatePresignedUrl(req);

            // 获取图片自带经纬度
            String longitude = null, latitude = null;
            Map<String, String> fileCoordinate = FileCoordinateUtil.fileCoordinateCheck(multipartFile);
            if (fileCoordinate != null) {
                longitude = fileCoordinate.get("longitude");
                latitude = fileCoordinate.get("latitude");
            } else {
                log.info("图片不带经纬度");
            }

            Map<String, String> data = new LinkedHashMap<String, String>();

            data.put("url", url.toString());
            data.put("longitude", longitude);
            data.put("latitude", latitude);
            return R.ok().put("data", data);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            ossClient.shutdown();
        }
        return R.error();
    }
}
