package com.example.login.Controller;


import com.example.login.aspect.MyPermission;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorityController {

    @MyPermission(token = "token",required = true )
    @RequestMapping(value = "/user_authority",method = RequestMethod.POST)
    public boolean user_authority( @RequestParam(value = "token") String token ,
                                   @RequestParam(value = "action_id") String action_id){
        if("admin".equals(token)){
            return true;
        }else {
            return false;
        }
     }

 }