package com.sky.erp.sys.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.erp.sys.common.Constant;
import com.sky.erp.sys.common.DataGridView;
import com.sky.erp.sys.common.ResultObj;
import com.sky.erp.sys.entity.Dept;
import com.sky.erp.sys.entity.User;
import com.sky.erp.sys.service.IDeptService;
import com.sky.erp.sys.service.IUserService;
import com.sky.erp.sys.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author syk
 * @since 2020-07-07
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IDeptService deptService;
    /*
    查询所有除超级管理员外的用户
     */
    @RequestMapping("loadAllUser")
    public DataGridView loadAllUser(UserVo userVo){
        Page<User> page = new Page<>(userVo.getPage(),userVo.getLimit());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(userVo.getName()),"name",userVo.getName());
        queryWrapper.or().eq(StrUtil.isNotBlank(userVo.getName()),"loginname",userVo.getName());
        queryWrapper.eq(StrUtil.isNotBlank(userVo.getAddress()),"address",userVo.getAddress());
        queryWrapper.eq(userVo.getDeptid()!=null,"deptid",userVo.getDeptid());
        queryWrapper.ne("type", Constant.USER_TYPE_SUPPER);
        userService.page(page,queryWrapper);
        List<User> users = page.getRecords();
        for (User user : users) {

            Integer deptid = user.getDeptid();
            if (deptid!=null){
                user.setDeptname(deptService.getById(deptid).getTitle());
            }
            Integer mgr = user.getMgr();
            if (mgr!=null){
                user.setLeadername(userService.getById(mgr).getName());
            }
        }

        return new DataGridView(page.getTotal(),users);
    }

    /**
     * 分配角色
     * @param uid
     * @param rids
     * @return
     */
    @RequestMapping("selectRole")
    public ResultObj selectRole(Integer uid,Integer[] rids){
        try {
            userService.saveRoleUser(uid,rids);
            return ResultObj.DISPATCH_SUCCESS;
        } catch (Exception e){
            return ResultObj.DISPATCH_ERROR;
        }
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping("deleteUser")
    public ResultObj deleteUser(Integer id){
        try {
            userService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }






}
