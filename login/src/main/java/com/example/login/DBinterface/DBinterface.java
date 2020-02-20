package com.example.login.DBinterface;

import com.example.login.entity.responsetype.ApiRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "service-db")
public interface DBinterface {

    @RequestMapping(value = "/jdbcTemplate/queryForObj", method = RequestMethod.POST)
    Map queryForObj(@RequestParam(required = true)String  db ,
                    @RequestParam(required = true)String sql,
                    @RequestParam(required = true)int rowIndex );

    @RequestMapping(value = "/jdbcTemplate/queryForList", method = RequestMethod.POST)
    List queryForList(@RequestParam(required = true)String  db ,
                      @RequestParam(required = true)String sql  );

    @RequestMapping(value = "/jdbcTemplate/update", method = RequestMethod.POST)
     int update( @RequestParam(required = true)String  db ,
                 @RequestParam(required = true)String sql  );


    @RequestMapping(value = "/jdbcTemplate/queryForTotal", method = RequestMethod.POST)
    Long queryForTotal( @RequestParam(required = true)String  db ,
                @RequestParam(required = true)String sql  );

    @RequestMapping(value = "/mybatisDBController/excute", method = RequestMethod.POST)
    ApiRes excute(@RequestParam(required = true)String db,
                  @RequestParam(required = true)String mapper,
                  @RequestParam(required = true)String sqlid,
                  @RequestParam(required = true)String params,
                  @RequestParam(required = true)String paramsClass);
}