package com.sky.erp.sys.service;

import com.sky.erp.sys.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author syk
 * @since 2020-07-08
 */
public interface IPermissionService extends IService<Permission> {
    public List<Integer> getRolePermissionPidsByRids(List<Integer> rids);

}
