# milk_auth

#### 介绍
权限管理是所有后台系统都会涉及的一个重要组成部分，而权限管理的核心流程是相似的，如果每个后台单独开发一套权限管理系统，就是重复造轮子，是人力的极大浪费，本项目就是针对这个问题，提供了一套通用的权限解决方案。

#### 软件架构
项目服务器端架构：SpringBoot + MyBatisPlus + SpringSecurity
前端架构：Node.js + Npm + Vue + ElementUI + Axios

#### 核心技术
 基础框架：SpringBoot                              
 数据缓存：Redis                                   
 数据库：Mysql                                    
 权限控制：SpringSecurity                          
 全局日志记录：AOP                                   
 前端模板：vue-admin-template                      
 前端技术：Node.js + Npm + Vue + ElementUI + Axios 


#### 安装教程

1.  xxxx
2.  xxxx
3.  xxxx

#### 使用说明

1.  后端权限控制  在方法上加注解
```
@PreAuthorize("hasAnyAuthority('bnt.sysMenu.add')")
```
2.  前端权限控制  在按钮加
```:disabled="$hasBP('bnt.sysMenu.update') === false"```
3.  记录日志 在方法上加注解
``` @Log(title = "角色模块",businessType = BusinessType.ASSGIN,isSaveRequestData = true,isSaveResponseData = true)
```
4. 添加缓存
```@CacheAuth(name="操作模块",overtime=1*60*60*1000")
默认缓存一小时```


#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
