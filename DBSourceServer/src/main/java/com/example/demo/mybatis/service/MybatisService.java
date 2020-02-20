package com.example.demo.mybatis.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.responsetype.ApiRes;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Service
public class MybatisService {

    @Autowired
    @Resource(name = "db1Template")
    private SqlSessionTemplate sqlSessionTemplate1;

    @Autowired
    @Resource(name = "db2Template")
    private SqlSessionTemplate sqlSessionTemplate2;

    public ApiRes select (String db, String mapper, String sqlid, String params, String classname) {
        Object obj = getParamObj(params, classname);
        String statement = getStatement_pre(db) + mapper + "." + sqlid;
        Object res =getDB(db).selectOne(statement, obj);
        ApiRes ares = new ApiRes(0,"",res);
        return  ares;
    }

    public ApiRes selectArray(String db, String mapper, String sqlid, String params, String classname) {
        Object obj = getParamObj(params, classname);
        String statement = getStatement_pre(db) + mapper + "." + sqlid;
        Object res =getDB(db).selectList(statement, obj);
        ApiRes ares = new ApiRes(0,"",res);
        if("selectForPage".equals(sqlid)){
            Integer a = getDB(db).selectOne(getStatement_pre(db) + mapper + "." +"selectForPageCount", obj);
            ares.set_total(a.longValue());
        }
        return  ares;
    }

    public ApiRes  update(String db, String mapper, String sqlid, String params, String classname) {
        Object obj = getParamObj(params, classname);
        String statement = getStatement_pre(db) + mapper + "." + sqlid;
        Object res = getDB(db).update(statement, obj);
        ApiRes ares = new ApiRes(0,"",res);
        return  ares;
    }

    public ApiRes insert(String db, String mapper, String sqlid, String params, String classname) {
        Object obj = getParamObj(params, classname);
        String statement = getStatement_pre(db) + mapper + "." + sqlid;
        Object res = getDB(db).insert(statement, obj);
        JSONObject jsonObject =(JSONObject) JSONObject.toJSON(obj);
        Map resmap = new HashMap();
        for (Map.Entry<String, Object> entry: jsonObject.entrySet()) {
            Object o = entry.getValue();
            if(o instanceof String) {
                if(entry.getKey().indexOf("Id") >= 0 ){
                    resmap.put(entry.getKey(),entry.getValue());
                }
            }
        }
        ApiRes ares = new ApiRes(0,"",resmap);
        return  ares;
    }

    public ApiRes delete(String db, String mapper, String sqlid, String params, String classname) {
        Object obj = getParamObj(params, classname);
        String statement = getStatement_pre(db) + mapper + "." + sqlid;
        Object res =  getDB(db).delete(statement, obj);
        ApiRes ares = new ApiRes(0,"",res);
        return  ares;
    }

    public SqlSessionTemplate getDB(String db) {
        if ("db1".equals(db)) {
            return sqlSessionTemplate1;
        } else if ("db2".equals(db)) {
            return sqlSessionTemplate2;
        } else {
            return null;
        }
    }

    public String getStatement_pre(String db) {
        if ("db1".equals(db)) {
            return "com.example.demo.mybatis.dbs.db1.dao.";
        } else if ("db2".equals(db)) {
            return "com.example.demo.mybatis.dbs.db2.dao.";
        } else {
            return null;
        }
    }

    public Object getParamObj(String params, String classname) {
        Object obj;
        Class<?> clazz;
        try {
            clazz = Class.forName(classname);
            obj = JSON.parseObject(JSON.toJSONString(params), clazz);
            return obj;
        } catch (Exception e) {
            try {
                clazz = Class.forName(classname);
                obj = JSON.parseObject(params, clazz);
                return obj;
            } catch (Exception ex) {
                return params;
            }
        }
    }
}
