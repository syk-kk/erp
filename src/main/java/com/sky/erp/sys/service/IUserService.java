package com.sky.erp.sys.service;

import com.sky.erp.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author syk
 * @since 2020-07-07
 */
public interface IUserService extends IService<User> {
     void saveRoleUser(Integer uid,Integer[] rids);
     List<Integer> getHasRoleIdsByUid(Integer uid);

}
