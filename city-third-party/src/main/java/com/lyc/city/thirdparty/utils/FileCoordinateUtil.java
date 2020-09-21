package com.lyc.city.thirdparty.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.lyc.city.utils.PointUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author lyc
 * @date 2020/8/26 14:07
 */
@Slf4j
public class FileCoordinateUtil {

    public static Map<String, String> fileCoordinateCheck(MultipartFile file) {
        Map<String, String> coordinate = null;
        File localFile;
        try {
            // 判断上传图片是否自带经纬度坐标与时间
            localFile = File.createTempFile("tmp", null);
            file.transferTo(localFile);
            Metadata metadata = ImageMetadataReader.readMetadata(localFile);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    String tagName = tag.getTagName();  //标签名
                    String desc = tag.getDescription(); //标签信息
                    switch (tagName) {
//                        case "Image Height":
//                            System.out.println("图片高度: " + desc);
//                            break;
//                        case "Image Width":
//                            System.out.println("图片宽度: " + desc);
//                            break;
                        case "Date/Time Original":
                            log.info("照片拍摄时间:" + desc);
                            break;
                        case "GPS Latitude":
                            coordinate.put("longitude", PointUtil.pointToCoordinate(desc));
                            log.info("照片纬度 : " + PointUtil.pointToCoordinate(desc));
//                            System.err.println("纬度(度分秒格式) : " + PointUtil.pointToLatlong(desc));
                            break;
                        case "GPS Longitude":
                            coordinate.put("latitude", PointUtil.pointToCoordinate(desc));
                            log.info("照片经度: " + PointUtil.pointToCoordinate(desc));
//                            System.err.println("经度(度分秒格式): " + PointUtil.pointToLatlong(desc));
                            break;
                    }
                }
            }
            localFile.deleteOnExit();
        } catch (IOException | ImageProcessingException e) {
            e.printStackTrace();
        }
        return coordinate;
    }
}
