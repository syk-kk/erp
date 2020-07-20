package com.sky.erp.bus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author syk
 * @since 2020-07-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_outport")
public class Outport implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer providerid;

    private String paytype;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outporttime;

    private String operateperson;

    private Double outportprice;

    private Integer number;

    private String remark;

    private Integer goodsid;

    @TableField(exist = false)
    private String providername;//供应商名称

    @TableField(exist = false)
    private String goodsname;//商品名称


}
