package com.sky.erp.bus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("bus")
public class BusController {

    /**
     * 跳转到客户管理页面
     */
    @RequestMapping("toCustomerManager")
    public String toCustomerManager(){
        return "business/customer/customerManager";
    }

    /**
     * 跳转到供应商管理页面
     */
    @RequestMapping("toProviderManager")
    public String toProviderManager(){
        return "business/provider/providerManager";
    }

    /**
     * 跳转到商品管理页面
     */
    @RequestMapping("toGoodsManager")
    public String toGoodsManager(){
        return "business/goods/goodsManager";
    }

    /**
     * 跳转到进货管理页面
     */
    @RequestMapping("toInportManager")
    public String toInportManager(){
        return "business/inport/inportManager";
    }

    /**
     * 跳转到退货管理页面
     */
    @RequestMapping("toOutportManager")
    public String toOutportManager(){
        return "business/outport/outportManager";
    }
}
