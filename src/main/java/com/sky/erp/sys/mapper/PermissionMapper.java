package com.sky.erp.sys.mapper;

import com.sky.erp.sys.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author syk
 * @since 2020-07-08
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    //根据id删除角色和权限关系表中的对应记录
    void removeRolePermissionById(@Param("id") Serializable id);
    List<Integer> getRolePermissionPidsByRids(List<Integer> rids);
}
