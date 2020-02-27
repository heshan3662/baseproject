package com.example.demo.redis.controller;

import com.example.demo.entity.responsetype.ApiRes;
import com.example.demo.mybatis.entity.Test;
import com.example.demo.redis.config.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;



@RestController
@Api(tags = "redisController", description = "redis")
@RequestMapping("/redisController")
public class RedisController {

    @Autowired
    RedisService redisService;


    @ApiOperation(value = "获取redis值")
    @ApiImplicitParams({
            @ApiImplicitParam(name="key",value="key",dataType="int", paramType = "query",required = true,example="",defaultValue ="" ),
    })
    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ApiRes  get(
            @RequestParam(required = true)String key
    ) {
        ApiRes ares = new ApiRes(0,"", redisService.get(key));
        return   ares;
    }


    @ApiOperation(value = "存储redis值")
    @ApiImplicitParams({
            @ApiImplicitParam(name="key",value="key",dataType="int", paramType = "query",required = true,example="",defaultValue ="" ),
            @ApiImplicitParam(name="value",value="value",dataType="int", paramType = "query",required = true,example="",defaultValue ="" ),

    })
    @ResponseBody
    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public ApiRes  set(
            @RequestParam(required = true)String key,
            @RequestParam(required = true)String value
    ) {
        ApiRes ares = new ApiRes(0,"", redisService.set(key,value, (long)0.01));
        return  ares ;
    }

    @ApiOperation(value = "删除redis值")
    @ApiImplicitParams({
            @ApiImplicitParam(name="key",value="key",dataType="int", paramType = "query",required = true,example="",defaultValue ="" ),

    })
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ApiRes  delete(
            @RequestParam(required = true)String key
     ) {
        ApiRes ares = new ApiRes(0,"", redisService.delete(key ));
        return  ares ;
     }

}