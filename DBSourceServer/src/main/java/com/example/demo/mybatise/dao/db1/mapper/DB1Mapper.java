package com.example.demo.mybatise.dao.db1.mapper;

import com.example.demo.mybatise.entity.Users;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
 import org.springframework.stereotype.Service;

 import javax.annotation.Resource;
import java.util.Map;


@Service
public class DB1Mapper {

    @Autowired
    @Resource(name = "db1Template")
    private SqlSessionTemplate sqlSessionTemplate;


     public Users getUser(Long id) {
        return sqlSessionTemplate.selectOne("getUser",id);
     }
}
