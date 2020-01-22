package com.example.login.Controller;


import com.example.login.Configuration.helper.Application;
import com.example.login.Utils.ResponseUtils;
import com.example.login.entity.GdUser;
import com.example.login.entity.responsetype.ApiRes;
import com.example.login.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "login", description = "登录接口")
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @ApiOperation(value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="username",value="用户名",dataType="string", paramType = "query",required = true,example="",defaultValue ="admin" ),
            @ApiImplicitParam(name="password",value="密码",dataType="string", paramType = "query",required = true,defaultValue ="admin"),
            @ApiImplicitParam(name="type",value="登录方式",dataType="string", paramType = "query",required = false, allowableValues = "0,1" ,defaultValue ="0" ),
    })
    @ResponseBody
    @RequestMapping(value = "/login_in",method = RequestMethod.POST)
    public ApiRes<GdUser> login_in (@RequestParam(value = "username", defaultValue = "") String username,
                                    @RequestParam(value = "password", defaultValue = "") String password,
                                    @RequestParam(value = "type", defaultValue = "") String type ){
        ApiRes< GdUser> apiRes = new  ApiRes< GdUser>();
        if(Application.ready){
            Application.ready = false ;
        }
        String  res = loginService.login_in(   username,password,type);
        apiRes = ResponseUtils.transResponseForObject(apiRes,res,GdUser.class);
        return apiRes ;
    }


    @ApiOperation(value = "获取权限点")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userid",value="用户id",dataType="string", paramType = "query",required = true,example="",defaultValue ="admin" ),
     })
    @ResponseBody
    @RequestMapping(value = "/get_authority",method = RequestMethod.POST)
    public ApiRes<GdUser> get_authority (@RequestParam(value = "userid", defaultValue = "") String userid ){
        ApiRes< GdUser> apiRes = new  ApiRes< GdUser>();

        String  res = loginService.get_authority(   userid );
        apiRes = ResponseUtils.transResponseForObject(apiRes,res,GdUser.class);
        return apiRes ;

    }


    @ApiOperation(value = "登出")
    @ApiImplicitParams({
     })
    @ResponseBody
    @RequestMapping(value = "/login_out",method = RequestMethod.GET)
    public ApiRes login_out ( HttpServletRequest request){
        ApiRes< GdUser> apiRes = new  ApiRes< GdUser>();
        String token = request.getHeader("token");
        boolean b= loginService.login_out(   token);
         if(b){
            apiRes.set_result_code(0);
            apiRes.set_result_message("登出成功");
            return apiRes ;
        }else {
            apiRes.set_result_code(-1);
            apiRes.set_result_message("登出失败");
            return apiRes ;
        }


    }
 }