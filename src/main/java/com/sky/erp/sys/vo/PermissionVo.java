package com.sky.erp.sys.vo;

import com.sky.erp.sys.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PermissionVo extends Permission {

    private Integer page;
    private Integer limit;
    private Integer[] ids;


}
