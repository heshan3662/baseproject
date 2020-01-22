package com.springcloud.servicezuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.springcloud.servicezuul.configuration.helper.Application;
import com.springcloud.servicezuul.responsetype.DataRes;
import com.springcloud.servicezuul.responsetype.ResultCode;
import com.springcloud.servicezuul.service.CacheService;
import com.springcloud.servicezuul.utils.RedisUtils;
import com.sun.xml.internal.bind.v2.TODO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 登录过滤器
 *记得类上加Component注解
 */
@Component
public class PreFilter extends ZuulFilter {


    @Autowired
    CacheService cacheService;


    /**
     * 过滤器类型，前置过滤器
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 过滤器顺序，越小越先执行
     */
    @Override
    public int filterOrder() {
        return 4;
    }

    /**
     * 过滤器是否生效
     * 返回true代表需要权限校验，false代表不需要用户校验即可访问
     */
    @Override
    public boolean shouldFilter() {
        //共享RequestContext，上下文对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        System.out.println(request.getRequestURI());
        // swagger 的接口api
        if(request.getRequestURI().indexOf("/v2/api-docs")>= 0 ){
            return false;
        }
        if(request.getRequestURI().indexOf("/login/login_in")>= 0 ){
            return false;
        }
        return true;
    }

    /**
     * 业务逻辑
     * 只有上面返回true的时候，才会进入到该方法
     */
    @Override
    public Object run() throws ZuulException {

        //JWT
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(!Application.ready){
            // 过滤该请求，不对其进行路由
            requestContext.setSendZuulResponse(false);
            DataRes dataRes = new DataRes(request.getRequestURI(), new Integer(-100), "未安装");
            //返回错误代码
            try {
                (RequestContext.getCurrentContext()).getResponse().getWriter().write(dataRes.toString());
            } catch (Exception e) {
            }
        }
        //token对象, 在请求头传递过来
        String token = request.getHeader("token");
        if (StringUtils.isBlank((token))) {
            token = request.getParameter("token");
        }
        System.out.println("页面传来的token值为：" + token);
        //登录校验逻辑  如果token为null，则直接返回客户端，而不进行下一步接口调用

        if (StringUtils.isBlank(token)) {
            // 过滤该请求，不对其进行路由
            requestContext.setSendZuulResponse(false);
            DataRes dataRes = new DataRes(request.getRequestURI(), new Integer(-3), "未登陆");
            //返回错误代码
            try {
                (RequestContext.getCurrentContext()).getResponse().getWriter().write(dataRes.toString());
            } catch (Exception e) {
            }
        }

        return null;
    }
}