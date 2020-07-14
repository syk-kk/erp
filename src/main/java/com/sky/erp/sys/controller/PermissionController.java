package com.sky.erp.sys.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.erp.sys.common.Constant;
import com.sky.erp.sys.common.DataGridView;
import com.sky.erp.sys.common.ResultObj;
import com.sky.erp.sys.common.TreeNode;
import com.sky.erp.sys.entity.Permission;
import com.sky.erp.sys.service.IPermissionService;
import com.sky.erp.sys.vo.PermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author syk
 * @since 2020-07-08
 */
@RestController
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @RequestMapping("loadPermissionManagerLeftTreeJson")
    public DataGridView loadPermissionManagerLeftTreeJson(){
        List<TreeNode> data = new ArrayList<>();
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",Constant.TYPE_MENU);
        List<Permission> list = permissionService.list(queryWrapper);
        for (Permission p : list) {
            data.add(new TreeNode(p.getId(),p.getPid(),p.getTitle(),p.getOpen()==1?true:false));
        }
        return new DataGridView(data);

    }

    @RequestMapping("loadAllPermission")
    public DataGridView loadAllPermission(PermissionVo permissionVo){

        Page<Permission> page = new Page<>(permissionVo.getPage(),permissionVo.getLimit());
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(permissionVo.getId()!=null,"pid",permissionVo.getId());
        queryWrapper.like(StrUtil.isNotBlank(permissionVo.getPercode()),"percode",permissionVo.getPercode());
        queryWrapper.like(StrUtil.isNotBlank(permissionVo.getTitle()),"title",permissionVo.getTitle());
        queryWrapper.eq("type",Constant.TYPE_PERMISSION);
//        queryWrapper.eq("available",Constant.AVAILABLE_TRUE);
        permissionService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());

    }


    @RequestMapping("deletePermission")
    public ResultObj delPermission(Integer id){
        try {
            permissionService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }

    @RequestMapping("deleteBatchPermission")
    public ResultObj deleteBatchPermission(PermissionVo permissionVo){
        try {
            permissionService.removeByIds(Arrays.asList(permissionVo.getIds()));
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }

    @RequestMapping("updatePermission")
    public ResultObj updatePermission(Permission permission){
        try {
            if (permission.getOpen()==null ){
                permission.setOpen(0);
            }
            if ( permission.getAvailable()==null){
                permission.setAvailable(0);
            }
            permissionService.updateById(permission);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e){
            return ResultObj.UPDATE_ERROR;
        }
    }

    @RequestMapping("addPermission")
    public ResultObj addPermission(Permission permission){
        try {
            QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("pid",permission.getPid());
            List<Permission> list = permissionService.list(queryWrapper);
            for (Permission p : list) {
                if (p.getTitle().equals(permission.getTitle())){
                    return new ResultObj(0,"该菜单已存在");
                }
            }
            if (permission.getOpen()==null ){
                permission.setOpen(0);
            }
            if ( permission.getAvailable()==null){
                permission.setAvailable(0);
            }
            permission.setType(Constant.TYPE_PERMISSION);
            permissionService.save(permission);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e){
            return ResultObj.ADD_ERROR;
        }
    }

    @RequestMapping("loadPermissionMaxOrderNum")
    public Map<String,Object> loadPermissionMaxOrderNum(){
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("max(id) as id");
        queryWrapper.eq("type",Constant.TYPE_PERMISSION);
        Map<String,Object> map = new HashMap<>();
        map.put("ordernum",permissionService.getOne(queryWrapper).getId()+1);
        return map;
    }

}
