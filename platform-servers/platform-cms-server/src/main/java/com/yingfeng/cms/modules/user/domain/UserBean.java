package com.yingfeng.cms.modules.user.domain;

import com.yingfeng.cms.commons.domain.BaseBean;
import lombok.Data;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 用户对象<br/>
 *
 * @author pengc
 * @see com.yingfeng.cms.modules.user.domain
 * @since 2017/9/4
 */
@Data
@Table(name = "t_user")
public class UserBean extends BaseBean implements Serializable {

    private String name;

    @Size(min = 3, max = 20, message="用户名长度只能在3-20之间")
    private String password;


}
