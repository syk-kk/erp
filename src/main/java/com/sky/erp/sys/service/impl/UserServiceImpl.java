package com.sky.erp.sys.service.impl;

import com.sky.erp.sys.entity.User;
import com.sky.erp.sys.mapper.UserMapper;
import com.sky.erp.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author syk
 * @since 2020-07-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

//    @Override
//    public User getById(Serializable id) {
//        return super.getById(id);
//    }
//
//    @Override
//    public boolean save(User entity) {
//        return super.save(entity);
//    }
//
//    @Override
//    public boolean updateById(User entity) {
//        return super.updateById(entity);
//    }

    @Override
    public boolean removeById(Serializable id) {
        getBaseMapper().removeRoleUserByUid((Integer) id);
        return super.removeById(id);
    }

    @Override
    public void saveRoleUser(Integer uid, Integer[] rids) {
        getBaseMapper().removeRoleUserByUid(uid);
        for (Integer rid : rids) {
            getBaseMapper().saveRoleUser(uid,rid);
        }
    }

    @Override
    public List<Integer> getHasRoleIdsByUid(Integer uid) {
        return getBaseMapper().getHasRoleIdsByUid(uid);
    }
}
