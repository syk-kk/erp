package com.sky.erp.sys.controller;

import cn.hutool.core.io.FileUtil;
import com.sky.erp.sys.common.DataGridView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public DataGridView uploadFile(MultipartFile mf){


        return new DataGridView();
    }
}
