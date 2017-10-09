package com.yingfeng.commons.web.constants;

/**
 * 异常码常量
 * @author pengc
 * @since 2017/09/05
 */
public class ExceptionCodeInfo {

    //用户名或者密码错误
    public static String LOGIN_INFO = "10001";

    //验证码错误，请重新输入
    public static String VERIFICATE_CODE = "10002";

    //该账户已经被禁用
    public static String USER_DISABLE = "10004";

    //session失效
    public static String SESSION_FAILURE = "20004";

    //保存异常
    public static String SAVE_ERROR = "20006";

    //文件处理方面
    public static String FILE_NOT_FOUND = "30001";

    //网络配置信息设置失败
    public static String NETWORK_ERR = "30002";

    //文件导出失败
    public static String EXPERT_FILE_ERROR = "30006";

    //文件太大不符合要求
    public static String  FILE_SIZE_ERROR = "30007";

    //上传的文件名称不正确
    public static String  FILE_NAME_ERROR = "30008";

}
