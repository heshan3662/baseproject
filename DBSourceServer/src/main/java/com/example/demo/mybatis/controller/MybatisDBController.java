package com.example.demo.mybatis.controller;

import com.example.demo.entity.responsetype.ApiRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.mybatis.service.*;
import javax.annotation.Resource;

@RestController
@Api(tags = "mybatisDBController", description = "数据库Mybatis连接方式")
@RequestMapping("/mybatisDBController")
public class MybatisDBController {

    @Autowired
    MybatisService MybatisService;

    @Autowired
    @Resource(name = "db1Template")
    private SqlSessionTemplate sqlSessionTemplate;

    @ApiOperation(value = "执行语句")
    @ApiImplicitParams({
            @ApiImplicitParam(name="db",value="数据源",dataType="string", paramType = "query",required = true, allowableValues = "db1,db2" ,defaultValue ="db1"  ),
            @ApiImplicitParam(name="mapper",value="数据实体对象",dataType="string", paramType = "query",required = true,  defaultValue ="UserMapper"  ),
            @ApiImplicitParam(name="sqlid",value="执行sql",dataType="string", paramType = "query",required = true,example="",defaultValue ="selectByPrimaryKey" ),
            @ApiImplicitParam(name="params",value="参数",dataType="string", paramType = "query",required = true,example="",defaultValue ="US-100002" ),
            @ApiImplicitParam(name="paramsClass",value="参数类型",dataType="string", paramType = "query",required = true,example="",defaultValue ="java.lang.String" ),

    })
    @ResponseBody
    @RequestMapping(value = "/excute", method = RequestMethod.POST)
    public ApiRes excute(
            @RequestParam(required = true)String db,
            @RequestParam(required = true)String mapper,
            @RequestParam(required = true)String sqlid,
            @RequestParam(required = true)String params,
            @RequestParam(required = true)String paramsClass

    ) {
        ApiRes obj  ;
        if(sqlid.indexOf("selectForPage") == 0  ){
            obj   =  MybatisService.selectArray( db , mapper ,    sqlid ,    params ,  paramsClass);
        }else if(sqlid.indexOf("select") == 0 ||sqlid.indexOf("SELECT") == 0 ){
            obj   =  MybatisService.select( db , mapper ,    sqlid ,    params ,  paramsClass);
        }else if(sqlid.indexOf("update") == 0 ||sqlid.indexOf("UPDATE") == 0){
            obj   =  MybatisService.update( db , mapper ,    sqlid ,    params ,  paramsClass);
        }else if(sqlid.indexOf("insert") == 0 ||sqlid.indexOf("INSERT") == 0){
            obj   =  MybatisService.insert( db , mapper ,    sqlid ,    params ,  paramsClass);
        }else if(sqlid.indexOf("delete") == 0 ||sqlid.indexOf("DELETE") == 0){
            obj   =  MybatisService.delete( db , mapper ,    sqlid ,    params ,  paramsClass);
        }else {
            return  null ;
        }
         return obj;
    }

    @ApiOperation(value = "查询语句")
    @ApiImplicitParams({
            @ApiImplicitParam(name="db",value="数据源",dataType="string", paramType = "query",required = true, allowableValues = "db1,db2" ,defaultValue ="db1"  ),
            @ApiImplicitParam(name="mapper",value="数据实体对象",dataType="string", paramType = "query",required = true,  defaultValue ="UserMapper"  ),
            @ApiImplicitParam(name="sqlid",value="执行sql",dataType="string", paramType = "query",required = true,example="",defaultValue ="selectByPrimaryKey" ),
            @ApiImplicitParam(name="params",value="参数",dataType="string", paramType = "query",required = true,example="",defaultValue ="US-100002" ),
            @ApiImplicitParam(name="paramsClass",value="参数类型",dataType="string", paramType = "query",required = true,example="",defaultValue ="java.lang.String" ),

    })
    @ResponseBody
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public ApiRes select(
            @RequestParam(required = true)String db,
            @RequestParam(required = true)String mapper,
            @RequestParam(required = true)String sqlid,
            @RequestParam(required = true)String params,
            @RequestParam(required = true)String paramsClass

    ) {
        ApiRes obj =   MybatisService.select( db , mapper ,    sqlid ,    params ,  paramsClass);
        return obj;
    }

