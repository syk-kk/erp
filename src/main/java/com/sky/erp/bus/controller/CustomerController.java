package com.sky.erp.bus.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.erp.bus.entity.Customer;
import com.sky.erp.bus.service.ICustomerService;
import com.sky.erp.bus.vo.CustomerVo;
import com.sky.erp.sys.common.DataGridView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author syk
 * @since 2020-07-16
 */
@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    /**
     * 加载客户信息
     */
    @RequestMapping("loadAllCustomer" +
            "")
    public DataGridView loadAllCustomer(CustomerVo customerVo){
        Page<Customer> page = new Page<>(customerVo.getPage(),customerVo.getLimit());
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(customerVo.getCustomername()),"customername",customerVo.getCustomername());
        queryWrapper.like(StrUtil.isNotBlank(customerVo.getBank()),"bank",customerVo.getBank());
        queryWrapper.like(StrUtil.isNotBlank(customerVo.getConnectionperson()),"connectionperson",customerVo.getConnectionperson());
        customerService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

}
