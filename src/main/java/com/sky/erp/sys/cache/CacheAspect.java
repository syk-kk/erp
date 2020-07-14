package com.sky.erp.sys.cache;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.sky.erp.sys.entity.Dept;
import com.sky.erp.sys.entity.User;
import com.sky.erp.sys.vo.DeptVo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class CacheAspect {

    private Log log = LogFactory.get();
    /**
     * 缓存容器map
     */
    private Map<String,Object> cache = new HashMap<>();

    /**
     * 部门切点表达式
     */
    private static final String POINT_DEPT_UPDATE = "execution(* com.sky.erp.sys.service.impl.DeptServiceImpl.updateById(..))";
    private static final String POINT_DEPT_REMOVE = "execution(* com.sky.erp.sys.service.impl.DeptServiceImpl.removeById(..))";
    private static final String POINT_DEPT_REMOVE_BATCH = "execution(* com.sky.erp.sys.service.impl.DeptServiceImpl.removeByIds(..))";
    private static final String POINT_DEPT_GET = "execution(* com.sky.erp.sys.service.impl.DeptServiceImpl.getById(..))";
    private static final String POINT_DEPT_ADD = "execution(* com.sky.erp.sys.service.impl.DeptServiceImpl.save(..))";

    /**
     * 用户切点表达式
     */
    private static final String POINT_USER_GET = "execution(* com.sky.erp.sys.service.impl.UserServiceImpl.getById(..))";
    private static final String POINT_USER_UPDATE = "execution(* com.sky.erp.sys.service.impl.UserServiceImpl.updateById(..))";
    private static final String POINT_USER_REMOVE = "execution(* com.sky.erp.sys.service.impl.UserServiceImpl.removeById(..))";
    private static final String POINT_USER_ADD = "execution(* com.sky.erp.sys.service.impl.UserServiceImpl.save(..))";

    /**
     * 部门前缀
     */
    private static final String CACHE_DEPT_PREFIX = "dept:";

    /**
     * 用户前缀
     */
    private static final String CACHE_USER_PREFIX = "user:";


//    ************************************部门切面************************************************************

    /**
     * 添加部门缓存信息
     */
    @Around(POINT_DEPT_ADD)
    public Object cacheDeptAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        Dept dept = (Dept)joinPoint.getArgs()[0];
        Boolean flag = (Boolean)joinPoint.proceed();
        if (flag){
            cache.put(CACHE_DEPT_PREFIX+dept.getId(),dept);
        }
        return flag;
    }



    /**
     * 得到部门缓存信息
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINT_DEPT_GET)
    public Object cacheDeptGet(ProceedingJoinPoint joinPoint) throws Throwable {
        Integer arg = (Integer) joinPoint.getArgs()[0];
        Object object = cache.get(CACHE_DEPT_PREFIX + arg);
        if (object!=null){
            log.info("从缓存中获取部门信息"+CACHE_DEPT_PREFIX + arg);
            return object;
        } else {
            Dept result = (Dept) joinPoint.proceed();
            cache.put(CACHE_DEPT_PREFIX+arg,result);
            log.info("从数据库中获取部门信息并放入缓存中"+CACHE_DEPT_PREFIX + arg);
            return result;
        }
    }

    /**
     * 更新缓存中的部门信息
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINT_DEPT_UPDATE)
    public Object cacheDeptUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        Dept arg = (Dept)joinPoint.getArgs()[0];
        Boolean flag = (Boolean)joinPoint.proceed();
        if (flag){
            cache.put(CACHE_DEPT_PREFIX+arg.getId(),arg);
            log.info("已更新缓存中的部门信息"+CACHE_DEPT_PREFIX+arg.getId());
        }
        return flag;
    }

    /**
     * 删除缓存中的部门信息
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINT_DEPT_REMOVE)
    public Object cacheDeptRemove(ProceedingJoinPoint joinPoint) throws Throwable {
        Integer arg = (Integer) joinPoint.getArgs()[0];
        Object object = cache.get(CACHE_DEPT_PREFIX + arg);
        Boolean flag = (Boolean)joinPoint.proceed();
        if (object!=null && flag){
            cache.remove(CACHE_DEPT_PREFIX+arg);
            log.info("已删除缓存中的部门信息"+CACHE_DEPT_PREFIX+arg);
        }

        return flag;
    }

    /**
     * 删除缓存中多个部门信息
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINT_DEPT_REMOVE_BATCH)
    public Object cacheDeptRemoveBatch(ProceedingJoinPoint joinPoint) throws Throwable {
        List<Integer> arg = (List<Integer>) joinPoint.getArgs()[0];
        Boolean flag = (Boolean)joinPoint.proceed();
        if (flag){
            for (Integer id : arg) {
                if (cache.containsKey(CACHE_DEPT_PREFIX+id)){
                    cache.remove(CACHE_DEPT_PREFIX+id);
                }
            }
            log.info("已删除缓存中多个部门信息");
        }
        return flag;
    }


//    **********************************用户切面*********************************************************

    /**
     * 添加用户缓存信息
     */
    @Around(POINT_USER_ADD)
    public Object cacheUserAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        User user = (User) joinPoint.getArgs()[0];
        Boolean flag = (Boolean)joinPoint.proceed();
        if (flag){
            cache.put(CACHE_USER_PREFIX+user.getId(),user);
        }
        return flag;
    }

    /**
     * 得到缓存用户信息
     */
    @Around(POINT_USER_GET)
    public Object cacheUserGet(ProceedingJoinPoint joinPoint) throws Throwable {
        Integer arg = (Integer)joinPoint.getArgs()[0];
        User user = (User)cache.get(CACHE_USER_PREFIX+arg);
        if (user!=null){
            log.info("从缓存中取用户信息");
            return user;
        }else {
            User o = (User)joinPoint.proceed();
            cache.put(CACHE_USER_PREFIX+arg,o);
            log.info("从数据库中查询用户信息并放入缓存中"+CACHE_USER_PREFIX+arg);
            return o;
        }
    }

    /**
     * 更新用户缓存信息
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(POINT_USER_UPDATE)
    public Object cacheUserUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        User arg = (User)joinPoint.getArgs()[0];
        Boolean flag = (Boolean)joinPoint.proceed();
        if (flag){
            cache.put(CACHE_USER_PREFIX+arg.getId(),arg);
            log.info("已更新用户缓存信息");
        }
        return flag;
    }

    /**
     * 删除用户缓存信息
     */
    @Around(POINT_USER_REMOVE)
    public Object cacheUserRemove(ProceedingJoinPoint joinPoint) throws Throwable {
        Integer arg = (Integer)joinPoint.getArgs()[0];
        Object object = cache.get(CACHE_USER_PREFIX+arg);
        Boolean flag = (Boolean)joinPoint.proceed();
        if (flag&&object!=null){
            cache.remove(CACHE_USER_PREFIX+arg);
            log.info("已删除用户缓存信息");
        }
        return flag;
    }
}
