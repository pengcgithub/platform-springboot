package com.yingfeng.cms.modules.weixin.domain;

import lombok.Data;

/**
 * 授权用户对象<br/>
 *
 * @author pengc
 * @see com.yingfeng.cms.modules.weixin.domain
 * @since 2017/10/10
 */
@Data
public class AuthorizedUsersBean {

    private String name;

    private String sex;

    private String openId;

    private String province;

    private String city;

    private String country;

    private String headImgUrl;

}
