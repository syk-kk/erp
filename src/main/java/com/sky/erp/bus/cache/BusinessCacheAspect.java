package com.sky.erp.bus.cache;

import cn.hutool.log.Log;
import com.sky.erp.bus.entity.Customer;
import com.sky.erp.bus.entity.Provider;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class BusinessCacheAspect {

    private Log log = Log.get();

    //缓存容器
    private Map<String,Object> cache = new HashMap<>();

    /**
     * 客户切点表达式
     */
    private static final String POINT_CUSTOMER_ADD = "execution(* com.sky.erp.bus.service.impl.CustomerServiceImpl.save(..))";
    private static final String POINT_CUSTOMER_UPDATE = "execution(* com.sky.erp.bus.service.impl.CustomerServiceImpl.updateById(..))";
    private static final String POINT_CUSTOMER_GET = "execution(* com.sky.erp.bus.service.impl.CustomerServiceImpl.getById(..))";
    private static final String POINT_CUSTOMER_REMOVE = "execution(* com.sky.erp.bus.service.impl.CustomerServiceImpl.removeById(..))";
    private static final String POINT_CUSTOMER_REMOVE_BATCH = "execution(* com.sky.erp.bus.service.impl.CustomerServiceImpl.removeByIds(..))";

    /**
     * 供应商切点表达式
     */
    private static final String POINT_PROVIDER_ADD = "execution(* com.sky.erp.bus.service.impl.ProviderServiceImpl.save(..))";
    private static final String POINT_PROVIDER_UPDATE = "execution(* com.sky.erp.bus.service.impl.ProviderServiceImpl.updateById(..))";
    private static final String POINT_PROVIDER_GET = "execution(* com.sky.erp.bus.service.impl.ProviderServiceImpl.getById(..))";
    private static final String POINT_PROVIDER_REMOVE = "execution(* com.sky.erp.bus.service.impl.ProviderServiceImpl.removeById(..))";
    private static final String POINT_PROVIDER_REMOVE_BATCH = "execution(* com.sky.erp.bus.service.impl.ProviderServiceImpl.removeByIds(..))";



    /**
     * 客户前缀
     */
    private static final String CACHE_CUSTOMER_PREFIX = "customer";

    /**
     * 供应商前缀
     */
    private static final String CACHE_PROVIDER_PREFIX = "provider";
    
    
    
    
//    ******************客户切面开始*************************************************************************************

    /**
     * 添加客户切入
     */
    @Around(POINT_CUSTOMER_ADD)
    public Object cacheCustomerAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        Customer arg = (Customer)joinPoint.getArgs()[0];
        Boolean flag = (Boolean) joinPoint.proceed();
        if (flag){
            cache.put(CACHE_CUSTOMER_PREFIX+arg.getId(),arg);
            log.info("以向缓存中添加客户"+arg.getId());
        }
        return flag;
    }

    /**
     * 修改客户切入
     */
    @Around(POINT_CUSTOMER_UPDATE)
    public Object cacheCustomerUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        Customer arg = (Customer)joinPoint.getArgs()[0];
        Boolean flag = (Boolean)joinPoint.proceed();
        if (flag){
            cache.put(CACHE_CUSTOMER_PREFIX+arg.getId(),arg);
            log.info("已更新缓存中客户"+arg.getId());
        }
        return flag;
    }

    /**
     * 删除客户切入
     */
    @Around(POINT_CUSTOMER_REMOVE)
    public Object cacheCustomerDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        Integer arg = (Integer)joinPoint.getArgs()[0];
        Boolean flag = (Boolean)joinPoint.proceed();
        if (flag){
            if (cache.containsKey(CACHE_CUSTOMER_PREFIX+arg)){
                cache.remove(CACHE_CUSTOMER_PREFIX+arg);
                log.info("已删除数据库和缓存中客户"+arg);
            }
           log.info("缓存中没有，只删除数据库中客户"+arg);
        }
        return flag;
    }

    /**
     * 批量删除客户切入
     */
    @Around(POINT_CUSTOMER_REMOVE_BATCH)
    public Object cacheCustomerDeleteBatch(ProceedingJoinPoint joinPoint) throws Throwable {
        List<Integer> arg = (List<Integer>) joinPoint.getArgs()[0];
        Boolean flag = (Boolean)joinPoint.proceed();
        if (flag){
            for (Integer id : arg) {
                cache.remove(CACHE_CUSTOMER_PREFIX+id);
            }
            log.info("已删除缓存中多个客户"+arg.toString());
        }
        return flag;
    }

    /**
     * 获取客户切入
     */
    @Around(POINT_CUSTOMER_GET)
    public Object cacheCustomerGet(ProceedingJoinPoint joinPoint) throws Throwable {
        Integer arg = (Integer)joinPoint.getArgs()[0];
        if (cache.containsKey(CACHE_CUSTOMER_PREFIX+arg)){
            log.info("从缓存中获取客户"+arg);
            return cache.get(CACHE_CUSTOMER_PREFIX+arg);
        } else {
            Customer customer = (Customer)joinPoint.proceed();
            log.info("从数据库中获取客户"+arg);
            return customer;
        }
    }
    
