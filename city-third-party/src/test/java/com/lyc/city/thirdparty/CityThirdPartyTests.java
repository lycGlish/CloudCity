package com.lyc.city.thirdparty;

import com.lyc.city.thirdparty.component.SmsComponent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;

/**
 * @author lyc
 * @date 2020/8/21 10:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CityThirdPartyTests {

//    @Autowired
//    OSSClient ossClient;

    @Autowired
    SmsComponent smsComponent;

    @Test
    public void testSendCode(){
        smsComponent.sendSmsCode("15807030606","123");
    }

//    @Test
    public void testUpload() throws FileNotFoundException {
        // 上传文件流。
//        InputStream inputStream = new FileInputStream("C:\\Users\\lenovo\\Desktop\\临时文件\\mc.png");
//        ossClient.putObject("market-lyc", "1.png", inputStream);

        // 关闭OSSClient。
//        ossClient.shutdown();
//
//        System.out.println("上传成功.....");
    }
}
