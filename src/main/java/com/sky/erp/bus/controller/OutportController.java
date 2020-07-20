package com.sky.erp.bus.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.erp.bus.entity.Goods;
import com.sky.erp.bus.entity.Inport;
import com.sky.erp.bus.entity.Outport;
import com.sky.erp.bus.entity.Provider;
import com.sky.erp.bus.service.IGoodsService;
import com.sky.erp.bus.service.IInportService;
import com.sky.erp.bus.service.IOutportService;
import com.sky.erp.bus.service.IProviderService;
import com.sky.erp.bus.vo.OutportVo;
import com.sky.erp.sys.common.DataGridView;
import com.sky.erp.sys.common.ResultObj;
import com.sky.erp.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * <p>
 *  退货管理前端控制器
 * </p>
 *
 * @author syk
 * @since 2020-07-19
 */
@RestController
@RequestMapping("outport")
public class OutportController {

    @Autowired
    private IOutportService outportService;

    @Autowired
    private IInportService inportService;

    @Autowired
    private IProviderService providerService;

    @Autowired
    private IGoodsService goodsService;

    /**
     * 商品退货
     * @param outport
     * @return
     */
    @RequestMapping("addOutport")
    public ResultObj addOutport(Integer inportid, Outport outport, HttpSession session){
        try {
            User user = (User)session.getAttribute("user");

            Inport inport = inportService.getById(inportid);

            Goods goods = goodsService.getById(inport.getGoodsid());
            goods.setNumber(goods.getNumber()-outport.getNumber());
            goodsService.updateById(goods);

            outport.setPaytype(inport.getPaytype());
            outport.setProviderid(inport.getProviderid());
            outport.setGoodsid(inport.getGoodsid());
            outport.setOutporttime(new Date());
            outport.setOperateperson(user.getName());
            outport.setOutportprice(outport.getNumber()*inport.getInportprice());
            outportService.save(outport);

            return new ResultObj(200,"退货成功");
        } catch (Exception e){
            return new ResultObj(-1,"退货失败");
        }

    }

    /**
     * 查询退货信息
     * @param outportVo
     * @return
     */
    @RequestMapping("loadAllOutport")
    public DataGridView loadALLOutport(OutportVo outportVo){
        Page<Outport> page = new Page<>(outportVo.getPage(),outportVo.getLimit());
        QueryWrapper<Outport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(outportVo.getProviderid()!=null,"providerid",outportVo.getProviderid());
        queryWrapper.eq(outportVo.getGoodsid()!=null,"goodsid",outportVo.getGoodsid());
        queryWrapper.like(StrUtil.isNotBlank(outportVo.getPaytype()),"paytype",outportVo.getPaytype());
        queryWrapper.like(StrUtil.isNotBlank(outportVo.getOperateperson()),"operateperson",outportVo.getOperateperson());
        queryWrapper.ge(outportVo.getStartTime()!=null,"outporttime",outportVo.getStartTime());
        queryWrapper.le(outportVo.getEndTime()!=null,"outporttime",outportVo.getEndTime());
        queryWrapper.orderByDesc("outporttime");
        outportService.page(page,queryWrapper);
        for (Outport outport : page.getRecords()) {
            Provider provider = providerService.getById(outport.getProviderid());
            Goods goods = goodsService.getById(outport.getGoodsid());
            if (null!=provider){
                outport.setProvidername(provider.getProvidername());
            }
            if (null!=goods){
                outport.setGoodsname(goods.getGoodsname());
            }
        }
        return new DataGridView(page.getTotal(),page.getRecords());
    }

}
