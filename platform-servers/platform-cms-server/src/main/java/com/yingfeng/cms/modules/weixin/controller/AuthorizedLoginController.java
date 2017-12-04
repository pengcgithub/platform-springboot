package com.yingfeng.cms.modules.weixin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yingfeng.cms.modules.weixin.domain.AuthorizedUsersBean;
import com.yingfeng.modules.weixin.UserInfoUtil;
import com.yingfeng.modules.weixin.WeixinUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;


/**
 * 微信重定向相关方法<br/>
 *
 * @author pengc
 * @see com.yingfeng.cms.modules.weixin.controller
 * @since 2017/9/27
 */
@Slf4j
@Controller
@RequestMapping("/authorized")
public class AuthorizedLoginController {

    private static final String WX_APPID = "服务号APPID";
    private static final String SCOPE_BASE = "snsapi_base"; //静默授权
    private static final String SCOPE_USERINFO = "snsapi_userinfo"; //获取用户信息会提示确认授权
    private static final String WX_APPSECRET = "服务号APPSECRET";

    /**
     * 授权登录URL地址<br/>
     *
     * @return String
     *         微信授权的URL路径
     */
    @ResponseBody
    @GetMapping("/redirectUrl")
    public String authorizedLoginUrl(){
        String redirect_uri = "";

        try {
            String backUrl ="http://m.ershouhui.com/yf/201710/finger/activity/";  //拼接微信回调地址
            redirect_uri = java.net.URLEncoder.encode(backUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("authorizedLoginWoman>>error>>"+e.getMessage());
        }

        String oauth2Url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WX_APPID + "&redirect_uri=" + redirect_uri
                + "&response_type=code&scope=" + SCOPE_USERINFO + "&state=STATE#wechat_redirect";
        log.info("authorizedLoginWomanUrl>>"+ oauth2Url);

        return oauth2Url;
    }

    /**
     * 获取用户授权<br/>
     *
     * @param code
     *      微信授权之后返回的code
     * @param state
     *      微信授权之后返回的state
     * @return 授权用户信息
     */
    @GetMapping("/userInfo")
    @ResponseBody
    public AuthorizedUsersBean getWxAuthorizedUser(
            @RequestParam(name = "code") String code, @RequestParam(name = "state") String state) {

        AuthorizedUsersBean authorizedUsersBean = new AuthorizedUsersBean();

        // 1. 用户同意授权,获取code
        log.info("用户同意授权,获取code:{} , state:{}", code, state);

        // 2. 通过code换取网页授权access_token
        if (code != null || !(code.equals(""))) {

            // 替换字符串，获得请求access token URL
            String tokenUrl = UserInfoUtil.getWebAccess(WX_APPID, WX_APPSECRET, code);
            log.info("第二步:get Access Token URL:{}", tokenUrl);

            // 通过https方式请求获得web_access_token
            String response = WeixinUtil.httpRequest(tokenUrl, "GET", null);

            JSONObject jsonObject = JSON.parseObject(response);
            log.info("请求到的Access Token:{}", jsonObject.toJSONString());

            if (null != jsonObject) {
                try {
                    String WebAccessToken = jsonObject.getString("access_token");
                    String openId = jsonObject.getString("openid");
                    log.info("获取access_token成功!");
                    log.info("WebAccessToken:{} , openId:{}", WebAccessToken, openId);

                    // 3. 使用获取到的 Access_token 和 openid 拉取用户信息
                    String userMessageUrl = UserInfoUtil.getUserMessage(WebAccessToken, openId);
                    log.info("第三步:获取用户信息的URL:{}", userMessageUrl);

                    // 通过https方式请求获得用户信息响应
                    String userMessageResponse = WeixinUtil.httpRequest(userMessageUrl, "GET", null);

                    authorizedUsersBean = convertUserInfo(userMessageResponse);
                } catch (JSONException e) {
                    log.error("获取Web Access Token失败");
                }
            }
        }

        return authorizedUsersBean;
    }

    /**
     * 组装用户授权信息<br>
     *
     * @param userMessageResponse 授权用户响应数据
     * @return authorizedUsersBean {@link AuthorizedUsersBean 授权用户对象}
     * @since 2017/10/10
     * @throws JSONException json转换异常
     */
    private AuthorizedUsersBean convertUserInfo(String userMessageResponse) {
        AuthorizedUsersBean authorizedUsersBean = new AuthorizedUsersBean();

        JSONObject userMessageJsonObject = JSON.parseObject(userMessageResponse);

        log.info("用户信息:{}", userMessageJsonObject.toJSONString());

        if (userMessageJsonObject != null) {
            try {
                //用户昵称
                String nickName = userMessageJsonObject.getString("nickname");
                //用户性别
                String sex = userMessageJsonObject.getString("sex");
                sex = (sex.equals("1")) ? "男" : "女";
                //用户唯一标识
                String openid = userMessageJsonObject.getString("openid");

                authorizedUsersBean.setName(nickName);
                authorizedUsersBean.setSex(sex);
                authorizedUsersBean.setOpenId(openid);

                log.info("用户昵称:{}", nickName, "用户性别:{}", sex, "OpenId:{}", openid);
            } catch (JSONException e) {
                log.error("获取用户信息失败");
            }
        }

        return authorizedUsersBean;
    }

}
