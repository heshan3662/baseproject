package com.example.login.aspect;

import java.lang.annotation.*;

@Documented //作用域
@Inherited //可继承
@Target(ElementType.METHOD)//标明自定义注解可作用的地方，指方法
@Retention(RetentionPolicy.RUNTIME) //存活阶段，RUNRIME:存在运行期，还有jvm，class文件级别
public @interface MyPermission {
    String token() default "token";

    //是否需要数据权限，默认为true
    boolean required() default true;
}
