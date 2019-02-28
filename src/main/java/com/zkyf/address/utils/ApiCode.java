package com.zkyf.address.utils;

/**
 * 自定义常用的HttpCode
 */
public class ApiCode {

    public final static int OK                      = 200;//ok

    public final static int BAD_REQUEST             = 400;//错误的请求
    public final static int UNAUTHORIZED            = 401;//未经授权（登录相关）
    public final static int NOT_FOUND               = 404;//没有找到

    public final static int INTERNAL_SERVER_ERROR   = 500;//内部服务器错误
    public final static int MISS_PARAMETER          = 502;//缺少参数
    public final static int INVALID_PARAMETER       = 503;//无效参数

    public final static int EXIT_ENTITY       = 504;//已存在

    public final static int BLACK_WORKER       = 700;//黑名单用户
    public final static int BAD_WORKER       = 701;//存在不良记录

    public final static int DB_EXCEPTION            = 710;
    public final static int DB_NULL_VALUE           = 711;

    public final static int SERVICE_NOT_START       = 720;
    public final static int SERVICE_EXCEPTION       = 721;

    public final static int NO_AUTHORITY           = 723;

}
