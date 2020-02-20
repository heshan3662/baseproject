
package com.example.login.Controller;


import com.alibaba.fastjson.JSON;
import com.example.login.Utils.DBParamUtils;
import com.example.login.Utils.GB2Alpha;
import com.example.login.Utils.ResponseUtils;
import com.example.login.entity.*;
import com.example.login.entity.responsetype.ApiRes;
import com.example.login.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.awt.SystemColor.menu;

@RestController
@Api(tags = "auth_point", description = "权限点管理")
@RequestMapping("/auth_point")
public class AuthPointController {

    @Autowired
    LoginService loginService;

    @Autowired
    EntityManageService entityManageService;

    private String zclass = "com.example.demo.mybatis.dbs.db1.model.AuthPoint" ;

    private String Mapper = "AuthPointMapper" ;

    @ApiOperation(value = "添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name="module",value="标识",dataType="string", paramType = "query",required = true,example="",defaultValue ="menu1" ),
            @ApiImplicitParam(name="title",value="标题",dataType="string", paramType = "query",required = true,example="",defaultValue ="测试功能1" ),
    })
    @ResponseBody
    @RequestMapping(value = "/add_authPoint",method = RequestMethod.POST)
    public ApiRes  add_authPoint(
            @RequestParam(value = "module", defaultValue = "") String module,
            @RequestParam(value = "title", defaultValue = "") String title
    ){
        ApiRes  apiRes = new  ApiRes ();
        if(StringUtils.isBlank(module) ){
            apiRes.set_result_code(-1);
            apiRes.set_result_message("唯一标识不能为空！");
            return apiRes;
        }
        if(StringUtils.isBlank(title) ){
            apiRes.set_result_code(-1);
            apiRes.set_result_message("标题！");
            return apiRes;
        }

        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User user = loginService.get_cur_user_info(token);
        String createuser = user.getUserId();
        if( StringUtils.isBlank(createuser)){
            apiRes.set_result_code(-1);
            apiRes.set_result_message("创建者获取错误！");
            return apiRes;
        }
        AuthPoint authPoint = new AuthPoint();
        authPoint.setCreatedBy(createuser);
        authPoint.setQuickCode(new GB2Alpha().String2Alpha(title));
        authPoint.setCreatedOn(new Date());
        authPoint.setTitle(title);
        authPoint.setModule(module);
        String sqlid = "insert";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(authPoint),paramsClass);
        return apiRes;
    }

    @ApiOperation(value = "注销")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authPointid",value="权限点id",dataType="string", paramType = "query",required = true,example="",defaultValue ="dp-001" )
    })
    @ResponseBody
    @RequestMapping(value = "/cancellation_authPoint",method = RequestMethod.POST)
    public ApiRes  cancellation_authPoint(@RequestParam(value = "authPointid", defaultValue = "") String authPointid
    ){
        ApiRes  apiRes = new  ApiRes ();
        String token =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User user    = loginService.get_cur_user_info(   token);
        String modifiedUser =   user.getUserId();
        AuthPoint authPoint = new AuthPoint();
        authPoint.setAuthId(authPointid );
        authPoint.setIsDisabled("T");
        authPoint.setModifiedOn(new Date());
        authPoint.setModifiedBy(user.getUserId());
        String sqlid = "updateByPrimaryKeySelective";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(authPoint),paramsClass);
        return apiRes;

    }

    @ApiOperation(value = "激活")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authPointid",value="权限点id",dataType="string", paramType = "query",required = true,example="",defaultValue ="dp-001" )
    })
    @ResponseBody
    @RequestMapping(value = "/activated_authPoint",method = RequestMethod.POST)
    public ApiRes  activated_authPoint(@RequestParam(value = "authPointid", defaultValue = "") String authPointid
    ){
        ApiRes  apiRes = new  ApiRes ();
        String token =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User user    = loginService.get_cur_user_info(   token);
        String modifiedUser =   user.getUserId();
        AuthPoint authPoint = new AuthPoint();
        authPoint.setAuthId(authPointid );
        authPoint.setIsDisabled("F");
        authPoint.setModifiedOn(new Date());
        authPoint.setModifiedBy(user.getUserId());
        String sqlid = "updateByPrimaryKeySelective";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(authPoint),paramsClass);
        return apiRes;
    }

    @ApiOperation(value = "权限点修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name="menuid",value="菜单id",dataType="string", paramType = "query",required = true,example="",defaultValue ="" ),
            @ApiImplicitParam(name="module",value="唯一标识",dataType="string", paramType = "query",required = false,example="",defaultValue ="" ),
            @ApiImplicitParam(name="title",value="标题",dataType="string", paramType = "query",required = false,example="",defaultValue ="" ),
    })
    @ResponseBody
    @RequestMapping(value = "/modify_authPoint",method = RequestMethod.POST)
    public ApiRes  modify_authPoint(
            @RequestParam(value = "authPointid", defaultValue = "") String authPointid,
            @RequestParam(value = "module", defaultValue = "") String module,
            @RequestParam(value = "title", defaultValue = "") String title
    ){
        ApiRes  apiRes = new  ApiRes ();
        String token =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User user    = loginService.get_cur_user_info(   token);
        String modifiedUser =   user.getUserId();
        AuthPoint authPoint = new AuthPoint();
        authPoint.setAuthId(authPointid );
        authPoint.setModule(module);
        authPoint.setTitle(title);
        authPoint.setModifiedOn(new Date());
        authPoint.setModifiedBy(user.getUserId());
        if(!StringUtils.isBlank(authPoint.getTitle())){
            authPoint.setQuickCode(new GB2Alpha().String2Alpha(authPoint.getTitle()));
        }
        String sqlid = "updateByPrimaryKeySelective";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(authPoint),paramsClass);
        return apiRes;
    }

    @ApiOperation(value = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params", value = "入参", dataType = "string", paramType = "query", required = true, example = "",
                    defaultValue = "{\"start\":0,\"end\":10}"),

    })
    @ResponseBody
    @RequestMapping(value = "/get_authPoints_info", method = RequestMethod.POST)
    public ApiRes<List<AuthPoint>>  get_authPoints_info(
            @RequestParam(value = "params", defaultValue = "") String params
    ) {
        ApiRes  apiRes = new ApiRes<List<Menu>>();
        String sqlid = "selectForPage";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes = entityManageService.excute(Mapper,sqlid,params,paramsClass);
        return apiRes;
    }


    @ApiOperation(value = "通过id获取权限点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authPointid", value = "权限点id", dataType = "string", paramType = "query", required = true, example = "", defaultValue = "test"),

    })
    @ResponseBody
    @RequestMapping(value = "/get_authPoint_info", method = RequestMethod.POST)
    public ApiRes<AuthPoint>  get_authPoint_info(@RequestParam(value = "authPointid", defaultValue = "") String authPointid
    ) {
        ApiRes  apiRes = new ApiRes<List<User>>();
        String sqlid = "selectByPrimaryKey";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes = entityManageService.excute(Mapper,sqlid ,authPointid ,paramsClass);
        return apiRes;
    }
}