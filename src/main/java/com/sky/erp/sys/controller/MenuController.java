package com.sky.erp.sys.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.erp.sys.common.*;
import com.sky.erp.sys.entity.Dept;
import com.sky.erp.sys.entity.Permission;
import com.sky.erp.sys.entity.User;
import com.sky.erp.sys.service.IPermissionService;
import com.sky.erp.sys.vo.DeptVo;
import com.sky.erp.sys.vo.PermissionVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private IPermissionService permissionService;

    @RequestMapping("loadIndexLeftMenuJson")
    public DataGridView loadIndexLeftMenuJson(HttpSession session){
        //构造查询条件
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constant.TYPE_MENU);
        queryWrapper.eq("available",Constant.AVAILABLE_TRUE);

        //用户对应的菜单集合
        List<Permission> menu = null;
        //节点集合
        List<TreeNode> list = new ArrayList<>();

        //获得登录用户，取出用户中的菜单权限
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        menu = activeUser.getPermissions();

        for (Permission permission : menu) {
            TreeNode node = new TreeNode(permission.getId(),permission.getPid(),
                    permission.getTitle(),permission.getIcon(),permission.getHref(),
                    permission.getOpen()==1?true:false);
            list.add(node);
        }
        List<TreeNode> nodes = TreeNodeBuilder.builder(list, 1);

        return new DataGridView(nodes);

    }

    @RequestMapping("loadMenuManagerLeftTreeJson")
    public DataGridView loadMenuManagerLeftTreeJson(){
        List<TreeNode> data = new ArrayList<>();
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",Constant.TYPE_MENU);
        List<Permission> list = permissionService.list(queryWrapper);
        for (Permission p : list) {
            data.add(new TreeNode(p.getId(),p.getPid(),p.getTitle(),p.getOpen()==1?true:false));
        }
        return new DataGridView(data);

    }

    @RequestMapping("loadAllMenu")
    public DataGridView loadAllMenu(PermissionVo permissionVo){

        Page<Permission> page = new Page<>(permissionVo.getPage(),permissionVo.getLimit());
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(permissionVo.getId()!=null,"id",permissionVo.getId());
        queryWrapper.or().eq(permissionVo.getId()!=null,"pid",permissionVo.getId());
        queryWrapper.like(StrUtil.isNotBlank(permissionVo.getTitle()),"title",permissionVo.getTitle());
        queryWrapper.eq("type",Constant.TYPE_MENU);
//        queryWrapper.eq("available",Constant.AVAILABLE_TRUE);
        permissionService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());

    }

    @RequestMapping("checkMenuHasChildren")
    public ResultObj checkMenuHasChildren(Integer id){
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",id);
        queryWrapper.eq("type",Constant.TYPE_MENU);
        List<Permission> list = permissionService.list(queryWrapper);
        if (list.size()>0){
            return new ResultObj(200,null);
        }else {
            return new ResultObj(-1,null);
        }
    }

    @RequestMapping("delMenu")
    public ResultObj delMenu(Integer id){
        try {
            permissionService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }

    @RequestMapping("deleteBatchMenu")
    public ResultObj deleteBatchMenu(PermissionVo permissionVo){
        try {
            permissionService.removeByIds(Arrays.asList(permissionVo.getIds()));
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }

    @RequestMapping("updateMenu")
    public ResultObj updateMenu(Permission permission){
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

    @RequestMapping("addMenu")
    public ResultObj addMenu(Permission permission){
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
            permission.setType(Constant.TYPE_MENU);
            permissionService.save(permission);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e){
            return ResultObj.ADD_ERROR;
        }
    }

    @RequestMapping("loadMenuMaxOrderNum")
    public Map<String,Object> loadMenuMaxOrderNum(){
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("max(ordernum) as ordernum");
        queryWrapper.eq("type",Constant.TYPE_MENU);
        Map<String,Object> map = new HashMap<>();
        map.put("ordernum",permissionService.getOne(queryWrapper).getOrdernum()+1);
        return map;
    }
}
