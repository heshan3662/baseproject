package com.example.login.Controller;


import com.alibaba.fastjson.JSON;
import com.example.login.Utils.DBParamUtils;
import com.example.login.Utils.GB2Alpha;
import com.example.login.Utils.ResponseUtils;
import com.example.login.entity.Department;
import com.example.login.entity.Role;
import com.example.login.entity.RoleMember;
import com.example.login.entity.User;
import com.example.login.entity.responsetype.ApiRes;
import com.example.login.service.EntityManageService;
import com.example.login.service.LoginService;
import com.example.login.service.RoleMemberService;
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

@RestController
@Api(tags = "roleMember", description = "角色成员管理")
@RequestMapping("/roleMember")
public class RoleMemberController {

    @Autowired
    LoginService loginService;

    @Autowired
    EntityManageService entityManageService;

    private String zclass = "com.example.demo.mybatis.dbs.db1.model.RoleMember" ;
    private String Mapper = "RoleMemberMapper" ;

    @ApiOperation(value = "添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name="roleid",value="角色id",dataType="string", paramType = "query",required = true,example="",defaultValue ="测试" ),
            @ApiImplicitParam(name="userid",value="人员id",dataType="string", paramType = "query",required = true,example="",defaultValue ="测试" ),
    })
    @ResponseBody
    @RequestMapping(value = "/add_roleMember",method = RequestMethod.POST)
    public ApiRes  add_roleMember(
            @RequestParam(value = "roleid" ) String roleid,
            @RequestParam(value = "userid" ) String userid

    ){
        ApiRes  apiRes = new  ApiRes ();
        if(StringUtils.isBlank(roleid) ){
            apiRes.set_result_code(-1);
            apiRes.set_result_message("角色不能为空！");
            return apiRes;
        }
        if(StringUtils.isBlank(userid) ){
            apiRes.set_result_code(-1);
            apiRes.set_result_message("人员不能为空！");
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
        RoleMember roleMember = new RoleMember();
        roleMember.setCreatedBy(createuser);
        roleMember.setCreatedOn(new Date());
        roleMember.setRoleId(roleid);
        roleMember.setUserId(userid);
        String sqlid = "insert";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(roleMember),paramsClass);
        return apiRes;
    }

    @ApiOperation(value = "注销")
    @ApiImplicitParams({
            @ApiImplicitParam(name="memberid",value="角色成员id",dataType="string", paramType = "query",required = true,example="",defaultValue ="dp-001" )
    })
    @ResponseBody
    @RequestMapping(value = "/cancellation_roleMember",method = RequestMethod.POST)
    public ApiRes  cancellation_roleMember(@RequestParam(value = "memberid", defaultValue = "") String memberid
    ){
        ApiRes  apiRes = new  ApiRes ();
        String token =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User user    = loginService.get_cur_user_info(   token);
        String modifiedUser =   user.getUserId();
        RoleMember roleMember = new RoleMember();
        roleMember.setMemberId(memberid );
        roleMember.setIsDisabled("T");
        roleMember.setModifiedOn(new Date());
        roleMember.setModifiedBy(modifiedUser);
        String sqlid = "updateByPrimaryKeySelective";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(roleMember),paramsClass);
        return apiRes ;
    }

    @ApiOperation(value = "激活")
    @ApiImplicitParams({
            @ApiImplicitParam(name="memberid",value="角色成员id",dataType="string", paramType = "query",required = true,example="",defaultValue ="dp-001" )
    })
    @ResponseBody
    @RequestMapping(value = "/activated_roleMember",method = RequestMethod.POST)
    public ApiRes  activated_roleMember(@RequestParam(value = "memberid", defaultValue = "") String memberid
    ){
        ApiRes  apiRes = new  ApiRes ();
        String token =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User user    = loginService.get_cur_user_info(   token);
        String modifiedUser =   user.getUserId();
        RoleMember roleMember = new RoleMember();
        roleMember.setMemberId(memberid );
        roleMember.setIsDisabled("F");
        roleMember.setModifiedOn(new Date());
        roleMember.setModifiedBy(modifiedUser);
        String sqlid = "updateByPrimaryKeySelective";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(roleMember),paramsClass);
        return apiRes ;
    }

    @ApiOperation(value = "角色成员修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name="memberid",value="id",dataType="string", paramType = "query",required = true,example="",defaultValue ="" ),
            @ApiImplicitParam(name="param",value="json参数",dataType="string", paramType = "query",required = false,example="",defaultValue ="{ }" ),
    })
    @ResponseBody
    @RequestMapping(value = "/modify_roleMember",method = RequestMethod.POST)
    public ApiRes  modify_roleMember(  @RequestParam(value = "memberid", defaultValue = "") String memberid,
                                @RequestParam(value = "param", defaultValue = "") String param
    ){
        ApiRes  apiRes = new  ApiRes ();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User user = loginService.get_cur_user_info(token);
        String modifiedUser = user.getUserId();
        if( StringUtils.isBlank(modifiedUser)){
            apiRes.set_result_code(-1);
            apiRes.set_result_message("修改部门失败！");
            return apiRes;
        }
        RoleMember roleMember =  JSON.parseObject(param,RoleMember.class);
        roleMember.setMemberId(memberid);
        roleMember.setModifiedOn(new Date());
        roleMember.setModifiedBy(modifiedUser);
        String sqlid = "updateByPrimaryKeySelective";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(roleMember),paramsClass);
        return apiRes ;
    }

    @ApiOperation(value = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params", value = "入参", dataType = "string", paramType = "query", required = true, example = "",
                    defaultValue = "{\"start\":0,\"end\":10}"),
    })
    @ResponseBody
    @RequestMapping(value = "/get_roleMembers_info", method = RequestMethod.POST)
    public ApiRes<List<RoleMember>>  get_roleMembers_info( @RequestParam(value = "params", defaultValue = "") String params
    ) {
        ApiRes  apiRes ;
        String sqlid = "selectForPage";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes = entityManageService.excute(Mapper,sqlid,params,paramsClass);
        return apiRes;
    }


    @ApiOperation(value = "通过id获取角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberid", value = "角色id", dataType = "string", paramType = "query", required = true, example = "", defaultValue = "test"),

    })
    @ResponseBody
    @RequestMapping(value = "/get_roleMember_info", method = RequestMethod.POST)
    public ApiRes<RoleMember>   get_roleMember_info(@RequestParam(value = "memberid", defaultValue = "") String memberid
    ) {
        ApiRes  apiRes  ;
        String sqlid = "selectByPrimaryKey";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes = entityManageService.excute(Mapper,sqlid ,memberid ,paramsClass);
        return apiRes;
    }
}