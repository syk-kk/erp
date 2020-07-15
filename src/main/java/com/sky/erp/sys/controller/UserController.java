package com.sky.erp.sys.controller;


import cn.hutool.core.comparator.PinyinComparator;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.erp.sys.common.Constant;
import com.sky.erp.sys.common.DataGridView;
import com.sky.erp.sys.common.ResultObj;
import com.sky.erp.sys.entity.Dept;
import com.sky.erp.sys.entity.Permission;
import com.sky.erp.sys.entity.Role;
import com.sky.erp.sys.entity.User;
import com.sky.erp.sys.service.IDeptService;
import com.sky.erp.sys.service.IRoleService;
import com.sky.erp.sys.service.IUserService;
import com.sky.erp.sys.vo.UserVo;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private IRoleService roleService;
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
                User leader = userService.getById(mgr);
                user.setLeadername(leader.getName());
                user.setLeaderdeptid(leader.getDeptid());
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
     * 加载用户已拥有的角色
     */
    @RequestMapping("loadHasRole")
    public DataGridView loadHasRole(Integer id){
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available",Constant.AVAILABLE_TRUE);
        List<Role> allRole = roleService.list(queryWrapper);
        List<Integer> hasRids = userService.getHasRoleIdsByUid(id);
        for (Role role : allRole) {
            Boolean flag = false;
            for (Integer rid : hasRids) {
                if (role.getId()==rid){
                    flag = true;
                    break;
                }
            }
            role.setLAY_CHECKED(flag);
        }
        return new DataGridView(allRole);

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

    /**
     * 获取排序码
     */
    @RequestMapping("loadUserMaxOrderNum")
    public Map<String,Object> loadUserMaxOrderNum(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("max(ordernum) as ordernum");
        Map<String,Object> map = new HashMap<>();
        map.put("ordernum",userService.getOne(queryWrapper).getOrdernum()+1);
        return map;
    }

    /**
     * 根据deptid加载用户
     */
    @RequestMapping("loadUsersByDeptId")
    public DataGridView loadUsersByDeptId(Integer deptid){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deptid",deptid);
        queryWrapper.eq("type",Constant.USER_TYPE_NORMAL);
        queryWrapper.eq("available",Constant.AVAILABLE_TRUE);
        List<User> users = userService.list(queryWrapper);
        return new DataGridView(users);
    }

    /**
     * 添加用户
     */
    @RequestMapping("addUser")
    public ResultObj addUser(User user){
        try {
            if (user.getAvailable()==null){
                user.setAvailable(0);
            }
            user.setType(Constant.USER_TYPE_NORMAL);
            String salt = IdUtil.simpleUUID().toUpperCase();
            user.setSalt(salt);
            user.setPwd(new Md5Hash(Constant.USER_DEFAULT_PWD,salt,2).toString());
            userService.save(user);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e){
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 中文转拼音
     */
    @RequestMapping("ChineseToPinyin")
    public Map<String,Object> ChineseToPinyin(String username){

        Map<String,Object> map = new HashMap<>();
        try {

//          设置转换后拼音显示的格式
            HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
//          拼音不带音标
            outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//          将中文字符串转成拼音
            String s = PinyinHelper.toHanYuPinyinString(username, outputFormat, "", true);
            map.put("value",s);
        } catch (BadHanyuPinyinOutputFormatCombination e){
            map.put("value","");
        }

        return map;
    }

    /**
     * 重置用户密码
     */
    @RequestMapping("resetUserPwd")
    public ResultObj resetUserPwd(Integer id){
        try {
            User user = new User();
            String salt = IdUtil.simpleUUID().toUpperCase();
            user.setId(id);
            user.setSalt(salt);
            user.setPwd(new Md5Hash(Constant.USER_DEFAULT_PWD,salt,2).toString());
            userService.updateById(user);
            return ResultObj.RESET_SUCCESS;
        } catch (Exception e){
            return ResultObj.RESET_ERROR;
        }
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @RequestMapping("updateUser")
    public ResultObj updateUser(User user){
        try {
            userService.updateById(user);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e){
            return ResultObj.UPDATE_ERROR;
        }
    }






}
