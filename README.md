# 权限设计

### 即将开发
1、缓存经常使用的数据,sa-token的数据持久到redis(导入依赖有问题)
2、一些优化

#### 介绍
权限管理是所有后台系统都会涉及的一个重要组成部分，而权限管理的核心流程是相似的，如果每个后台单独开发一套权限管理系统，就是重复造轮子，是人力的极大浪费，本项目就是针对这个问题，提供了一套通用的权限解决方案。
项目提供了很多的工具类，对其的开发可直接copy

#### 软件架构
项目服务器端架构：SpringBoot + MyBatisPlus + sa-token + Redis
前端架构：Node.js + Npm + Vue + ElementUI + Axios

#### 核心技术
 基础框架：SpringBoot
 开发文档：Swagger UI
 数据缓存：Redis                                   
 数据库：Mysql                                    
 权限控制：Sa-token                          
 全局日志记录：AOP                                   
 前端模板：vue-admin-template                      
 前端技术：Node.js + Npm + Vue + ElementUI + Axios 


#### 安装教程

1.  修改一点
2.  xxxx
3.  xxxx

#### 使用说明

1、后端权限控制  在方法上加注解sa-token自带的注解
``@CheckPermission("btn.menu.add")``
2、前端权限控制  在按钮加
``:disabled="$hasBP('bnt.sysMenu.update') === false"``
3、记录日志 在方法上加注解
`` @Log(title = "角色模块",businessType = BusinessType.ASSGIN,isSaveRequestData = true,isSaveResponseData = true)``
4、记录登录日志
``@LoginLog``
