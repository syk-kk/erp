package com.sky.erp.sys.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.erp.sys.common.Constant;
import com.sky.erp.sys.common.DataGridView;
import com.sky.erp.sys.common.ResultObj;
import com.sky.erp.sys.common.TreeNode;
import com.sky.erp.sys.entity.Permission;
import com.sky.erp.sys.entity.Role;
import com.sky.erp.sys.service.IPermissionService;
import com.sky.erp.sys.service.IRoleService;
import com.sky.erp.sys.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author syk
 * @since 2020-07-11
 */
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    @RequestMapping("loadAllRole")
    public DataGridView loadAllRole(RoleVo roleVo){

        Page<Role> page = new Page<>(roleVo.getPage(),roleVo.getLimit());
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(roleVo.getName()),"name",roleVo.getName());
        queryWrapper.like(StrUtil.isNotBlank(roleVo.getRemark()),"remark",roleVo.getRemark());
        queryWrapper.eq(roleVo.getAvailable()!=null,"available",roleVo.getAvailable());
        queryWrapper.orderByDesc("createtime");
        roleService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());

    }

    @RequestMapping("addRole")
    public ResultObj addRole(Role Role){
        try {
            Role.setCreatetime(new Date());
            roleService.save(Role);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            return ResultObj.ADD_ERROR;
        }
    }

    @RequestMapping("updateRole")
    public ResultObj updateRole(Role Role){
        try {
            roleService.updateById(Role);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            return ResultObj.UPDATE_ERROR;
        }
    }

    @RequestMapping("deleteRole")
    public ResultObj deleteRole(RoleVo roleVo){
        try {
            roleService.removeById(roleVo.getId());
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }

    }

    @RequestMapping("initPermissionByRoleId")
    public DataGridView initPermissionByRoleId(Integer rid){
        List<TreeNode> data = new ArrayList<>();

        //查询所有可用的菜单和权限
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constant.AVAILABLE_TRUE);
        List<Permission> permissions = permissionService.list(queryWrapper);

        //根据rid查询sys_role_permission表中的pid
        List<Integer> pids = roleService.queryRolePermissionIdsByRid(rid);
        List<Permission> current_permissions = null;
        if (pids.size()>0){
            queryWrapper.in(pids.size()>0,"id",pids);
            current_permissions = permissionService.list(queryWrapper);
        } else {
            current_permissions = new ArrayList<>();
        }



        for (Permission p1 : permissions) {
            String checkArr = "0";
            for (Permission p2 : current_permissions) {
                if (p1.getId()==p2.getId()){
                    checkArr = "1";
                    break;
                }
            }
            data.add(new TreeNode(p1.getId(),p1.getPid(),p1.getTitle(),p1.getOpen()==1?true:false,checkArr));
        }


        return new DataGridView(data);
    }

    @RequestMapping("savaRolePermission")
    public ResultObj savaRolePermission(RoleVo roleVo){
        try {
            roleService.savaRolePermission(roleVo);
            return ResultObj.DISPATCH_SUCCESS;
        } catch (Exception e){
            return ResultObj.DISPATCH_ERROR;
        }
    }

}