//    **************客户切面结束*****************************************************************************************
    
    
    
    
    
//    ***************供应商切面开始***************************************************************************************

    /**
     * 添加供应商切入
     */
    @Around(POINT_PROVIDER_ADD)
    public Object cacheProviderAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        Provider arg = (Provider)joinPoint.getArgs()[0];
        Boolean flag = (Boolean) joinPoint.proceed();
        if (flag){
            cache.put(CACHE_PROVIDER_PREFIX+arg.getId(),arg);
            log.info("以向缓存中添加供应商"+arg.getId());
        }
        return flag;
    }

    /**
     * 修改供应商切入
     */
    @Around(POINT_PROVIDER_UPDATE)
    public Object cacheProviderUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        Provider arg = (Provider)joinPoint.getArgs()[0];
        Boolean flag = (Boolean)joinPoint.proceed();
        if (flag){
            cache.put(CACHE_PROVIDER_PREFIX+arg.getId(),arg);
            log.info("已更新缓存中供应商"+arg.getId());
        }
        return flag;
    }

    /**
     * 删除供应商切入
     */
    @Around(POINT_PROVIDER_REMOVE)
    public Object cacheProviderDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        Integer arg = (Integer)joinPoint.getArgs()[0];
        Boolean flag = (Boolean)joinPoint.proceed();
        if (flag){
            if (cache.containsKey(CACHE_PROVIDER_PREFIX+arg)){
                cache.remove(CACHE_PROVIDER_PREFIX+arg);
                log.info("已删除数据库和缓存中供应商"+arg);
            }
            log.info("缓存中没有，只删除数据库中供应商"+arg);
        }
        return flag;
    }

    /**
     * 批量删除供应商切入
     */
    @Around(POINT_PROVIDER_REMOVE_BATCH)
    public Object cacheProviderDeleteBatch(ProceedingJoinPoint joinPoint) throws Throwable {
        List<Integer> arg = (List<Integer>) joinPoint.getArgs()[0];
        Boolean flag = (Boolean)joinPoint.proceed();
        if (flag){
            for (Integer id : arg) {
                cache.remove(CACHE_PROVIDER_PREFIX+id);
            }
            log.info("已删除缓存中多个供应商"+arg.toString());
        }
        return flag;
    }

    /**
     * 获取供应商切入
     */
    @Around(POINT_PROVIDER_GET)
    public Object cacheProviderGet(ProceedingJoinPoint joinPoint) throws Throwable {
        Integer arg = (Integer)joinPoint.getArgs()[0];
        if (cache.containsKey(CACHE_PROVIDER_PREFIX+arg)){
            log.info("从缓存中获取供应商"+arg);
            return cache.get(CACHE_PROVIDER_PREFIX+arg);
        } else {
            Provider provider = (Provider)joinPoint.proceed();
            log.info("从数据库中获取供应商"+arg);
            return provider;
        }
    }
    
    
//    ***************供应商切面结束***************************************************************************************
    
    
    
}