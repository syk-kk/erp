package com.sky.erp.sys.vo;

import com.sky.erp.sys.entity.Dept;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DeptVo extends Dept {

    private Integer page;
    private Integer limit;
    private Integer[] ids;
}
