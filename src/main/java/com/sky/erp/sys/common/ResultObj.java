package com.sky.erp.sys.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 返回结果信息实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultObj implements Serializable {
    /*
    登录信息:登录成功、用户名或密码错误、验证码错误
     */
    public static final ResultObj LOGIN_SUCCESS = new ResultObj(Constant.OK,Constant.MSG_LOGIN_SUCCESS);
    public static final ResultObj LOGIN_ERROR_AUTHC = new ResultObj(Constant.ERROR,Constant.MSG_LOGIN_ERROR_AUTHC);
    public static final ResultObj LOGIN_ERROR_CODE = new ResultObj(Constant.ERROR,Constant.MSG_LOGIN_ERROR_CODE);

    /*
    删除信息
     */
    public static final ResultObj DELETE_SUCCESS = new ResultObj(Constant.OK,Constant.MSG_DELETE_SUCCESS);
    public static final ResultObj DELETE_ERROR = new ResultObj(Constant.ERROR,Constant.MSG_DELETE_ERROR);

    /*
    更新信息
     */
    public static final ResultObj UPDATE_SUCCESS = new ResultObj(Constant.OK,Constant.MSG_UPDATE_SUCCESS);
    public static final ResultObj UPDATE_ERROR = new ResultObj(Constant.ERROR,Constant.MSG_UPDATE_ERROR);

    /*
    添加信息
     */
    public static final ResultObj ADD_SUCCESS = new ResultObj(Constant.OK,Constant.MSG_ADD_SUCCESS);
    public static final ResultObj ADD_ERROR = new ResultObj(Constant.ERROR,Constant.MSG_ADD_ERROR);

    /*
    重置信息
     */
    public static final ResultObj RESET_SUCCESS = new ResultObj(Constant.OK,Constant.MSG_RESET_SUCCESS);
    public static final ResultObj RESET_ERROR = new ResultObj(Constant.ERROR,Constant.MSG_RESET_ERROR);

    /*
    权限分配信息
     */
    public static final ResultObj DISPATCH_SUCCESS = new ResultObj(Constant.OK,Constant.MSG_DISPATCH_SUCCESS);
    public static final ResultObj DISPATCH_ERROR = new ResultObj(Constant.ERROR,Constant.MSG_DISPATCH_ERROR);




    private Integer code;
    private String msg;
}
