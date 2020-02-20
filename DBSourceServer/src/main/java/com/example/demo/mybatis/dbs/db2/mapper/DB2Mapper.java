package com.example.demo.mybatis.dbs.db2.mapper;

import com.example.demo.mybatis.entity.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DB2Mapper {

    @Autowired
    @Resource(name = "db2Template")
    private SqlSessionTemplate sqlSessionTemplate;


    public Test getTest(Long id) {
        return sqlSessionTemplate.selectOne("com.example.demo.mybatis.dao.db2.getTest",id);
    }
}
