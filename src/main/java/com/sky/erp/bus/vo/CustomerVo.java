package com.sky.erp.bus.vo;

import com.sky.erp.bus.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomerVo extends Customer {

    private Integer page;
    private Integer limit;
    private Integer[] ids;
}
