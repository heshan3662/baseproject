# service-zuul 网关服务

## service-zuul
 **zuul配置 yml**
 
 routes:
    login:
      path: /login-service/**  
      serviceId: service-login
      
**调用方式**      
    http://localhost:8766/login-service/login/login_out


## Hystrix
    http://localhost:8766/hystrix
## swagger 、swagger-ui.html 
    http://localhost:8766/swagger-ui.html#/
    网上很多新增的实现类SwaggerConfig implements SwaggerResourcesProvider不起作用，原因是有多个类实现这个接口。
    @Primary无效，最后将此类写在启动app中，解决此问题 