package com.sky.erp.sys.controller;


import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.erp.sys.common.DataGridView;
import com.sky.erp.sys.common.ResultObj;
import com.sky.erp.sys.entity.Loginfo;
import com.sky.erp.sys.service.ILoginfoService;
import com.sky.erp.sys.vo.LoginfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  日志管理前端控制器
 * </p>
 *
 * @author syk
 * @since 2020-07-08
 */
@RestController
@RequestMapping("loginfo")
public class LoginfoController {

    @Autowired
    private ILoginfoService loginfoService;

    /**
     * 查询登录日志
     * @param loginfoVo
     * @return
     */
    @RequestMapping("loadAllLoginfo")
    public DataGridView loadAllLogInfo(LoginfoVo loginfoVo){
        Page<Loginfo> page = new Page<>(loginfoVo.getPage(),loginfoVo.getLimit());
        QueryWrapper<Loginfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(loginfoVo.getLoginname()),"loginname",loginfoVo.getLoginname());
        queryWrapper.like(StrUtil.isNotBlank(loginfoVo.getLoginip()),"loginip",loginfoVo.getLoginip());
        queryWrapper.ge(loginfoVo.getStartTime()!=null,"logintime",loginfoVo.getStartTime());
        queryWrapper.le(loginfoVo.getEndTime()!=null,"logintime",loginfoVo.getEndTime());
        queryWrapper.orderByDesc("logintime");
        loginfoService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /**
     * 根据id删除登录日志
     * @param loginfoVo
     * @return
     */
    @RequestMapping("deleteLoginfo")
    public ResultObj deleteLoginfo(LoginfoVo loginfoVo){
        try {
            loginfoService.removeById(loginfoVo.getId());
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 根据id批量删除登录日志
     * @param loginfoVo
     * @return
     */
    @RequestMapping("deleteBatchLoginfo")
    public ResultObj deleteBatchLoginfo(LoginfoVo loginfoVo){
        try {
            loginfoService.removeByIds(Arrays.asList(loginfoVo.getIds()));
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }


}
