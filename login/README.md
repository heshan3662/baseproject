# service-login 登录管理

##实体划分
 **用户**
 **资源**
 **权限**
 
 ##功能划分
 **用户管理**
 用户注册、登录、注销
  
 **模块管理**
 模块连接展示
 
 **方法管理**
 调用后台方法
 
 **权限管理**
 对用户、模块、后台方法进行权限划分
 
 ##业务员逻辑
 单独开辟一个认证服务或者公共存储的方式实现。 
 公共存储为redis、cache,cache不适合分布式服务。
 如果redis1