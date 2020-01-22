package com.example.demo.jdbctemplate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@Service
public class DBTools {
    @Autowired
    @Qualifier( "db1JdbcTemplate")
    private  JdbcTemplate jdbcTemplate1;
    @Autowired
    @Qualifier("db2JdbcTemplate")
    private  JdbcTemplate jdbcTemplate2 ;

    JdbcTemplate jdbcTemplate;

    public  JdbcTemplate getDB(String db  ) {
        if("db1".equals(db)){
            return  jdbcTemplate1;
        }else if ("db2".equals(db)){
            return  jdbcTemplate2;
        }else {
            return null ;
        }

    }

    /***
     * 查询
     * @param sql
     * @return 返回list
     */
    public   List<Map<String, Object>> queryForList(String db,String sql ) {
        List<Map<String, Object>> queryForList =  getDB(db).queryForList(sql );
        return  queryForList;
    }

    /***
     * 查询
     * @param sql
      * @param rowIndex  行序号（从0开始）
     * @return 返回list中的第i行
     */
    public   Map<String, Object>  queryforMap(String db,String sql, int rowIndex ) {
        List<Map<String, Object>> queryForList = getDB(db).queryForList(sql );
        if(queryForList.size() -1  <  rowIndex ){
            return  null ;
        }
        Map map= queryForList.get(rowIndex);
        return  map;
    }

    /***
     * update
     * @param sql
      * @return update的行数
     */
    public   int   update(String db,String sql  ) {
        jdbcTemplate = getDB(db);
        int count = jdbcTemplate.update(sql );
        return  count;
    }

    /****
     * 批量执行
     * @param sql
     * @param list  sql对应的参数集合
     */
    public   void batchUpdate(String db,String sql, List  list)
    {
        jdbcTemplate = getDB(db);
        jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter() {
            @Override
            public int getBatchSize() {
                return list.size();
            }
            @Override
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                Map<Integer, String> map  =  (Map)list.get(i);
                for (Map.Entry<Integer, String> entry : map.entrySet()) {
                    ps.setString(entry.getKey(), entry.getValue());
                }
            }
        });
    }
    /**
     * 执行插入操作 返回新添加内容的自增长id
     * @apiNote 自增长主键问题多，慎用,无事物管控，可用数据库序列替换掉！
     * @param sql
     * @return
     */
    public   long insertForPRIMARY_KEY(String db,String sql ) {
        jdbcTemplate = getDB(db);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Object[] params =null ;
        try {
            jdbcTemplate.update(
                    new PreparedStatementCreator() {
                         public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                            PreparedStatement ps = jdbcTemplate.getDataSource()
                                    .getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                            if (params != null && params.length > 0) {
                                for (int i = 0; i < params.length; i++) {
                                    ps.setObject(i+1, params[i]);
                                }
                            }
                            return ps;
                        }
                    }, keyHolder);
        } catch (Exception e) {
//            logger.error("数据库查询错误[insert]: {}\n{}", sql, params);
            e.printStackTrace();
        }
        long res = keyHolder.getKey() == null ? 0 : keyHolder.getKey().longValue();
        return res;
    }

}