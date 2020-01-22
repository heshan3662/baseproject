package com.example.feign.annotation;

import com.alibaba.fastjson.JSONObject;
import com.example.feign.Interface.UserLoginService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;

@Component  //交由Spring容器管理
@Aspect     //标注增强处理类（切面类）
public class AuthentificationAspect implements Ordered {
    @Autowired
    UserLoginService userLoginService ;


    /**
     * 自定义切点位置，针对不同切点，方法上的@Around()可以这样写ex：@Around(value = "permissionTest() && args(..)")
     */

    @Pointcut("execution(* com.example.feign.Controller..*(..))")
    public void permissionTest() {

    }

    @Around("permissionTest()")
    public Object doPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();

        Method method = signature.getMethod();
        method.getName();
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

        if(!userLoginService.user_authority(token,"")){
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
}