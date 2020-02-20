package com.example.demo.mybatis.controller;

import com.example.demo.mybatis.entity.Test;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;



 @RestController
 @Api(tags = "testMybatise", description = "数据库mybatise测试")
 @RequestMapping("/testMybatise")
class TestMybatise {

    @Autowired
    com.example.demo.mybatis.dbs.db2.mapper.DB2Mapper DB2Mapper;

    @Autowired
    @Resource(name = "db1Template")
    private SqlSessionTemplate sqlSessionTemplate;


//     @ApiOperation(value = "获取db1用户")
//     @ApiImplicitParams({
//             @ApiImplicitParam(name="id",value="用户名token",dataType="long", paramType = "query",required = true,example="",defaultValue ="1" ),
//      })
//     @ResponseBody
//     @RequestMapping(value = "/getDB1", method = RequestMethod.POST)
//    public Users getDB1(
//             @RequestParam(required = true)Long id
//     ) {
//         Users users  = DB1Service.getUser(id);
//        return users;
//    }


     @ApiOperation(value = "获取db2用户")
     @ApiImplicitParams({
             @ApiImplicitParam(name="id",value="用户名token",dataType="int", paramType = "query",required = true,example="",defaultValue ="1" ),
     })
     @ResponseBody
     @RequestMapping(value = "/getDB2", method = RequestMethod.POST)
    public Test getDB2(
             @RequestParam(required = true)Long id
     ) {
//        Map o = sqlSessionTemplate.selectOne("com.example.demo.mybatis.dao.MysqlMapper.getUser",9L);
//        return o;
        Test test  = DB2Mapper.getTest(id);
        return test;
    }


}