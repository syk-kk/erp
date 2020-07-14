package com.sky.erp.sys.service.impl;

import com.sky.erp.sys.entity.Role;
import com.sky.erp.sys.mapper.RoleMapper;
import com.sky.erp.sys.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.erp.sys.vo.RoleVo;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author syk
 * @since 2020-07-11
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Override
    public boolean removeById(Serializable id) {
        getBaseMapper().removeRolePermissionByRid(id);
        getBaseMapper().removeRoleUserByRid(id);
        return super.removeById(id);
    }

    @Override
    public List<Integer> queryRolePermissionIdsByRid(Integer rid){
       return getBaseMapper().queryRolePermissionIdsByRid(rid);
    }

    @Override
    public void savaRolePermission(RoleVo roleVo) {
        getBaseMapper().removeRolePermissionByRid(roleVo.getId());
        if (roleVo.getPid()!=null&&roleVo.getPid().length>0){
            for (Integer pid : roleVo.getPid()) {
                getBaseMapper().savaRolePermission(roleVo.getId(),pid);
            }
        }
    }
}
