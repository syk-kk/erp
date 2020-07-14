package com.sky.erp.sys.vo;

import com.sky.erp.sys.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RoleVo extends Role {
    private Integer page;
    private Integer limit;
    private Integer[] pid;
}
