package com.yingfeng.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableCaching
@EnableScheduling
@EnableTransactionManagement
@ComponentScan(basePackages = "com.yingfeng")
@SpringBootApplication
@MapperScan(basePackages = "com.yingfeng.cms.modules.**.mapper")
@ServletComponentScan
@EnableRedisHttpSession
public class PlatformCmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlatformCmsApplication.class, args);
	}
}
