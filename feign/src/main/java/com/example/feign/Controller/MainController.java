package com.example.feign.Controller;

import com.example.feign.annotation.MyPermission;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @MyPermission(token = "token",required = true )
    public String permissionTest01(  String token){
        System.out.println("已通过权限");
        return "success";
    }
    @RequestMapping("test02")
    public String permissionTest02(@RequestBody String name){
        System.out.println("未加权限");
        return "success";
    }
}