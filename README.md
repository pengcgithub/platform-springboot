# platform-springBoot项目说明

## 简介

platform-springBoot以CMS项目为基准构建的，为了前后端分离提供解决方案。


## 模块介绍及目录结构 

##### platform-dependencies |   依赖包，版本控制

##### platform-sdk  |   基础
* platform-web    |   SpringWeb相关

##### platform-modules  |   独立模块
* platform-utils      |   基础工具类

##### platform-servers  |  服务
* platform-cms-server   |  消息发布系统

##### docs              |  platform文档

##### sql               |  platform 依赖sql


## 技术说明

- springBoot 1.4.1
- swagger
- redis [安装](https://github.com/pengcgithub/java-development-environment/blob/master/redis/redis%E5%AE%89%E8%A3%85.md)
- mybatis、pageHelper、mapper
- druid
- lombok
- 统一异常管理
- spring-data-redis整合
- logback记录日志
- FastDFS [安装](https://github.com/pengcgithub/java-development-environment/blob/master/fastDFS/fastdfs.md)
- mysql [安装](https://github.com/pengcgithub/java-development-environment/blob/master/mysql/mysql%E5%AE%89%E8%A3%85.md)

## 修改配置运行

配置文件如下：`application.properties`、`application-dev.properties`、`application-test.properties`、`application-prod.properties` 分别表示`主配置`、`开发环境`、`测试环境`、`生产环境`四个，如果是公共的配置，那么可以在`主配置`中添加（例如：pageHelper、mapper），如果是涉及不同环境的配置，则需要根据不同的环境进行配置（例如：mysql、redis）。

- redis配置

![redis](https://i.imgur.com/hwnAl0Z.png)
    
- mysql数据库连接配置

![mysql](https://i.imgur.com/4m4DQNR.png)

- fastDFS配置

![fastDFS](https://i.imgur.com/V7rCby9.png)

## 联系(Contact)

- [pengcheng3211@163.com](https://github.com/pengcgithub)




