package com.example.login.Controller;


import com.example.login.Utils.ResponseUtils;
import com.example.login.entity.GdUser;
import com.example.login.entity.responsetype.ApiRes;
import com.example.login.service.CacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "user", description = "用户接口")
@RequestMapping("/user")
public class UserController {

    @Autowired
    CacheService cacheService;

    @ApiOperation(value = "获取人员信息")
    @ResponseBody
    @RequestMapping(value = "/get_userinfo",method = RequestMethod.POST)
    public ApiRes<GdUser> get_userinfo(HttpServletRequest request   ){
        ApiRes< GdUser> apiRes = new  ApiRes< GdUser>();
        String token = request.getHeader("token");
        GdUser gdUser =  cacheService.GetCacheForUserInfoByToken(   token);
        if(gdUser != null){
            apiRes.set_data(gdUser);
            apiRes.set_result_code(0);
            return apiRes ;
        }else {
            apiRes.set_result_code(-1);
            apiRes.set_result_message("未检测到人员登录信息");
            return apiRes ;
        }
     }

 }