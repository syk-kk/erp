package com.sky.erp.sys.service.impl;

import com.sky.erp.sys.entity.Permission;
import com.sky.erp.sys.mapper.PermissionMapper;
import com.sky.erp.sys.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author syk
 * @since 2020-07-08
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Override
    public boolean removeById(Serializable id) {

        //根据id删除角色和权限关系表中的对应记录
        getBaseMapper().removeRolePermissionById(id);
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        for (Serializable id : idList) {
            getBaseMapper().removeRolePermissionById(id);
        }
        return super.removeByIds(idList);
    }
}
