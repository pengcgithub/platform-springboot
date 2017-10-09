package com.yingfeng.cms.modules.user.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.yingfeng.cms.modules.user.domain.UserBean;
import com.yingfeng.cms.modules.user.service.UserService;
import com.yingfeng.commons.web.constants.ExceptionCodeInfo;
import com.yingfeng.commons.web.exception.SimpleException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 用户<br/>
 *
 * @author pengc
 * @see com.yingfeng.cms.modules.user.controller
 * @since 2017/9/4
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/save")
    public int saveUser(@RequestBody @Validated UserBean userBean) {
        return userService.saveUser(userBean);
    }

    @GetMapping("/user/by/{id}")
    public UserBean queryUserById(@PathVariable String id) {
        return userService.findById(id);
    }

    @GetMapping("/user/all")
    public List<UserBean> queryUserAll() {
        return userService.findUser();
    }

    @GetMapping("/user/cors")
    public String corsTest() {
        String jsonstr = JSON.toJSONString(userService.findUser());
        return jsonstr;
    }

    @DeleteMapping("/user/delete/by/{id}")
    public boolean deleteUserById(@PathVariable String id) {
        return userService.deleteUserById(id);
    }

    @GetMapping("/user/exception")
    public String queryUserException() {
        throw new SimpleException("查询用户异常", ExceptionCodeInfo.NETWORK_ERR);
    }

    @PostMapping("/user/page")
    public PageInfo<UserBean> queryUserPage(@RequestBody UserBean userBean) {
        List<UserBean> userBeanList = userService.findUserPage(userBean);
        return new PageInfo<>(userBeanList);
    }

    @ApiOperation(value = "session fail 验证", notes = "session失效异常")
    @GetMapping("/session/fail")
    public void sessionFail() {
        throw new SimpleException("session失效异常", ExceptionCodeInfo.NETWORK_ERR);
    }


}
