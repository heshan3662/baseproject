package com.example.login.aspect;

import com.alibaba.fastjson.JSONObject;
import com.example.login.service.AuthorityService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

@Component  //交由Spring容器管理
@Aspect     //标注增强处理类（切面类）
public class AuthentificationAspect implements Ordered {
    @Autowired
    AuthorityService authorityService;


    /**
     * 自定义切点位置，针对不同切点，方法上的@Around()可以这样写ex：@Around(value = "permissionTest() && args(..)")
     */

    @Pointcut("execution(* com.example.login.Controller..*(..))")
    public void permissionTest() {

    }

    @Around("permissionTest()")
    public Object doPermission(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature)joinPoint.getSignature();

        Method method = signature.getMethod();
        String methodName = signature.getMethod().getName();
        System.out.println("方法名：" + methodName);


        Object[] args = joinPoint.getArgs();
        if(null == args || args.length == 0){
            return "参数为空";
        }
        MyPermission myPermission = method.getAnnotation(MyPermission.class);
        if(myPermission == null){
            return joinPoint.proceed();
        }

        //判断是否需要数据权限
        boolean required = myPermission.required();
        if (!required) {
            return joinPoint.proceed(); //调用目标方法
        }
        JSONObject json = JSONObject.parseObject(String.valueOf(args[0]));
        String token = json.getString(myPermission.token());
        HttpServletRequest request = currentRequest();
        if (Objects.isNull(request)) {
            return  "权限验证未通过";
        }
        String token_value = request.getHeader(token);
        if(!authorityService.user_authority(token,methodName)){
            return "权限验证未通过";
        }

        return joinPoint.proceed(); //调用目标方法
    }

    /**
     * oap执行顺序
     * 两种方法实现
     * 1.实现Ordered接口
     * 2.直接在类前添加@Order(0)
      * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }

    private HttpServletRequest currentRequest() {
        // Use getRequestAttributes because of its return null if none bound
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Optional.ofNullable(servletRequestAttributes).map(ServletRequestAttributes::getRequest).orElse(null);
    }

}