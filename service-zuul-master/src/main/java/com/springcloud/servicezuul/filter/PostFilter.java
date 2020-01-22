package com.springcloud.servicezuul.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.springcloud.servicezuul.responsetype.DataRes;
import com.springcloud.servicezuul.responsetype.ResultCode;
import com.springcloud.servicezuul.service.CacheService;
import com.springcloud.servicezuul.utils.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.nio.charset.Charset;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

/**
 * 登录过滤器
 *记得类上加Component注解
 */
@Component
public class PostFilter extends ZuulFilter {

    @Autowired
    CacheService cacheService;



    /**
     * 过滤器类型，前置过滤器
     */
    @Override
    public String filterType() {
        return POST_TYPE ;
    }

    /**
     * 过滤器顺序，越小越先执行
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 过滤器是否生效
     * 返回true代表需要权限校验，false代表不需要用户校验即可访问
     */
    @Override
    public boolean shouldFilter() {

        //共享RequestContext，上下文对象
        RequestContext requestContext = getCurrentContext();
        if(!"200".equals(requestContext.getResponseStatusCode())){
            return false;
        }
        HttpServletRequest request = requestContext.getRequest();

        System.out.println(request.getRequestURI());
        //需要权限校验URL
//        if(request.getRequestURI().indexOf("/login/login_in")>= 0 ){
//            return true;
//        }

        return false;
    }

    /**
     * 业务逻辑
     * 只有上面返回true的时候，才会进入到该方法
     */
    @Override
    public Object run() throws ZuulException {
        try {
            RequestContext context = getCurrentContext();

            HttpServletRequest request = context.getRequest();
            String username = request.getParameter("username");
            InputStream stream = context.getResponseDataStream();
            String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
            if("".equals(body)){
                return  null ;
            }
            boolean b= cacheService.SetCacheForUserInfo(body);
            if(b){
                DataRes dataRes = new DataRes(request.getRequestURI(), ResultCode.RESULT_SUCCESS, "成功");
            }else {
                DataRes dataRes = new DataRes(request.getRequestURI(), ResultCode.RESULT_ERROR, "失败");
            }
            context.setResponseBody(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}