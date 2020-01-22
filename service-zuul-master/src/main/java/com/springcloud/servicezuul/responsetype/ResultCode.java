package com.springcloud.servicezuul.responsetype;

public class ResultCode {

    public static final int RESULT_SUCCESS  = 0;            // 接口返回正常
    public static final int  RESULT_ERROR   = -1;           // 接口返回异常
    public static final int RESULT_NOAUTH   = -2;           // 无权限
    public static final int RESULE_NOLOGIN  = -3;           // 未登陆
    public static final int RESULE_SERVICEINSTAL  = -100;           // 未安装

    public static final int RESULE_NOFOUND  = 404;          //无此接口
    public static final int TOO_MANY_REQUESTS  = 429;          //太多访问

    public static final int RESULE_TIMEOUT  = 999;          //服务器忙

}
