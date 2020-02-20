package com.example.login.Controller;


import com.alibaba.fastjson.JSON;
import com.example.login.Utils.DBParamUtils;
import com.example.login.entity.ComMember;
import com.example.login.entity.RoleMember;
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
@Api(tags = "comMember", description = "公司成员管理")
@RequestMapping("/comMember")
public class ComMemberController {

    @Autowired
    LoginService loginService;

    @Autowired
    EntityManageService entityManageService;

    private String zclass = "com.example.demo.mybatis.dbs.db1.model.ComMember" ;
    private String Mapper = "ComMemberMapper" ;

    @ApiOperation(value = "添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name="comid",value="公司id",dataType="string", paramType = "query",required = true,example="",defaultValue ="测试" ),
            @ApiImplicitParam(name="userid",value="人员id",dataType="string", paramType = "query",required = true,example="",defaultValue ="测试" ),
    })
    @ResponseBody
    @RequestMapping(value = "/add_comMember",method = RequestMethod.POST)
    public ApiRes  add_comMember(
            @RequestParam(value = "comid" ) String comid,
            @RequestParam(value = "userid" ) String userid

    ){
        ApiRes  apiRes = new  ApiRes ();
        if(StringUtils.isBlank(comid) ){
            apiRes.set_result_code(-1);
            apiRes.set_result_message("公司不能为空！");
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
        ComMember comMember = new ComMember();
        comMember.setCreatedBy(createuser);
        comMember.setCreatedOn(new Date());
        comMember.setComId(comid);
        comMember.setUserId(userid);
        String sqlid = "insert";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(comMember),paramsClass);
        return apiRes;
    }

    @ApiOperation(value = "注销")
    @ApiImplicitParams({
            @ApiImplicitParam(name="memberid",value="角色成员id",dataType="string", paramType = "query",required = true,example="",defaultValue ="dp-001" )
    })
    @ResponseBody
    @RequestMapping(value = "/cancellation_comMember",method = RequestMethod.POST)
    public ApiRes  cancellation_comMember(@RequestParam(value = "memberid", defaultValue = "") String memberid
    ){
        ApiRes  apiRes = new  ApiRes ();
        String token =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User user    = loginService.get_cur_user_info(   token);
        String modifiedUser =   user.getUserId();
        ComMember comMember = new ComMember();
        comMember.setMemberId(memberid );
        comMember.setIsDisabled("T");
        comMember.setModifiedOn(new Date());
        comMember.setModifiedBy(modifiedUser);
        String sqlid = "updateByPrimaryKeySelective";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(comMember),paramsClass);
        return apiRes ;
    }

    @ApiOperation(value = "激活")
    @ApiImplicitParams({
            @ApiImplicitParam(name="memberid",value="成员id",dataType="string", paramType = "query",required = true,example="",defaultValue ="dp-001" )
    })
    @ResponseBody
    @RequestMapping(value = "/activated_comMember",method = RequestMethod.POST)
    public ApiRes  activated_comMember(@RequestParam(value = "memberid", defaultValue = "") String memberid
    ){
        ApiRes  apiRes = new  ApiRes ();
        String token =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User user    = loginService.get_cur_user_info(   token);
        String modifiedUser =   user.getUserId();
        ComMember  comMember = new ComMember();
        comMember.setMemberId(memberid );
        comMember.setIsDisabled("F");
        comMember.setModifiedOn(new Date());
        comMember.setModifiedBy(modifiedUser);
        String sqlid = "updateByPrimaryKeySelective";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(comMember),paramsClass);
        return apiRes ;
    }

    @ApiOperation(value = "角色成员修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name="memberid",value="id",dataType="string", paramType = "query",required = true,example="",defaultValue ="" ),
            @ApiImplicitParam(name="param",value="json参数",dataType="string", paramType = "query",required = false,example="",defaultValue ="{ }" ),
    })
    @ResponseBody
    @RequestMapping(value = "/modify_comMember",method = RequestMethod.POST)
    public ApiRes  modify_comMember(  @RequestParam(value = "memberid", defaultValue = "") String memberid,
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
        ComMember comMember =  JSON.parseObject(param,ComMember.class);
        comMember.setMemberId(memberid);
        comMember.setModifiedOn(new Date());
        comMember.setModifiedBy(modifiedUser);
        String sqlid = "updateByPrimaryKeySelective";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(comMember),paramsClass);
        return apiRes ;
    }

    @ApiOperation(value = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params", value = "入参", dataType = "string", paramType = "query", required = true, example = "",
                    defaultValue = "{\"start\":0,\"end\":10}"),
    })
    @ResponseBody
    @RequestMapping(value = "/get_comMembers_info", method = RequestMethod.POST)
    public ApiRes<List<ComMember>>  get_comMembers_info( @RequestParam(value = "params", defaultValue = "") String params
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
    @RequestMapping(value = "/get_comMember_info", method = RequestMethod.POST)
    public ApiRes<ComMember>   get_comMember_info(@RequestParam(value = "memberid", defaultValue = "") String memberid
    ) {
        ApiRes  apiRes  ;
        String sqlid = "selectByPrimaryKey";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes = entityManageService.excute(Mapper,sqlid ,memberid ,paramsClass);
        return apiRes;
    }
}