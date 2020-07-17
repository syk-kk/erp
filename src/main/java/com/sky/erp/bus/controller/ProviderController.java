package com.sky.erp.bus.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.erp.bus.entity.Provider;
import com.sky.erp.bus.service.IProviderService;
import com.sky.erp.bus.vo.ProviderVo;
import com.sky.erp.sys.common.Constant;
import com.sky.erp.sys.common.DataGridView;
import com.sky.erp.sys.common.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author syk
 * @since 2020-07-16
 */
@RestController
@RequestMapping("provider")
public class ProviderController {

    @Autowired
    private IProviderService providerService;

    /**
     * 加载供应商信息
     */
    @RequestMapping("loadAllProvider")
    public DataGridView loadAllProvider(ProviderVo providerVo){
        Page<Provider> page = new Page<>(providerVo.getPage(),providerVo.getLimit());
        QueryWrapper<Provider> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(providerVo.getProvidername()),"providername",providerVo.getProvidername());
        queryWrapper.like(StrUtil.isNotBlank(providerVo.getBank()),"bank",providerVo.getBank());
        queryWrapper.like(StrUtil.isNotBlank(providerVo.getConnectionperson()),"connectionperson",providerVo.getConnectionperson());
        providerService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /**
     * 添加供应商
     */
    @RequestMapping("addProvider")
    public ResultObj addProvider(Provider provider){
        try {
            if (provider.getAvailable()==null){
                provider.setAvailable(0);
            }
            providerService.save(provider);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e){
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 更新供应商
     */
    @RequestMapping("updateProvider")
    public ResultObj updateProvider(Provider provider){
        try {
            if (provider.getAvailable()==null){
                provider.setAvailable(0);
            }
            providerService.updateById(provider);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e){
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除单个供应商
     */
    @RequestMapping("deleteProvider")
    public ResultObj deleteProvider(Integer id){
        try {
            providerService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除供应商
     */
    @RequestMapping("deleteBatchProvider")
    public ResultObj deleteBatchProvider(ProviderVo providerVo){
        try {
            providerService.removeByIds(Arrays.asList(providerVo.getIds()));
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 为商品管理页面的下拉框加载所有可用供应商
     */
    @RequestMapping("loadAllProviderForSelect")
    public DataGridView loadAllProviderForSelect(){
        QueryWrapper<Provider> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constant.AVAILABLE_TRUE);
        List<Provider> data = providerService.list(queryWrapper);
        return new DataGridView(data);
    }


}
