package com.example.login.service;

import com.alibaba.fastjson.JSON;
import com.example.login.DBinterface.DBinterface;
import com.example.login.Utils.SystemUtil;
import com.example.login.entity.responsetype.ApiRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityManageService {

    @Autowired
    com.example.login.DBinterface.DBinterface DBinterface;

    public ApiRes excute (String  mapper,String sqlid , String obj,String  paramsClass ) {
        ApiRes res = DBinterface.excute(SystemUtil.CONFIG_DB1,
                mapper,
                sqlid,
                 obj ,
                paramsClass);
        return res;
    }


}