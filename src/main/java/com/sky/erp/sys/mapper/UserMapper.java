package com.sky.erp.sys.mapper;

import com.sky.erp.sys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author syk
 * @since 2020-07-07
 */
public interface UserMapper extends BaseMapper<User> {
    public void removeRoleUserByUid(@Param("uid") Integer uid);
    public void saveRoleUser(@Param("uid") Integer uid,@Param("rid") Integer rid);

}
