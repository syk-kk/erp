package com.sky.erp.bus.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.erp.bus.entity.Goods;
import com.sky.erp.bus.entity.Provider;
import com.sky.erp.bus.service.IGoodsService;
import com.sky.erp.bus.service.IProviderService;
import com.sky.erp.bus.vo.GoodsVo;
import com.sky.erp.sys.common.DataGridView;
import com.sky.erp.sys.common.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IProviderService providerService;

    /**
     * 加载商品
     */
    @RequestMapping("loadAllGoods")
    public DataGridView loadAllGoods(GoodsVo goodsVo){
        List<Goods> data = new ArrayList<>();
        Page<Goods> page = new Page<>(goodsVo.getPage(),goodsVo.getLimit());
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(goodsVo.getProviderid()!=null,"providerid",goodsVo.getProviderid());
        queryWrapper.like(StrUtil.isNotBlank(goodsVo.getGoodsname()),"goodsname",goodsVo.getGoodsname());
        queryWrapper.like(StrUtil.isNotBlank(goodsVo.getProductcode()),"productcode",goodsVo.getProductcode());
        queryWrapper.like(StrUtil.isNotBlank(goodsVo.getPromitcode()),"promitcode",goodsVo.getPromitcode());
        queryWrapper.like(StrUtil.isNotBlank(goodsVo.getDescription()),"description",goodsVo.getDescription());
        queryWrapper.like(StrUtil.isNotBlank(goodsVo.getSize()),"size",goodsVo.getSize());
        goodsService.page(page,queryWrapper);
        for (Goods goods : page.getRecords()) {
            Provider provider = providerService.getById(goods.getProviderid());
            goods.setProvidername(provider.getProvidername());
        }
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /**
     * 删除商品
     */
    @RequestMapping("deleteGoods")
    public ResultObj deleteGoods(Integer id){
        try {
            goodsService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除
     */
    @RequestMapping("deleteBatchGoods")
    public ResultObj deleteBatchGoods(GoodsVo goodsVo){
        try {
            goodsService.removeByIds(Arrays.asList(goodsVo.getIds()));
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 修改商品
     */
    @RequestMapping("updateGoods")
    public ResultObj updateGoods(Goods goods){
        try {


            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e){
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 添加商品
     */
    @RequestMapping("addGoods")
    public ResultObj addGoods(Goods goods){
        try {


            return ResultObj.ADD_SUCCESS;
        } catch (Exception e){
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 获取供应商
     */
//    public List<String> getProviderName(){
//
//    }

}
