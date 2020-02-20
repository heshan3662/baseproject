package com.example.login.Utils;

import com.example.login.entity.responsetype.ApiRes;
import com.example.login.service.EntityManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

 public class  DBParamUtils {


    public static String  getParamClass(String sqlid, String EntityClass ){
        if("selectByPrimaryKey".equals(sqlid)){
            return "java.lang.String";
        }else if("selectForPage".equals(sqlid)){
            return "java.util.HashMap";
        }else if("insert".equals(sqlid)){
            return EntityClass;
        }else if("updateByPrimaryKeySelective".equals(sqlid)){
            return EntityClass;
        }else {
            return  null  ;
        }

    }


}
