package com.sky.erp.sys.controller;

import cn.hutool.core.date.DateUtil;
import com.sky.erp.sys.common.MyFileUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传下载控制器
 */
@RestController
@RequestMapping("file")
public class FileController {

    /**
     * 文件上传
     */
    @RequestMapping("uploadFile")
    public Map<String,Object> uploadFile(MultipartFile mf) {

        //获取上传文件名
        String oldName = mf.getOriginalFilename();

        //获得新文件名
        String newName = MyFileUtil.createNewFileName(oldName);

        //获取当前日期
        String dateStr = DateUtil.format(new Date(),"yyyy-MM-dd");
        //根据当前日期创建保存目录对象
        File dir = new File(MyFileUtil.uploadPath,dateStr);
        if (!dir.exists()){
            dir.mkdirs();
        }
        //创建保存文件对象
        File uploadFile = new File(dir,newName);
        try {
            mf.transferTo(uploadFile);
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败");
        }

        //返回文件保存的相对路径：日期/新文件名
        Map<String,Object> map = new HashMap<>();
        map.put("path",dateStr+"/"+newName);

        return map;

    }

    /**
     * 图片显示
     */
    @RequestMapping("showImg")
    public ResponseEntity<Object> showImg(String path){
        return MyFileUtil.downLoadFile(path,"");
    }
}
