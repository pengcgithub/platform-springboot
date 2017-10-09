package com.yingfeng.cms.modules.user.mapper;

import com.yingfeng.cms.config.MyMapper;
import com.yingfeng.cms.modules.user.domain.UserBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * UserMapper接口文件<br/>
 *
 * @author pengc
 * @date 2017/6/30
 * @see com.yingfeng.cms.modules.user.mapper
 */
public interface UserMapper extends MyMapper<UserBean> {

    @Select("select * from t_user where id = #{id}")
    UserBean findById(@Param("id") String id);

    List<UserBean> queryUserAll();

}
