package com.yingfeng.cms.modules.user.service;

import com.yingfeng.cms.modules.user.domain.UserBean;

import java.util.List;

/**
 * 用户业务逻辑接口<br/>
 *
 * @author pengc
 * @see com.yingfeng.cms.modules.user.service
 * @since 2017/9/4
 */
public interface UserService {

    UserBean findById(String id);

    int saveUser(UserBean userBean);

    List<UserBean> findUser();

    boolean deleteUserById(String Id);

    List<UserBean> findUserPage(UserBean userBean);

}
