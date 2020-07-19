package com.sky.erp.bus.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.erp.bus.entity.Goods;
import com.sky.erp.bus.entity.Inport;
import com.sky.erp.bus.entity.Provider;
import com.sky.erp.bus.service.IGoodsService;
import com.sky.erp.bus.service.IInportService;
import com.sky.erp.bus.service.IProviderService;
import com.sky.erp.bus.vo.InportVo;
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
 *  进货管理前端控制器
 * </p>
 *
 * @author syk
 * @since 2020-07-19
 */
@RestController
@RequestMapping("inport")
public class InportController {

    @Autowired
    private IInportService inportService;

    @Autowired
    private IProviderService providerService;

    @Autowired
    private IGoodsService goodsService;

    /**
     * 加载进货信息
     * @param inportVo
     * @return
     */
    @RequestMapping("loadAllInport")
    public DataGridView loadAllInport(InportVo inportVo){
        Page<Inport> page = new Page<>(inportVo.getPage(),inportVo.getLimit());
        QueryWrapper<Inport> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(inportVo.getProviderid()!=null,"providerid",inportVo.getProviderid());
        queryWrapper.eq(inportVo.getGoodsid()!=null,"goodsid",inportVo.getGoodsid());
        queryWrapper.ge(inportVo.getStartTime()!=null,"inporttime",inportVo.getStartTime());
        queryWrapper.le(inportVo.getEndTime()!=null,"inporttime",inportVo.getEndTime());
        queryWrapper.eq(StrUtil.isNotBlank(inportVo.getPaytype()),"paytype",inportVo.getPaytype());
        queryWrapper.like(StrUtil.isNotBlank(inportVo.getOperateperson()),"operateperson",inportVo.getOperateperson());

        queryWrapper.orderByDesc("inporttime");
        inportService.page(page,queryWrapper);
        for (Inport inport : page.getRecords()) {
            Provider provider = providerService.getById(inport.getProviderid());
            Goods goods = goodsService.getById(inport.getGoodsid());
            if (null!=provider){
                inport.setProvidername(provider.getProvidername());
            }
            if (null!=goods){
                inport.setGoodsname(goods.getGoodsname());
            }
        }
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /**
     * 根据id删除进货信息
     * @param id
     * @return
     */
    @RequestMapping("deleteInport")
    public ResultObj deleteInport(Integer id){
        try {
            inportService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 根据id更新进货信息
     * @param inport
     * @return
     */
    @RequestMapping("updateInport")
    public ResultObj updateInport(Inport inport){
        try {
            inportService.updateById(inport);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e){
            return ResultObj.UPDATE_ERROR;
        }
    }

    @RequestMapping("addInport")
    public ResultObj addInport(Inport inport, HttpSession session){
        try {
            User user = (User) session.getAttribute("user");
            inport.setOperateperson(user.getName());

            inport.setInporttime(new Date());
            inportService.save(inport);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e){
            return ResultObj.ADD_ERROR;
        }
    }


}
