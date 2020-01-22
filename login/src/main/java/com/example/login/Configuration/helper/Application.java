package com.example.login.Configuration.helper;

import com.example.login.Configuration.helper.service.InitService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
@Service
public final class Application implements InitializingBean, ServletContextAware {
    @Autowired
    private InitService initService ;


    public   static boolean ready  ;

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        ready =initService.initService();
    }
}
