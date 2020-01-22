package com.example.demo.jdbctemplate.controller;

import com.example.demo.jdbctemplate.service.DBTools;
import com.example.demo.redis.config.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@Api(tags = "jdbcTemplate", description = "数据库jdbctemplate连接方式")
@RequestMapping("/jdbcTemplate")
public class TemplateDBController {
    @Value("${spring.redis.host}")
     String host ;
    @Value("${spring.isUseRedis}")
    String  isUseRedis ;
    @Autowired
    DBTools DBTools;
    @Autowired
    RedisUtils redisUtils;

    @ApiOperation(value = "获取单条信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="db",value="数据源",dataType="string", paramType = "query",required = true, allowableValues = "db1,db2" ,defaultValue ="db1"  ),
            @ApiImplicitParam(name="sql",value="执行sql",dataType="string", paramType = "query",required = true,example="select *from gd_user",defaultValue ="select *from gd_user" ),
            @ApiImplicitParam(name="rowIndex",value="行数",dataType="string", paramType = "query",required = true,example="",defaultValue ="0" ),

    })
    @ResponseBody
    @RequestMapping(value = "/queryForObj", method = RequestMethod.POST)
    public Map queryForObj(@RequestParam(required = true)String  db ,
                           @RequestParam(required = true)String sql,
                           @RequestParam(required = true)int rowIndex ) {
        return  DBTools.queryforMap( db ,sql,  rowIndex)  ;
    }

    @ApiOperation(value = "获取多条信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="db",value="数据源",dataType="string", paramType = "query",required = true, allowableValues = "db1,db2" ,defaultValue ="db1"  ),
            @ApiImplicitParam(name="sql",value="执行sql",dataType="string", paramType = "query",required = true,example="select *from gd_user",defaultValue ="select *from gd_user" ),

    })
    @ResponseBody
    @RequestMapping(value = "/queryForList", method = RequestMethod.POST)
    public List queryForList( @RequestParam(required = true)String  db ,
                              @RequestParam(required = true)String sql  ) {
        return  DBTools.queryForList( db ,sql )  ;
    }

    @ApiOperation(value = "插入、更新信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="db",value="数据源",dataType="string", paramType = "query",required = true, allowableValues = "db1,db2" ,defaultValue ="db1"  ),
            @ApiImplicitParam(name="sql",value="执行sql",dataType="string", paramType = "query",required = true,example="select *from gd_user",defaultValue ="" ),

    })
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public int update( @RequestParam(required = true)String  db ,
                              @RequestParam(required = true)String sql  ) {
        return  DBTools.update( db ,sql )  ;
    }


  }