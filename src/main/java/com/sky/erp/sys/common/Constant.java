package com.sky.erp.sys.common;

public interface Constant {

    /**
     * 状态码
     */
    public static final Integer OK=200;
    public static final Integer ERROR=-1;

    /**
     * 状态信息
     */
    public static final String MSG_LOGIN_SUCCESS="登录成功";
    public static final String MSG_LOGIN_ERROR_AUTHC="用户名或密码错误";
    public static final String MSG_LOGIN_ERROR_CODE="验证码错误";
    public static final String MSG_DELETE_SUCCESS="删除成功";
    public static final String MSG_DELETE_ERROR="删除失败";
    public static final String MSG_ADD_SUCCESS="添加成功";
    public static final String MSG_ADD_ERROR="添加失败";
    public static final String MSG_UPDATE_SUCCESS="更新成功";
    public static final String MSG_UPDATE_ERROR="更新失败";
    public static final String MSG_RESET_SUCCESS="重置成功";
    public static final String MSG_RESET_ERROR="重置失败";
    public static final String MSG_DISPATCH_SUCCESS="分配成功";
    public static final String MSG_DISPATCH_ERROR="分配失败";


    /**
     * sys_permission表中type字段分类
     */
    public static final String TYPE_MENU = "menu";
    public static final String TYPE_PERMISSION = "permission";

    /**
     * 菜单是否可用
     */
    public static final Integer AVAILABLE_TRUE = 1;
    public static final Integer AVAILABLE_FALSE = 0;

    /**
     * 后台登录用户类型
     */
    public static final Integer USER_TYPE_SUPPER = 0;
    public static final Integer USER_TYPE_NORMAL = 1;

    /**
     * 用户默认密码
     */
    public static final String USER_DEFAULT_PWD = "123456";
}
