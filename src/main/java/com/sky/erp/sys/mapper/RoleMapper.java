package com.sky.erp.sys.mapper;

import com.sky.erp.sys.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.erp.sys.vo.RoleVo;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author syk
 * @since 2020-07-11
 */
public interface RoleMapper extends BaseMapper<Role> {

   public void removeRolePermissionByRid(@Param("id") Serializable id);
   public void removeRoleUserByRid(@Param("id") Serializable id);
   public List<Integer> queryRolePermissionIdsByRid(@Param("rid") Integer rid);
   public void savaRolePermission(@Param("rid") Integer rid,@Param("pid") Integer pid);
}