    @ApiOperation(value = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="db",value="数据源",dataType="string", paramType = "query",required = true, allowableValues = "db1,db2" ,defaultValue ="db1"  ),
            @ApiImplicitParam(name="mapper",value="数据实体对象",dataType="string", paramType = "query",required = true,  defaultValue ="CompanyMapper"  ),
            @ApiImplicitParam(name="sqlid",value="执行sql",dataType="string", paramType = "query",required = true,example="",defaultValue ="selectForPage" ),
            @ApiImplicitParam(name="params",value="参数",dataType="string", paramType = "query",required = true,example="",defaultValue ="{\"start\":0,\"end\":10}" ),
            @ApiImplicitParam(name="paramsClass",value="参数类型",dataType="string", paramType = "query",required = true,example="",allowableValues = "java.util.HashMap" ,defaultValue ="java.util.HashMap" ),

    })
    @ResponseBody
    @RequestMapping(value = "/selectForPage", method = RequestMethod.POST)
    public ApiRes selectForPage(
            @RequestParam(required = true)String db,
            @RequestParam(required = true)String mapper,
            @RequestParam(required = true)String sqlid,
            @RequestParam(required = true)String params,
            @RequestParam(required = true)String paramsClass

    ) {
        ApiRes obj =   MybatisService.selectArray( db , mapper ,    sqlid ,    params ,  paramsClass);
        return obj;
    }

    @ApiOperation(value = "删除语句")
    @ApiImplicitParams({
            @ApiImplicitParam(name="db",value="数据源",dataType="string", paramType = "query",required = true, allowableValues = "db1,db2" ,defaultValue ="db1"  ),
            @ApiImplicitParam(name="mapper",value="数据实体对象",dataType="string", paramType = "query",required = true,  defaultValue ="UserMapper"  ),
            @ApiImplicitParam(name="sqlid",value="执行sqlid",dataType="string", paramType = "query",required = true,example="",defaultValue ="deleteByPrimaryKey" ),
            @ApiImplicitParam(name="params",value="参数",dataType="string", paramType = "query",required = true,example="",defaultValue ="US-100002" ),
            @ApiImplicitParam(name="paramsClass",value="参数类型",dataType="string", paramType = "query",required = true,example="",defaultValue ="java.lang.String" ),

    })
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ApiRes delete(
            @RequestParam(required = true)String db,
            @RequestParam(required = true)String mapper,
            @RequestParam(required = true)String sqlid,
            @RequestParam(required = true)String params,
            @RequestParam(required = true)String paramsClass

    ) {
        ApiRes obj = MybatisService.delete( db , mapper ,    sqlid ,    params ,  paramsClass);

        return obj;
    }

    @ApiOperation(value = "更新数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name="db",value="数据源",dataType="string", paramType = "query",required = true, allowableValues = "db1,db2" ,defaultValue ="db1"  ),
            @ApiImplicitParam(name="mapper",value="数据实体对象",dataType="string", paramType = "query",required = true,  defaultValue ="UserMapper"  ),
            @ApiImplicitParam(name="sqlid",value="执行sql",dataType="string", paramType = "query",required = true,example="",defaultValue ="updateByPrimaryKeySelective" ),
            @ApiImplicitParam(name="params",value="参数",dataType="string", paramType = "query",required = true,example="",defaultValue ="{\n" +
                    "  \"userId\": \"US-100002\",\n" +
                    "  \"loginName\": \"admin\",\n" +
                    "  \"password\": \"MTIzNDU2\",\n" +
                    "  \"phone\": \"18900000000\",\n" +
                    "  \"email\": \"123@xx.com\",\n" +
                    "  \"fullName\": \"张si\",\n" +
                    "  \"avatarUrl\": null,\n" +
                    "  \"jobTitle\": null,\n" +
                    "  \"deptId\": \"1-001\",\n" +
                    "  \"roleId\": \"2-002\",\n" +
                    "  \"isDisabled\": \"F\",\n" +
                    "  \"quickCode\": \"\",\n" +
                    "  \"createdBy\": \"\",\n" +
                    "  \"createdOn\": \"2020-02-13T06:48:24.000+0000\",\n" +
                    "  \"modifiedOn\": \"2020-02-14T06:53:35.000+0000\",\n" +
                    "  \"modifiedBy\": \"US-100002\",\n" +
                    "  \"token\": null\n" +
                    "}" ),
            @ApiImplicitParam(name="paramsClass",value="参数类型",dataType="string", paramType = "query",required = true,example="",defaultValue ="com.example.demo.mybatis.dbs.db1.model.User" ),

    })
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ApiRes update(
            @RequestParam(required = true)String db,
            @RequestParam(required = true)String mapper,
            @RequestParam(required = true)String sqlid,
            @RequestParam(required = true)String params,
            @RequestParam(required = true)String paramsClass

    ) {
        ApiRes obj =  MybatisService.update( db , mapper ,    sqlid ,    params ,  paramsClass);
        return obj;
    }


    @ApiOperation(value = "插入数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name="db",value="数据源",dataType="string", paramType = "query",required = true, allowableValues = "db1,db2" ,defaultValue ="db1"  ),
            @ApiImplicitParam(name="mapper",value="数据实体对象",dataType="string", paramType = "query",required = true,  defaultValue ="UserMapper"  ),
            @ApiImplicitParam(name="sqlid",value="执行sql",dataType="string", paramType = "query",required = true,example="",defaultValue ="insert" ),
            @ApiImplicitParam(name="params",value="参数",dataType="string", paramType = "query",required = true,example="",defaultValue ="{\n" +
                    "  \"loginName\": \"admin1\",\n" +
                    "  \"password\": \"MTIzNDU2\",\n" +
                    "  \"phone\": \"18900000000\",\n" +
                    "  \"email\": \"123@xx.com\",\n" +
                    "  \"fullName\": \"张si\",\n" +
                    "  \"avatarUrl\": null,\n" +
                    "  \"jobTitle\": null,\n" +
                    "  \"deptId\": \"1-001\",\n" +
                    "  \"roleId\": \"2-002\",\n" +
                    "  \"isDisabled\": \"F\",\n" +
                    "  \"quickCode\": \"\",\n" +
                    "  \"createdBy\": \"\",\n" +
                    "  \"createdOn\": \"2020-02-13T06:48:24.000+0000\",\n" +
                    "  \"modifiedOn\": \"2020-02-14T06:53:35.000+0000\",\n" +
                    "  \"modifiedBy\": \"US-100002\",\n" +
                    "  \"token\": null\n" +
                    "}" ),
            @ApiImplicitParam(name="paramsClass",value="参数类型",dataType="string", paramType = "query",required = true,example="",defaultValue ="com.example.demo.mybatis.dbs.db1.model.User" ),

    })
    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ApiRes insert(
            @RequestParam(required = true)String db,
            @RequestParam(required = true)String mapper,
            @RequestParam(required = true)String sqlid,
            @RequestParam(required = true)String params,
            @RequestParam(required = true)String paramsClass

    ) {
        ApiRes res   =   MybatisService.insert( db , mapper ,    sqlid ,    params ,  paramsClass);
        return res;
    }



}