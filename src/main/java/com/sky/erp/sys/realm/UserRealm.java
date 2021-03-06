package com.sky.erp.sys.realm;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sky.erp.sys.common.ActiveUser;
import com.sky.erp.sys.common.Constant;
import com.sky.erp.sys.entity.Permission;
import com.sky.erp.sys.entity.Role;
import com.sky.erp.sys.entity.User;
import com.sky.erp.sys.mapper.UserMapper;
import com.sky.erp.sys.service.IPermissionService;
import com.sky.erp.sys.service.IRoleService;
import com.sky.erp.sys.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        ActiveUser activeUser = (ActiveUser)principalCollection.getPrimaryPrincipal();
        User user = activeUser.getUser();

        if (user.getType().equals(Constant.USER_TYPE_SUPPER)){
            authorizationInfo.addStringPermission("*:*");
        } else {
            List<String> percode = new ArrayList<>();
            for (Permission permission : activeUser.getPermissions()) {
                if (permission.getType().equals(Constant.TYPE_PERMISSION)){
                    percode.add(permission.getPercode());
                }
            }
            System.out.println(percode.toString());
            authorizationInfo.addStringPermissions(percode);
        }


        return authorizationInfo;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;

        //查询用户拥有角色
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();

        //查询用户的角色对应的权限
        QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();

        //查询用户
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("available",Constant.AVAILABLE_TRUE);
        userQueryWrapper.eq("loginname",token.getUsername());
        User user = userService.getOne(userQueryWrapper);
        if (user!=null){
            ActiveUser activeUser = new ActiveUser();
            activeUser.setUser(user);

            //根据用户id查询拥有的角色id,并把拥有的角色放到activeUser中
            List<Integer> rids = userService.getHasRoleIdsByUid(user.getId());
            roleQueryWrapper.eq("available",Constant.AVAILABLE_TRUE);
            roleQueryWrapper.in("id",rids);
            activeUser.setRoles(roleService.list(roleQueryWrapper));

            //根据角色id查询对于的权限，并放到activeUser中
            List<Integer> pids = permissionService.getRolePermissionPidsByRids(rids);
            permissionQueryWrapper.eq("available",Constant.AVAILABLE_TRUE);
            permissionQueryWrapper.in("id",pids);
            activeUser.setPermissions(permissionService.list(permissionQueryWrapper));


            SimpleAuthenticationInfo authenticationInfo =
                    new SimpleAuthenticationInfo(activeUser,user.getPwd(),
                            ByteSource.Util.bytes(user.getSalt()),getName());
            return authenticationInfo;
        }

        return null;
    }
}
