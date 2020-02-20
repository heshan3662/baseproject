package com.example.login.Controller;


import com.alibaba.fastjson.JSON;
import com.example.login.Utils.DBParamUtils;
import com.example.login.Utils.GB2Alpha;
import com.example.login.Utils.ResponseUtils;
import com.example.login.entity.Company;
import com.example.login.entity.Department;
import com.example.login.entity.User;
import com.example.login.entity.responsetype.ApiRes;
import com.example.login.service.LoginService;
import com.example.login.service.EntityManageService;
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
@Api(tags = "company", description = "公司管理")
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    EntityManageService entityManageService;

    @Autowired
    LoginService loginService;

    private String zclass = "com.example.demo.mybatis.dbs.db1.model.Company" ;
    private String Mapper = "CompanyMapper" ;

    @ApiOperation(value = "添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name="companyname",value="名称",dataType="string", paramType = "query",required = true,example="",defaultValue ="测试" ),
    })
    @ResponseBody
    @RequestMapping(value = "/add_company",method = RequestMethod.POST)
    public ApiRes  add_company(
            @RequestParam(value = "companyname", defaultValue = "") String companyname
    ){
        ApiRes  apiRes = new  ApiRes ();
        if(StringUtils.isBlank(companyname) ){
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
        Company company = new Company();
        company.setName(companyname);
        company.setQuickCode(new GB2Alpha().String2Alpha(companyname));
        company.setIsDisabled("F");
        company.setCreatedBy(createuser);
        company.setCreatedOn(new Date());
        String sqlid = "insert";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(company),paramsClass);
        return apiRes;
    }

    @ApiOperation(value = "注销")
    @ApiImplicitParams({
            @ApiImplicitParam(name="comid",value="公司id",dataType="string", paramType = "query",required = true,example="",defaultValue ="" )
    })
    @ResponseBody
    @RequestMapping(value = "/cancellation_com",method = RequestMethod.POST)
    public ApiRes  cancellation_com(@RequestParam(value = "comid", defaultValue = "") String comid
    ){
        ApiRes  apiRes = new  ApiRes ();
        String token =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User user    = loginService.get_cur_user_info(   token);
        String modifiedUser =   user.getUserId();
        Company company = new Company();
        company.setComId(comid );
        company.setIsDisabled("T");
        company.setModifiedOn(new Date());
        company.setModifiedBy(user.getUserId());
        String sqlid = "updateByPrimaryKeySelective";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(company),paramsClass);
        return apiRes;

    }


    @ApiOperation(value = "激活")
    @ApiImplicitParams({
            @ApiImplicitParam(name="comid",value="公司id",dataType="string", paramType = "query",required = true,example="",defaultValue ="" )
    })
    @ResponseBody
    @RequestMapping(value = "/activated_company",method = RequestMethod.POST)
    public ApiRes  activated_company(@RequestParam(value = "comid", defaultValue = "") String comid
    ){
        ApiRes  apiRes = new  ApiRes ();
        String token =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User user    = loginService.get_cur_user_info(   token);
        String modifiedUser =   user.getUserId();
        Company company = new Company();
        company.setComId(comid );
        company.setIsDisabled("F");
        company.setModifiedOn(new Date());
        company.setModifiedBy(user.getUserId());
        String sqlid = "updateByPrimaryKeySelective";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(company),paramsClass);
        return apiRes;

    }

    @ApiOperation(value = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name="comid",value="公司id",dataType="string", paramType = "query",required = true,example="",defaultValue ="" ),
            @ApiImplicitParam(name="param",value="json参数",dataType="string", paramType = "query",required = false,example="",defaultValue ="{" +
                    "    \"name\": \"test\"}" ),
    })
    @ResponseBody
    @RequestMapping(value = "/modify_com",method = RequestMethod.POST)
    public ApiRes  modify_com(
            @RequestParam(value = "comid", required = true) String comid,
            @RequestParam(value = "param", required = true) String param
    ){
        ApiRes  apiRes = new  ApiRes ();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User user = loginService.get_cur_user_info(token);
        String modifiedUser = user.getUserId();
        if( StringUtils.isBlank(modifiedUser)){
            apiRes.set_result_code(-1);
            apiRes.set_result_message("修改公司失败！");
            return apiRes;
        }
        Company company = JSON.parseObject(param,Company.class);
        company.setComId(comid);
        company.setModifiedOn(new Date());
        company.setModifiedBy(user.getUserId());
        if(!StringUtils.isBlank(company.getName())){
            company.setQuickCode(new GB2Alpha().String2Alpha(company.getName()));
        }
        String sqlid = "updateByPrimaryKeySelective";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(company),paramsClass);
        return apiRes;
    }


    @ApiOperation(value = "通过id获取公司信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "comid", value = "部门id", dataType = "string", paramType = "query", required = true, example = "", defaultValue = ""),

    })
    @ResponseBody
    @RequestMapping(value = "/get_com_info", method = RequestMethod.POST)
    public ApiRes<Department>  get_com_info(@RequestParam(value = "comid",required = true) String comid
    ) {
        ApiRes  apiRes = new ApiRes<List<User>>();
        String sqlid = "selectByPrimaryKey";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes = entityManageService.excute(Mapper,sqlid ,comid,paramsClass);
        return apiRes;
    }


    @ApiOperation(value = "通用接口调用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sqlid", value = "执行动作", dataType = "string", paramType = "query", required = true, example = "",
                    allowableValues = "insert,updateByPrimaryKeySelective,selectByPrimaryKey,selectForPage,selectForPageCount",  defaultValue = ""),
            @ApiImplicitParam(name = "params", value = "入参", dataType = "string", paramType = "query", required = true, example = "",
                    defaultValue = "{\"start\":0,\"end\":10}"),
    })
    @ResponseBody
    @RequestMapping(value = "/common_socket", method = RequestMethod.POST)
    public ApiRes  common_socket(@RequestParam(value = "sqlid",required = true) String sqlid,
                                 @RequestParam(value = "params",required = true) String params
    ) {
        ApiRes  apiRes = new ApiRes ();
        if( StringUtils.isBlank(sqlid)){
            apiRes.set_result_code(-1);
            apiRes.set_result_message("执行动作不能为空！");
            return apiRes;
        }
        if( StringUtils.isBlank(params)){
            apiRes.set_result_code(-1);
            apiRes.set_result_message("入参不能为空！");
            return apiRes;
        }
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        //添加操作员信息
        if(zclass.equals(paramsClass)){
            Company company ;
            try{
                company = JSON.parseObject(params,Company.class);
            }catch (Exception e){
                apiRes.set_result_code(-1);
                apiRes.set_result_message("入参格式错误！");
                return apiRes;
            }
            String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
            User user = loginService.get_cur_user_info(token);
            String modifiedUser = user.getUserId();
            if( StringUtils.isBlank(modifiedUser)){
                apiRes.set_result_code(-1);
                apiRes.set_result_message("获取操作员信息错误！");
                return apiRes;
            }
            if(sqlid.indexOf("insert")>=0 || sqlid.indexOf("INSERT")>=0 ){
                company.setIsDisabled("F");
                company.setCreatedBy(modifiedUser);
                company.setCreatedOn(new Date());
            }else if(sqlid.indexOf("update")>=0 || sqlid.indexOf("UPDATE")>=0) {
                company.setModifiedOn(new Date());
                company.setModifiedBy(user.getUserId());
            }
            params = JSON.toJSONString(company);
         }
        apiRes = entityManageService.excute(Mapper,sqlid,params,paramsClass);
        return apiRes;
    }


}