package com.sky.erp.sys.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * json数据实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataGridView {

    private Integer code = 0;
    private String msg = "";

    /*
    数据长度
     */
    private Long count;

    /*
    数据
     */
    private Object data;

    public DataGridView(Object data) {
        this.data = data;
    }

    public DataGridView(Long count, Object data) {
        this.count = count;
        this.data = data;
    }
}
