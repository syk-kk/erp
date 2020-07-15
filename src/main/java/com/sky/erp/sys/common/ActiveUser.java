package com.sky.erp.sys.common;

import com.sky.erp.sys.entity.Permission;
import com.sky.erp.sys.entity.Role;
import com.sky.erp.sys.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录用户实体类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveUser implements Serializable {

    private User user;

    private List<Role> roles = new ArrayList<>();

    private List<Permission> permissions = new ArrayList<>();

}
