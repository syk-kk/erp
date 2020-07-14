package com.sky.erp.sys.service;

import com.sky.erp.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author syk
 * @since 2020-07-07
 */
public interface IUserService extends IService<User> {
    public void saveRoleUser(Integer uid,Integer[] rids);

}
