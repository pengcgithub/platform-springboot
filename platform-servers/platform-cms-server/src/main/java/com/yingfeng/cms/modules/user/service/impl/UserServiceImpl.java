package com.yingfeng.cms.modules.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.yingfeng.cms.modules.user.domain.UserBean;
import com.yingfeng.cms.modules.user.mapper.UserMapper;
import com.yingfeng.cms.modules.user.service.UserService;
import com.yingfeng.commons.web.exception.SimpleException;
import com.yingfeng.commons.web.support.session.DefaultSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 用户业务逻辑实现<br/>
 *
 * @author pengc
 * @see com.yingfeng.cms.modules.user.service.impl
 * @since 2017/9/4
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DefaultSessionService sessionService;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<Object,Object> redisTemplate;

    @Transactional(readOnly = true)
    @Override
    public UserBean findById(String id) {
        ValueOperations<Object, Object> ops = redisTemplate.opsForValue();
        ops.set("id", id);
        log.info("findById>>id>>"+ops.get("id"));
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int saveUser(UserBean userBean) {
        return userMapper.insert(userBean);
    }

    @Override
    public List<UserBean> findUser() {
        return userMapper.selectAll();
    }

    @Override
    public boolean deleteUserById(String Id) {
        try {
            userMapper.deleteByPrimaryKey(Id);
        } catch (Exception e) {
            throw new SimpleException("删除异常", null);
        }
        return true;
    }

    @Override
    public List<UserBean> findUserPage(UserBean userBean) {
        if (userBean.getPage() != null && userBean.getRows() != null) {
            PageHelper.startPage(userBean.getPage(), userBean.getRows());
        }
        return userMapper.queryUserAll();
    }
}
