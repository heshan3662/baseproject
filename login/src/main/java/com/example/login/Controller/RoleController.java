package com.example.login.Controller;


import com.alibaba.fastjson.JSON;
import com.example.login.Utils.DBParamUtils;
import com.example.login.Utils.GB2Alpha;
import com.example.login.Utils.ResponseUtils;
import com.example.login.entity.Department;
import com.example.login.entity.Menu;
import com.example.login.entity.Role;
import com.example.login.entity.User;
import com.example.login.entity.responsetype.ApiRes;
import com.example.login.service.DepartmentService;
import com.example.login.service.EntityManageService;
import com.example.login.service.LoginService;
import com.example.login.service.RoleService;
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
@Api(tags = "role", description = "角色管理")
@RequestMapping("/role")
public class RoleController {

    @Autowired
    LoginService loginService;

    @Autowired
    EntityManageService entityManageService;

    private String zclass = "com.example.demo.mybatis.dbs.db1.model.Role" ;
    private String Mapper = "RoleMapper" ;

    @ApiOperation(value = "添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name="rolename",value="名称",dataType="string", paramType = "query",required = true,example="",defaultValue ="测试" ),
    })
    @ResponseBody
    @RequestMapping(value = "/add_role",method = RequestMethod.POST)
    public ApiRes  add_role(
            @RequestParam(value = "rolename", defaultValue = "") String rolename
    ){
        ApiRes  apiRes = new  ApiRes ();
        if(StringUtils.isBlank(rolename) ){
            apiRes.set_result_code(-1);
            apiRes.set_result_message("名称不能为空！");
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
        Role role = new Role();
        role.setCreatedBy(createuser);
        role.setCreatedOn(new Date());
        role.setName(rolename);
        role.setQuickCode(new GB2Alpha().String2Alpha(rolename));
        String sqlid = "insert";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(role),paramsClass);
        return apiRes;
    }

    @ApiOperation(value = "注销")
    @ApiImplicitParams({
            @ApiImplicitParam(name="roleid",value="角色id",dataType="string", paramType = "query",required = true,example="",defaultValue ="dp-001" )
    })
    @ResponseBody
    @RequestMapping(value = "/cancellation_role",method = RequestMethod.POST)
    public ApiRes  cancellationRole(@RequestParam(value = "roleid", defaultValue = "") String roleid
    ){
        ApiRes  apiRes = new  ApiRes ();
        String token =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User user    = loginService.get_cur_user_info(   token);
        String modifiedUser =   user.getUserId();
        Role role = new Role();
        role.setRoleId(roleid );
        role.setIsDisabled("T");
        role.setModifiedOn(new Date());
        role.setModifiedBy(modifiedUser);
        String sqlid = "updateByPrimaryKeySelective";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(role),paramsClass);
        return apiRes ;
    }

    @ApiOperation(value = "激活")
    @ApiImplicitParams({
            @ApiImplicitParam(name="roleid",value="角色id",dataType="string", paramType = "query",required = true,example="",defaultValue ="dp-001" )
    })
    @ResponseBody
    @RequestMapping(value = "/activated_Dept",method = RequestMethod.POST)
    public ApiRes  activatedDept(@RequestParam(value = "roleid", defaultValue = "") String roleid
    ){
        ApiRes  apiRes = new  ApiRes ();
        String token =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User user    = loginService.get_cur_user_info(   token);
        String modifiedUser =   user.getUserId();
        Role role = new Role();
        role.setRoleId(roleid );
        role.setIsDisabled("F");
        role.setModifiedOn(new Date());
        role.setModifiedBy(modifiedUser);
        String sqlid = "updateByPrimaryKeySelective";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(role),paramsClass);
        return apiRes ;
    }

    @ApiOperation(value = "角色修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name="roleid",value="角色id",dataType="string", paramType = "query",required = true,example="",defaultValue ="" ),
            @ApiImplicitParam(name="rolename",value="角色名称",dataType="string", paramType = "query",required = false,example="",defaultValue ="" ),
    })
    @ResponseBody
    @RequestMapping(value = "/modify_Dept",method = RequestMethod.POST)
    public ApiRes  modifyDept(
                                @RequestParam(value = "roleid", defaultValue = "") String roleid,
                                @RequestParam(value = "rolename", defaultValue = "",required = false) String rolename
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
        Role role = new Role();
        role.setRoleId(roleid );
        role.setName(rolename);
        role.setModifiedOn(new Date());
        role.setModifiedBy(modifiedUser);
        if(StringUtils.isBlank(role.getName())){
            role.setQuickCode(new GB2Alpha().String2Alpha(role.getName()));
        }
        String sqlid = "updateByPrimaryKeySelective";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(role),paramsClass);
        return apiRes ;
    }

    @ApiOperation(value = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params", value = "入参", dataType = "string", paramType = "query", required = true, example = "",
                    defaultValue = "{\"start\":0,\"end\":10}"),
    })
    @ResponseBody
    @RequestMapping(value = "/get_roles_info", method = RequestMethod.POST)
    public ApiRes<List<Department>>  get_roles_info(  @RequestParam(value = "params", defaultValue = "") String params
    ) {
        ApiRes  apiRes = new ApiRes<List<Menu>>();
        String sqlid = "selectForPage";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes = entityManageService.excute(Mapper,sqlid,params,paramsClass);
        return apiRes;
    }

    @ApiOperation(value = "通过id获取角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleid", value = "角色id", dataType = "string", paramType = "query", required = true, example = "", defaultValue = "test"),

    })
    @ResponseBody
    @RequestMapping(value = "/get_role_info", method = RequestMethod.POST)
    public ApiRes<Role>  get_role_info(@RequestParam(value = "roleid", defaultValue = "") String roleid
    ) {
        ApiRes  apiRes = new ApiRes<List<User>>();
        String sqlid = "selectByPrimaryKey";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes = entityManageService.excute(Mapper,sqlid ,roleid ,paramsClass);
        return apiRes;
    }
}