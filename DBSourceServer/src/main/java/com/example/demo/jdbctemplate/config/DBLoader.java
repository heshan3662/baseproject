package com.example.demo.jdbctemplate.config;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class DBLoader {
    @Bean(name = "db1JdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(@Qualifier("db1") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "db2JdbcTemplate")
    public JdbcTemplate secondaryJdbcTemplate(@Qualifier("db2") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
