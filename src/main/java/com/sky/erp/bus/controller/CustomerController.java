package com.sky.erp.bus.controller;


import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.erp.bus.entity.Customer;
import com.sky.erp.bus.service.ICustomerService;
import com.sky.erp.bus.vo.CustomerVo;
import com.sky.erp.sys.common.DataGridView;
import com.sky.erp.sys.common.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

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

    /**
     * 添加客户
     */
    @RequestMapping("addCustomer")
    public ResultObj addCustomer(Customer customer){
        try {
            if (customer.getAvailable()==null){
                customer.setAvailable(0);
            }
            customerService.save(customer);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e){
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 更新客户
     */
    @RequestMapping("updateCustomer")
    public ResultObj updateCustomer(Customer customer){
        try {
            if (customer.getAvailable()==null){
                customer.setAvailable(0);
            }
            customerService.updateById(customer);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e){
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除单个客户
     */
    @RequestMapping("deleteCustomer")
    public ResultObj deleteCustomer(Integer id){
        try {
            customerService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除客户
     */
    @RequestMapping("deleteBatchCustomer")
    public ResultObj deleteBatchCustomer(CustomerVo customerVo){
        try {
            customerService.removeByIds(Arrays.asList(customerVo.getIds()));
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }

}
