package com.example.login.Controller;


import com.alibaba.fastjson.JSON;
import com.example.login.Configuration.helper.Application;
import com.example.login.Utils.ResponseUtils;
import com.example.login.aspect.MyPermission;
import com.example.login.entity.User;
import com.example.login.entity.responsetype.ApiRes;
import com.example.login.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.List;

@RestController
@Api(tags = "login", description = "用户登录接口")
@RequestMapping("/user_login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @ApiOperation(value = "注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "string", paramType = "query", required = true, example = "", defaultValue = "admin"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "string", paramType = "query", required = true, defaultValue = "123456"),
            @ApiImplicitParam(name = "repassword", value = "密码确认", dataType = "string", paramType = "query", required = true, defaultValue = "123456"),
            @ApiImplicitParam(name = "fullname", value = "姓名", dataType = "string", paramType = "query", required = false, defaultValue = "张三"),
            @ApiImplicitParam(name = "phone", value = "电话", dataType = "string", paramType = "query", required = false, defaultValue = "18900000000"),
            @ApiImplicitParam(name = "email", value = "邮箱", dataType = "string", paramType = "query", required = false, defaultValue = "123@xx.com"),
            @ApiImplicitParam(name = "comid", value = "公司id", dataType = "string", paramType = "query", required = false, defaultValue = "1-001"),
            @ApiImplicitParam(name = "deptid", value = "部门id", dataType = "string", paramType = "query", required = false, defaultValue = "1-001"),
            @ApiImplicitParam(name = "roleid", value = "角色id", dataType = "string", paramType = "query", required = false, defaultValue = "2-002"),
    })
    @ResponseBody
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    public ApiRes<User> regist(@RequestParam(value = "username", defaultValue = "") String username,
                               @RequestParam(value = "password", defaultValue = "") String password,
                               @RequestParam(value = "repassword", defaultValue = "") String repassword,
                               @RequestParam(value = "fullname", required = false) String fullname,
                               @RequestParam(value = "phone", required = false) String phone,
                               @RequestParam(value = "email", required = false) String email,
                               @RequestParam(value = "comid", required = false) String comid,
                               @RequestParam(value = "deptid", required = false) String deptid,
                               @RequestParam(value = "roleid", required = false) String roleid

    ) {
        ApiRes<User> apiRes = new ApiRes<User>();
        if (password.length() < 6) {
            apiRes.set_result_code(-1);
            apiRes.set_result_message("密码不能小于6位！");
            return apiRes;
        }

        if (!password.equals(repassword)) {
            apiRes.set_result_code(-1);
            apiRes.set_result_message("两次密码不相同！");
            return apiRes;
        }
        if (!StringUtils.isBlank(phone)) {
            if (phone.length() != 11) {
                apiRes.set_result_code(-1);
                apiRes.set_result_message("手机号必须等于11位！");
                return apiRes;
            }
        }
        if (!StringUtils.isBlank(email)) {
            String fomat = "^\\w+((-\\w+)|(\\.\\w+))*@\\w+(\\.\\w{2,3}){1,3}$";
            if (!email.matches(fomat)) {
                apiRes.set_result_code(-1);
                apiRes.set_result_message("邮箱格式不合法！");
                return apiRes;
            }
        }
        if (loginService.ckeck_username_exist(username)) {
            apiRes.set_result_code(-1);
            apiRes.set_result_message("账号已存在！");
            return apiRes;
        }
        String createuser = "";
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");

        if (!StringUtils.isBlank(token)) {
            User gu = loginService.get_cur_user_info(token);
            createuser = gu.getUserId();
        }
        byte[] bytes = password.getBytes();
        //Base64 加密
        String encoded = Base64.getEncoder().encodeToString(bytes);
        int res = loginService.regist(username, encoded, fullname, phone, email, createuser, deptid, roleid);
        if (res <= 0) {
            apiRes.set_result_code(-1);
            apiRes.set_result_message("注册失败，请稍后再试！");
            return apiRes;
        } else {
            apiRes.set_result_code(0);
            apiRes.set_result_message("注册成功！");
            return apiRes;
        }

    }

    @ApiOperation(value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "string", paramType = "query", required = true, example = "", defaultValue = "admin"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "string", paramType = "query", required = true, defaultValue = "123456"),
            @ApiImplicitParam(name = "type", value = "登录方式", dataType = "string", paramType = "query", required = false, allowableValues = "0,1,2", defaultValue = "0"),
    })
    @ResponseBody
    @RequestMapping(value = "/login_in", method = RequestMethod.POST)
    public ApiRes<User> login_in(@RequestParam(value = "username", defaultValue = "") String username,
                                 @RequestParam(value = "password", defaultValue = "") String password,
                                 @RequestParam(value = "type", defaultValue = "") String type) {
        ApiRes<User> apiRes = new ApiRes<User>();
        byte[] bytes = password.getBytes();
        //Base64 加密
        String encoded = Base64.getEncoder().encodeToString(bytes);
        String res = loginService.login_in(username, encoded, type);
        apiRes = ResponseUtils.transResponseForObject(apiRes, res, User.class);
        return apiRes;
    }

    @ApiOperation(value = "注销人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "注销用户名", dataType = "string", paramType = "query", required = true, example = "", defaultValue = ""),
    })
    @ResponseBody
    @RequestMapping(value = "/cancellation_user", method = RequestMethod.POST)
    public ApiRes cancellation_user(@RequestParam(value = "username", defaultValue = "") String username) {
        ApiRes<User> apiRes = new ApiRes<User>();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User user = loginService.get_cur_user_info(token);
        String modifiedUser = user.getUserId();
        boolean b = loginService.cancellation_user(modifiedUser, username);
        if (b) {
            apiRes.set_result_code(0);
            apiRes.set_result_message("注销成功");
        } else {
            apiRes.set_result_code(-1);
            apiRes.set_result_message("注销失败");
        }
        return apiRes;
    }

    @ApiOperation(value = "激活人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "激活用户名", dataType = "string", paramType = "query", required = true, example = "", defaultValue = ""),
    })
    @ResponseBody
    @RequestMapping(value = "/activated_user", method = RequestMethod.POST)
    public ApiRes activated_user(@RequestParam(value = "username", defaultValue = "") String username) {
        ApiRes<User> apiRes = new ApiRes<User>();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User gu = loginService.get_cur_user_info(token);
        String modifiedUser = gu.getUserId();
        loginService.activated_user(modifiedUser, username);
        apiRes.set_result_code(0);
        apiRes.set_result_message("");
        return apiRes;
    }

    @ApiOperation(value = "更改用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "string", paramType = "query", required = true, example = "", defaultValue = "admin"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "string", paramType = "query", required = true, defaultValue = "123456"),
            @ApiImplicitParam(name = "fullname", value = "姓名", dataType = "string", paramType = "query", required = false, defaultValue = "张三"),
            @ApiImplicitParam(name = "phone", value = "电话", dataType = "string", paramType = "query", required = false, defaultValue = "18900000000"),
            @ApiImplicitParam(name = "email", value = "邮箱", dataType = "string", paramType = "query", required = false, defaultValue = "123@xx.com"),
            @ApiImplicitParam(name = "deptid", value = "部门id", dataType = "string", paramType = "query", required = false, defaultValue = "1-001"),
            @ApiImplicitParam(name = "roleid", value = "角色id", dataType = "string", paramType = "query", required = false, defaultValue = "2-002"),
    })
    @ResponseBody
    @RequestMapping(value = "/modify_user", method = RequestMethod.POST)
    public ApiRes modify_user(@RequestParam(value = "username", defaultValue = "") String username,
                              @RequestParam(value = "password", defaultValue = "") String password,
                              @RequestParam(value = "fullname", required = false) String fullname,
                              @RequestParam(value = "phone", required = false) String phone,
                              @RequestParam(value = "email", required = false) String email,
                              @RequestParam(value = "deptid", required = false) String deptid,
                              @RequestParam(value = "roleid", required = false) String roleid

    ) {
        ApiRes<User> apiRes = new ApiRes<User>();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User gu = loginService.get_cur_user_info(token);
        String modifiedUser = gu.getUserId();
        loginService.update_user(username,
                password,
                fullname,
                phone,
                email,
                deptid,
                roleid,
                null,
                modifiedUser);
        apiRes.set_result_code(0);
        apiRes.set_result_message("");
        return apiRes;
    }

    @ApiOperation(value = "获取当前人员信息")
    @ApiImplicitParams({
    })
    @ResponseBody
    @RequestMapping(value = "/get_cur_user_info", method = RequestMethod.POST)
    public ApiRes<User> get_cur_user_info() {
        ApiRes<User> apiRes = new ApiRes<User>();
        if (Application.ready) {
            Application.ready = false;
        }
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User gu = loginService.get_cur_user_info(token);
        apiRes = ResponseUtils.transResponseForObject(apiRes, JSON.toJSONString(gu), User.class);
        return apiRes;
    }

    @ApiOperation(value = "获取人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "string", paramType = "query", required = false, example = "", defaultValue = "admin"),
            @ApiImplicitParam(name = "fullname", value = "姓名", dataType = "string", paramType = "query", required = false, defaultValue = "张三"),
            @ApiImplicitParam(name = "phone", value = "电话", dataType = "string", paramType = "query", required = false, defaultValue = "18900000000"),
            @ApiImplicitParam(name = "email", value = "邮箱", dataType = "string", paramType = "query", required = false, defaultValue = "123@xx.com"),
            @ApiImplicitParam(name = "deptid", value = "部门id", dataType = "string", paramType = "query", required = false, defaultValue = "1-001"),
            @ApiImplicitParam(name = "roleid", value = "角色id", dataType = "string", paramType = "query", required = false, defaultValue = "2-002"),
            @ApiImplicitParam(name = "isdisabled", value = "作废标识", dataType = "string", paramType = "query", required = false,allowableValues = "F,T", defaultValue = "F"),
            @ApiImplicitParam(name = "start", value = "起始位", dataType = "string", paramType = "query", required = false, defaultValue = "0"),
            @ApiImplicitParam(name = "limit", value = "终止位", dataType = "string", paramType = "query", required = false, defaultValue = "10"),
    })
    @ResponseBody
    @RequestMapping(value = "/get_user_info", method = RequestMethod.POST)
    @MyPermission(token = "username",required = true )
    public ApiRes<List<User>> get_user_info(@RequestParam(value = "username", defaultValue = "") String username,
                                            @RequestParam(value = "fullname", defaultValue = "") String fullname,
                                            @RequestParam(value = "phone", defaultValue = "") String phone,
                                            @RequestParam(value = "email", defaultValue = "") String email,
                                            @RequestParam(value = "deptid", defaultValue = "") String deptid,
                                            @RequestParam(value = "roleid", defaultValue = "") String roleid,
                                            @RequestParam(value = "isdisabled", defaultValue = "F") String isdisabled,
                                            @RequestParam(value = "start", defaultValue = "0") int start,
                                            @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        ApiRes<List<User>> apiRes = new ApiRes<List<User>>();
        String extentsql = "";
        if (!org.springframework.util.StringUtils.isEmpty(username)) {
            extentsql += " and LOGIN_NAME = '" + username + "'";
        }
        if (!org.springframework.util.StringUtils.isEmpty(fullname)) {
            extentsql += " and FULL_NAME = '" + fullname + "'";
        }
        if (!org.springframework.util.StringUtils.isEmpty(phone)) {
            extentsql += " and PHONE = '" + phone + "'";
        }
        if (!org.springframework.util.StringUtils.isEmpty(email)) {
            extentsql += " and EMAIL = '" + email + "'";
        }
        if (!org.springframework.util.StringUtils.isEmpty(deptid)) {
            extentsql += " and DEPT_ID = '" + deptid + "'";
        }
        if (!org.springframework.util.StringUtils.isEmpty(roleid)) {
            extentsql += " and  ROLE_ID = '" + roleid + "'";
        }
        if (!org.springframework.util.StringUtils.isEmpty(isdisabled)) {
            extentsql += " and  IS_DISABLED = '" + isdisabled + "'";
        }
        extentsql += " limit " + start + "," + limit;
        List list = loginService.get_user_info(extentsql);
        Long total = loginService.get_user_info_count(extentsql);
        apiRes = ResponseUtils.transResponseForObject(apiRes, JSON.toJSONString(list), List.class);
        apiRes.set_total(total);
        return apiRes;
    }


    @ApiOperation(value = "获取权限点")
    @ApiImplicitParams({
    })
    @ResponseBody
    @RequestMapping(value = "/get_authority", method = RequestMethod.POST)
    public ApiRes<User> get_authority() {
        ApiRes<User> apiRes = new ApiRes<User>();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User gu = loginService.get_cur_user_info(token);
        String userid = gu.getUserId();
        String res = loginService.get_authority(userid);
        apiRes = ResponseUtils.transResponseForObject(apiRes, res, User.class);
        return apiRes;
    }

    @ApiOperation(value = "登出")
    @ApiImplicitParams({
    })
    @ResponseBody
    @RequestMapping(value = "/login_out", method = RequestMethod.GET)
    public ApiRes login_out(HttpServletRequest request) {
        ApiRes apiRes = new ApiRes();
        String token = request.getHeader("token");
        boolean b = loginService.login_out(token);
        if (b) {
            apiRes.set_result_code(0);
            apiRes.set_result_message("登出成功");
            return apiRes;
        } else {
            apiRes.set_result_code(-1);
            apiRes.set_result_message("登出失败");
            return apiRes;
        }
    }
}