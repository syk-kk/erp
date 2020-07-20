package com.sky.erp.bus.service.impl;

import com.sky.erp.bus.entity.Goods;
import com.sky.erp.bus.entity.Inport;
import com.sky.erp.bus.mapper.GoodsMapper;
import com.sky.erp.bus.mapper.InportMapper;
import com.sky.erp.bus.service.IInportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author syk
 * @since 2020-07-19
 */
@Service
public class InportServiceImpl extends ServiceImpl<InportMapper, Inport> implements IInportService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public boolean save(Inport entity) {
        Goods goods = goodsMapper.selectById(entity.getGoodsid());
        goods.setNumber(goods.getNumber()+entity.getNumber());
        goodsMapper.updateById(goods);
        return super.save(entity);
    }

    @Override
    public boolean updateById(Inport entity) {
        Inport inport = getById(entity.getId());
        Goods goods = goodsMapper.selectById(entity.getGoodsid());
        goods.setNumber(goods.getNumber()-inport.getNumber()+entity.getNumber());
        goodsMapper.updateById(goods);
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        Inport inport = getById(id);
        Goods goods = goodsMapper.selectById(inport.getGoodsid());
        goods.setNumber(goods.getNumber()-inport.getNumber());
        goodsMapper.updateById(goods);
        return super.removeById(id);
    }
}
