package com.yingfeng.cms.config;


import com.yingfeng.cms.PlatformCmsApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 配置jar转war<br/>
 *
 * @author pengc
 * @see com.yingfeng.cms.config
 * @since 2017/9/6
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PlatformCmsApplication.class);
    }

}