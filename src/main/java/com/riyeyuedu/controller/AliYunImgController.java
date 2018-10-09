package com.riyeyuedu.controller;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author haojie tang
 * @date 2018/10/8 21:45
 */
@RestController
@RequestMapping(value = "/aLiYun")
public class AliYunImgController {

    @PostMapping(value = "/putImg")
    public ResponseEntity<?> putImg(@RequestBody MultipartFile file) {
        Date day=new Date();

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

        String img = df.format(day) + "." + suffix;

        try {
            ClientConfiguration config = new ClientConfiguration();
            config.setConnectionTimeout(1000);
            config.setMaxErrorRetry(1);

            // endpoint以杭州为例，其它region请按实际情况填写
            String endpoint = "http://oss-cn-beijing.aliyuncs.com";
            // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建
            String accessKeyId = "LTAICBaKv7FrgzZf";
            String accessKeySecret = "0CWhs6GjKdOqhGWNxZod3jM1npFdE7";
            // 创建OSSClient实例
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流
            InputStream inputStream = file.getInputStream();

            ossClient.putObject("nealcaffrey", "notice/" + img, inputStream);

            ossClient.shutdown();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return ResponseEntity.ok("http://nealcaffrey.oss-cn-beijing.aliyuncs.com/notice/" + img);
    }
}
