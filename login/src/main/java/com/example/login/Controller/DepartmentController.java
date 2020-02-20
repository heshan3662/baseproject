package com.example.login.Controller;


import com.alibaba.fastjson.JSON;
import com.example.login.Utils.DBParamUtils;
import com.example.login.Utils.GB2Alpha;
import com.example.login.Utils.ResponseUtils;
import com.example.login.entity.Department;
import com.example.login.entity.Menu;
import com.example.login.entity.User;
import com.example.login.entity.responsetype.ApiRes;
import com.example.login.service.DepartmentService;
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

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "dept", description = "部门管理")
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    LoginService loginService;

    @Autowired
    EntityManageService entityManageService;

    private String zclass = "com.example.demo.mybatis.dbs.db1.model.Department" ;

    private String Mapper = "DepartmentMapper" ;

    @ApiOperation(value = "添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name="deptname",value="名称",dataType="string", paramType = "query",required = true,example="",defaultValue ="测试" ),
            @ApiImplicitParam(name="comid",value="公司id",dataType="string", paramType = "query",required = true,defaultValue ="DP-001"),
            @ApiImplicitParam(name="parentdeptid",value="父级部门id",dataType="string", paramType = "query",required = true,defaultValue ="DP-001"),
            @ApiImplicitParam(name="principalid",value="负责人id",dataType="string", paramType = "query",required = true,defaultValue ="US-001"),
    })
    @ResponseBody
    @RequestMapping(value = "/add_Dept",method = RequestMethod.POST)
    public ApiRes  add_Dept(@RequestParam(value = "deptname", defaultValue = "") String deptname,
                           @RequestParam(value = "comid", defaultValue = "") String comid,
                               @RequestParam(value = "parentdeptid", defaultValue = "") String parentdeptid,
                               @RequestParam(value = "principalid", defaultValue = "") String principalid
    ){
        ApiRes  apiRes = new  ApiRes ();
        if(StringUtils.isBlank(deptname) ){
            apiRes.set_result_code(-1);
            apiRes.set_result_message("部门名称不能为空！");
            return apiRes;
        }
        if(StringUtils.isBlank(comid) ){
            apiRes.set_result_code(-1);
            apiRes.set_result_message("所属公司id不能为空！");
            return apiRes;
        }
        if(StringUtils.isBlank(parentdeptid) ){
            apiRes.set_result_code(-1);
            apiRes.set_result_message("父级部门为空！");
            return apiRes;
        }
        if(StringUtils.isBlank(principalid) ){
            apiRes.set_result_code(-1);
            apiRes.set_result_message("负责人为空！");
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
        Department dept = new Department();
        dept.setCreatedBy(createuser);
        dept.setCreatedOn(new Date());
        dept.setName(deptname);
        dept.setComId(comid);
        dept.setParentDept(parentdeptid);
        dept.setPrincipalId(principalid);
        dept.setQuickCode(new GB2Alpha().String2Alpha(deptname));

        String sqlid = "insert";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(dept),paramsClass);
        return apiRes;
    }

    @ApiOperation(value = "部门注销")
    @ApiImplicitParams({
            @ApiImplicitParam(name="deptid",value="部门id",dataType="string", paramType = "query",required = true,example="",defaultValue ="dp-001" )
    })
    @ResponseBody
    @RequestMapping(value = "/cancellation_Dept",method = RequestMethod.POST)
    public ApiRes  cancellation_Dept(@RequestParam(value = "deptid", defaultValue = "") String deptid
    ){
        ApiRes  apiRes = new  ApiRes ();
        String token =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User user    = loginService.get_cur_user_info(   token);
        String modifiedUser =   user.getUserId();
        Department dept = new Department();
        dept.setDeptId(deptid );
        dept.setIsDisabled("T");
        dept.setModifiedOn(new Date());
        dept.setModifiedBy(modifiedUser);
        String sqlid = "updateByPrimaryKeySelective";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(dept),paramsClass);
        return apiRes ;
    }

    @ApiOperation(value = "部门激活")
    @ApiImplicitParams({
            @ApiImplicitParam(name="deptid",value="部门id",dataType="string", paramType = "query",required = true,example="",defaultValue ="dp-001" )
    })
    @ResponseBody
    @RequestMapping(value = "/activated_Dept",method = RequestMethod.POST)
    public ApiRes  activatedDept(@RequestParam(value = "deptid", defaultValue = "") String deptid
    ){
        ApiRes  apiRes = new  ApiRes ();
        String token =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User user    = loginService.get_cur_user_info(   token);
        String modifiedUser =   user.getUserId();
        Department dept = new Department();
        dept.setDeptId(deptid );
        dept.setIsDisabled("F");
        dept.setModifiedOn(new Date());
        dept.setModifiedBy(modifiedUser);
        String sqlid = "updateByPrimaryKeySelective";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(dept),paramsClass);
        return apiRes ;
    }

    @ApiOperation(value = "部门修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name="deptid",value="部门id",dataType="string", paramType = "query",required = true,example="",defaultValue ="" ),
            @ApiImplicitParam(name="deptname",value="部门名称",dataType="string", paramType = "query",required = false,example="",defaultValue ="" ),
            @ApiImplicitParam(name="parentdeptid",value="父级部门id",dataType="string", paramType = "query",required = false,example="",defaultValue ="" ),
            @ApiImplicitParam(name="principalid",value="部门负责人id",dataType="string", paramType = "query",required = false,example="",defaultValue ="" )

    })
    @ResponseBody
    @RequestMapping(value = "/modify_Dept",method = RequestMethod.POST)
    public ApiRes  modifyDept(
                                @RequestParam(value = "deptid", defaultValue = "") String deptid,
                                @RequestParam(value = "deptname", defaultValue = "",required = false) String deptname,
                                @RequestParam(value = "parentdeptid", defaultValue = "",required = false) String parentdeptid,
                                @RequestParam(value = "principalid", defaultValue = "",required = false) String principalid
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
        Department dept = new Department();
        dept.setDeptId(deptid );
        dept.setName(deptname);
        dept.setParentDept(parentdeptid);
        dept.setPrincipalId(principalid);
        dept.setModifiedOn(new Date());
        dept.setModifiedBy(modifiedUser);
        if(!StringUtils.isBlank(dept.getName())){
            dept.setQuickCode(new GB2Alpha().String2Alpha(dept.getName()));
        }
        String sqlid = "updateByPrimaryKeySelective";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(dept),paramsClass);
        return apiRes ;
    }

    @ApiOperation(value = "获取单位信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params", value = "入参", dataType = "string", paramType = "query", required = true, example = "",
                    defaultValue = "{\"start\":0,\"end\":10}"),    })
    @ResponseBody
    @RequestMapping(value = "/get_depts_info", method = RequestMethod.POST)
    public ApiRes<List<Department>>  get_depts_info( @RequestParam(value = "params", defaultValue = "") String params
    ) {
        ApiRes  apiRes = new ApiRes<List<Menu>>();
        String sqlid = "selectForPage";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes = entityManageService.excute(Mapper,sqlid,params,paramsClass);
        return apiRes;
    }


    @ApiOperation(value = "通过id获取单位信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptid", value = "部门id", dataType = "string", paramType = "query", required = true, example = "", defaultValue = "test"),

    })
    @ResponseBody
    @RequestMapping(value = "/get_dept_info", method = RequestMethod.POST)
    public ApiRes<Department>  get_dept_info(@RequestParam(value = "deptid", defaultValue = "") String deptid
    ) {
        ApiRes  apiRes = new ApiRes<List<User>>();
        String sqlid = "selectByPrimaryKey";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes = entityManageService.excute(Mapper,sqlid ,deptid ,paramsClass);
        return apiRes;
    }
}