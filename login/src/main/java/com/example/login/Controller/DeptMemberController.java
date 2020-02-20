package com.example.login.Controller;


import com.alibaba.fastjson.JSON;
import com.example.login.Utils.DBParamUtils;
import com.example.login.entity.ComMember;
import com.example.login.entity.DeptMember;
import com.example.login.entity.User;
import com.example.login.entity.responsetype.ApiRes;
import com.example.login.service.EntityManageService;
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

import java.util.Date;
import java.util.List;

@RestController
@Api(tags = "deptMember", description = "公司成员管理")
@RequestMapping("/deptMember")
public class DeptMemberController {

    @Autowired
    LoginService loginService;

    @Autowired
    EntityManageService entityManageService;

    private String zclass = "com.example.demo.mybatis.dbs.db1.model.DeptMember" ;
    private String Mapper = "DeptMemberMapper" ;

    @ApiOperation(value = "添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name="deptid",value="部门id",dataType="string", paramType = "query",required = true,example="",defaultValue ="测试" ),
            @ApiImplicitParam(name="userid",value="人员id",dataType="string", paramType = "query",required = true,example="",defaultValue ="测试" ),
    })
    @ResponseBody
    @RequestMapping(value = "/add_deptMember",method = RequestMethod.POST)
    public ApiRes  add_deptMember(
            @RequestParam(value = "deptid" ) String deptid,
            @RequestParam(value = "userid" ) String userid

    ){
        ApiRes  apiRes = new  ApiRes ();
        if(StringUtils.isBlank(deptid) ){
            apiRes.set_result_code(-1);
            apiRes.set_result_message("部门不能为空！");
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
        DeptMember deptMember = new DeptMember();
        deptMember.setCreatedBy(createuser);
        deptMember.setCreatedOn(new Date());
        deptMember.setDeptId(deptid);
        deptMember.setUserId(userid);
        String sqlid = "insert";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(deptMember),paramsClass);
        return apiRes;
    }

    @ApiOperation(value = "注销")
    @ApiImplicitParams({
            @ApiImplicitParam(name="memberid",value="角色成员id",dataType="string", paramType = "query",required = true,example="",defaultValue ="dp-001" )
    })
    @ResponseBody
    @RequestMapping(value = "/cancellation_deptMember",method = RequestMethod.POST)
    public ApiRes  cancellation_deptMember(@RequestParam(value = "memberid", defaultValue = "") String memberid
    ){
        ApiRes  apiRes = new  ApiRes ();
        String token =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User user    = loginService.get_cur_user_info(   token);
        String modifiedUser =   user.getUserId();
        DeptMember deptMember = new DeptMember();
        deptMember.setMemberId(memberid );
        deptMember.setIsDisabled("T");
        deptMember.setModifiedOn(new Date());
        deptMember.setModifiedBy(modifiedUser);
        String sqlid = "updateByPrimaryKeySelective";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(deptMember),paramsClass);
        return apiRes ;
    }

    @ApiOperation(value = "激活")
    @ApiImplicitParams({
            @ApiImplicitParam(name="memberid",value="成员id",dataType="string", paramType = "query",required = true,example="",defaultValue ="dp-001" )
    })
    @ResponseBody
    @RequestMapping(value = "/activated_deptMember",method = RequestMethod.POST)
    public ApiRes  activated_deptMember(@RequestParam(value = "memberid", defaultValue = "") String memberid
    ){
        ApiRes  apiRes = new  ApiRes ();
        String token =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User user    = loginService.get_cur_user_info(   token);
        String modifiedUser =   user.getUserId();
        DeptMember  deptMember = new DeptMember();
        deptMember.setMemberId(memberid );
        deptMember.setIsDisabled("F");
        deptMember.setModifiedOn(new Date());
        deptMember.setModifiedBy(modifiedUser);
        String sqlid = "updateByPrimaryKeySelective";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(deptMember),paramsClass);
        return apiRes ;
    }

    @ApiOperation(value = " 成员修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name="memberid",value="id",dataType="string", paramType = "query",required = true,example="",defaultValue ="" ),
            @ApiImplicitParam(name="param",value="json参数",dataType="string", paramType = "query",required = false,example="",defaultValue ="{ }" ),
    })
    @ResponseBody
    @RequestMapping(value = "/modify_deptMember",method = RequestMethod.POST)
    public ApiRes  modify_deptMember(  @RequestParam(value = "memberid", defaultValue = "") String memberid,
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
        DeptMember deptMember =  JSON.parseObject(param,DeptMember.class);
        deptMember.setMemberId(memberid);
        deptMember.setModifiedOn(new Date());
        deptMember.setModifiedBy(modifiedUser);
        String sqlid = "updateByPrimaryKeySelective";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(deptMember),paramsClass);
        return apiRes ;
    }

    @ApiOperation(value = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params", value = "入参", dataType = "string", paramType = "query", required = true, example = "",
                    defaultValue = "{\"start\":0,\"end\":10}"),
    })
    @ResponseBody
    @RequestMapping(value = "/get_deptMembers_info", method = RequestMethod.POST)
    public ApiRes<List<DeptMember>>  get_deptMembers_info( @RequestParam(value = "params", defaultValue = "") String params
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
    @RequestMapping(value = "/get_deptMember_info", method = RequestMethod.POST)
    public ApiRes<DeptMember>   get_deptMember_info(@RequestParam(value = "memberid", defaultValue = "") String memberid
    ) {
        ApiRes  apiRes  ;
        String sqlid = "selectByPrimaryKey";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes = entityManageService.excute(Mapper,sqlid ,memberid ,paramsClass);
        return apiRes;
    }
}