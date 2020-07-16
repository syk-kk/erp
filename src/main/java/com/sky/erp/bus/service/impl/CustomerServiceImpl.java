package com.sky.erp.bus.service.impl;

import com.sky.erp.bus.entity.Customer;
import com.sky.erp.bus.mapper.CustomerMapper;
import com.sky.erp.bus.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author syk
 * @since 2020-07-16
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

}
