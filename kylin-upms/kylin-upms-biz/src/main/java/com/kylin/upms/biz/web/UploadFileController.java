package com.kylin.upms.biz.web;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhebin
 * @Date: 2019/9/24 15:01
 */
@RestController
@CrossOrigin
@RequestMapping("/loding")
public class UploadFileController {

    @Autowired
    MinioClient minioClient;

    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    public Object uploadFile(MultipartFile file) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("res",true);
        minioClient.putObject("kylin",file.getOriginalFilename(),file.getInputStream(),file.getContentType());
        String kylin=minioClient.getObjectUrl("kylin",file.getOriginalFilename());
        map.put("url",kylin);
        map.put("fileName",kylin);
        return map;
    }
    @Bean
    public MinioClient minioClient() throws InvalidPortException, InvalidEndpointException {
        return new MinioClient("http://47.104.250.79:9000","root","z314525617");
    }
}
