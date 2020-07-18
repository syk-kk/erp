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

    /**
     * 文件上传路径
     */
    public static String uploadPath = "E:/upload/";

    /**
     * 临时文件标志，上传商品图片时添加该标志，只有提交后才去掉该标志
     */
    public static String tempFlag = "_temp";

    /**
     * 默认商品图片
     */
    public static String defaultGoodsImg = "defaultgoods.jpg";

    static {
        InputStream inputStream =
                MyFileUtil.class.getClassLoader().getResourceAsStream("file.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            String uploadPath_value = properties.getProperty("uploadPath");
            String tempFlag_value = properties.getProperty("tempFlag");
            String defaultGoodsImg_value = properties.getProperty("defaultGoodsImg");
            if (null!=uploadPath_value){
                uploadPath = uploadPath_value;
            }
            if (null!=tempFlag_value){
                tempFlag = tempFlag_value;
            }
            if (null!=defaultGoodsImg_value){
                defaultGoodsImg = defaultGoodsImg_value;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据旧文件名创建新文件名
     * @param oldName
     * @return
     */
    public static String createNewFileName(String oldName) {

       String suffix = oldName.substring(oldName.lastIndexOf("."),oldName.length());

        return IdUtil.simpleUUID().toUpperCase()+suffix+tempFlag;
    }

    /**
     * 文件下载
     * @param path
     * @param downloadname
     * @return
     */
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

    /**
     * 文件改名
      * @param oldname
     */
    public static String rename(String oldname){

        //去掉临时文件标志
        String newname = StrUtil.replace(oldname,tempFlag,"");

        File file = new File(uploadPath,oldname);
        if (file.exists()){
            file.renameTo(new File(uploadPath,newname));
        }
        return newname;

    }

    /**
     * 根据相对路径删除文件
     */
    public static void deleteFile(String path){
        File file = new File(uploadPath,path);
        if (file.exists()){
            file.delete();
        }
    }


}
