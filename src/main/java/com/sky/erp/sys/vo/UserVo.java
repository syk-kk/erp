package com.sky.erp.sys.vo;

import com.sky.erp.sys.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserVo extends User {
    private String code;
    private Integer page;
    private Integer limit;


}
