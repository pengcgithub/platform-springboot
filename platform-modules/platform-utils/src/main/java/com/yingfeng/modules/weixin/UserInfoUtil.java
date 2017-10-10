package com.yingfeng.modules.weixin;


/**
 * 获取用户信息工具类<br>  
 * @author pengc
 * @see com.yingfeng.modules.weixin
 * @since 2017/10/10
 */
public class UserInfoUtil {

    // 1.获取code的请求地址
    public static String Get_Code = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=state#wechat_redirect";

    // 替换字符串
    public static String getCode(String APPID, String REDIRECT_URI, String SCOPE) {
        return String.format(Get_Code, APPID, REDIRECT_URI, SCOPE);
    }

    // 2.获取Web_access_tokenhttps的请求地址
    public static String Web_access_tokenhttps = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    // 替换字符串
    public static String getWebAccess(String APPID, String SECRET, String CODE) {
        return String.format(Web_access_tokenhttps, APPID, SECRET, CODE);
    }

    // 3.拉取用户信息的请求地址
    public static String User_Message = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    // 替换字符串
    public static String getUserMessage(String access_token, String openid) {
        return String.format(User_Message, access_token, openid);
    }

    // 4.订阅号拉去用户信息的请求地址，涉及订阅号是否关注的字段属性
    public static String User_Message_Subscribe = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";

    public static String getUserMessageSubscribe(String access_token, String openid) {
        return String.format(User_Message_Subscribe, access_token, openid);
    }

}
