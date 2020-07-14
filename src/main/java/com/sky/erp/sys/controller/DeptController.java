package com.sky.erp.sys.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.erp.sys.common.Constant;
import com.sky.erp.sys.common.DataGridView;
import com.sky.erp.sys.common.ResultObj;
import com.sky.erp.sys.common.TreeNode;
import com.sky.erp.sys.entity.Dept;
import com.sky.erp.sys.service.IDeptService;
import com.sky.erp.sys.vo.DeptVo;
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
 * @since 2020-07-09
 */
@RestController
@RequestMapping("dept")
public class DeptController {

    @Autowired
    private IDeptService deptService;

    @RequestMapping("loadDeptManagerLeftTreeJson")
    public DataGridView loadDeptManagerLeftTreeJson(){
//        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("available", Constant.AVAILABLE_TRUE);
        List<Dept> list = deptService.list();
        List<TreeNode> data = new ArrayList<>();
        for (Dept d : list) {
            data.add(new TreeNode(d.getId(),d.getPid(),d.getTitle(),d.getOpen()==1?true:false));
        }
        return new DataGridView(data);

    }

    @RequestMapping("loadAllDept")
    public DataGridView loadAlldept(DeptVo deptVo){
        Page<Dept> page = new Page<>(deptVo.getPage(),deptVo.getLimit());
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(deptVo.getId()!=null,"id",deptVo.getId());
        queryWrapper.or().eq(deptVo.getId()!=null,"pid",deptVo.getId());
        queryWrapper.like(StrUtil.isNotBlank(deptVo.getTitle()),"title",deptVo.getTitle());
        queryWrapper.like(StrUtil.isNotBlank(deptVo.getAddress()),"address",deptVo.getAddress());
        queryWrapper.like(StrUtil.isNotBlank(deptVo.getRemark()),"remark",deptVo.getRemark());
        queryWrapper.orderByAsc("ordernum");

        deptService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());

    }

    @RequestMapping("checkDeptHasChildren")
    public ResultObj checkDeptHasChildren(Integer id){
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",id);
        List<Dept> list = deptService.list(queryWrapper);
        if (list.size()>0){
            return new ResultObj(200,null);
        } else {
            return new ResultObj(-1,null);
        }

    }

    @RequestMapping("delDept")
    public ResultObj delDept(Integer id){
        try {
            deptService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }

    }

    @RequestMapping("deleteBatchDept")
    public ResultObj deleteBatchDept(DeptVo deptVo){
        try {
            deptService.removeByIds(Arrays.asList(deptVo.getIds()));
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }

    @RequestMapping("updateDept")
    public ResultObj updateDept(Dept dept){
        try {
            if (dept.getOpen()==null ){
                dept.setOpen(0);
            }
            if ( dept.getAvailable()==null){
                dept.setAvailable(0);
            }
            deptService.updateById(dept);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e){
            return ResultObj.UPDATE_ERROR;
        }
    }

    @RequestMapping("addDept")
    public ResultObj addDept(Dept dept){
        try {
            QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("pid",dept.getPid());
            List<Dept> list = deptService.list(queryWrapper);
            for (Dept d : list) {
                if (d.getTitle().equals(dept.getTitle())){
                    return new ResultObj(0,"该部门已存在");
                }
            }
            if (dept.getOpen()==null ){
                dept.setOpen(0);
            }
            if ( dept.getAvailable()==null){
                dept.setAvailable(0);
            }
//            dept.setOrdernum()
            dept.setCreatetime(new Date());
            deptService.save(dept);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e){
            return ResultObj.ADD_ERROR;
        }
    }

    @RequestMapping("loadDeptMaxOrderNum")
    public Map<String,Object> loadDeptMaxOrderNum(){
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("max(id) as id");
        Map<String,Object> map = new HashMap<>();
        map.put("ordernum",deptService.getOne(queryWrapper).getId()+1);
        return map;
    }


}
