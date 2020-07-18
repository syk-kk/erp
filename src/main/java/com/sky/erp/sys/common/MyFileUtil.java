package com.sky.erp.sys.common;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MyFileUtil {

//    文件上传路径
    public static String uploadPath = "E:/upload/";

    static {
        InputStream inputStream =
                MyFileUtil.class.getClassLoader().getResourceAsStream("file.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            String value = properties.getProperty("uploadPath");
            if (null!=value){
                uploadPath = value;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    根据旧文件名创建新文件名
    public static String createNewFileName(String oldName) {

       String suffix = oldName.substring(oldName.lastIndexOf("."),oldName.length());

        return IdUtil.simpleUUID().toUpperCase()+suffix;
    }


//    文件下载
    public static ResponseEntity<Object> downLoadFile(String path, String downloadname) {
        File file = new File(uploadPath,path);
        if (file.exists()){
            byte[] bytes = FileUtil.readBytes(file);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            if (StrUtil.isNotBlank(downloadname)){
                headers.setContentDispositionFormData("attachment",downloadname);
            }
            return new ResponseEntity<>(bytes,headers, HttpStatus.CREATED);
        }
        return null;

    }
}
