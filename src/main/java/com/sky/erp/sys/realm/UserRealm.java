package com.sky.erp.sys.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sky.erp.sys.common.ActiveUser;
import com.sky.erp.sys.entity.User;
import com.sky.erp.sys.mapper.UserMapper;
import com.sky.erp.sys.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    /*
    授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /*
    认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginname",token.getUsername());
        User user = userService.getOne(queryWrapper);
        if (user!=null){
            ActiveUser activeUser = new ActiveUser();
            activeUser.setUser(user);


            SimpleAuthenticationInfo authenticationInfo =
                    new SimpleAuthenticationInfo(activeUser,user.getPwd(),
                            ByteSource.Util.bytes(user.getSalt()),getName());
            return authenticationInfo;
        }

        return null;
    }
}
