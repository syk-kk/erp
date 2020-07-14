package com.sky.erp.sys.service;

import com.sky.erp.sys.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.erp.sys.vo.RoleVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author syk
 * @since 2020-07-11
 */
public interface IRoleService extends IService<Role> {
    public List<Integer> queryRolePermissionIdsByRid(Integer rid);
    public void savaRolePermission(RoleVo roleVo);
}
