package com.example.demo.jdbctemplate.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Primary //（主数据源配置）
    @Bean(name = "db1")
    @Qualifier("db1")
    @ConfigurationProperties(prefix = "spring.db1.datasource")
    public DataSource mysqlDataSource(){

        return DataSourceBuilder.create().build();
    }

    //
    @Bean(name = "db2")
    @Qualifier("db2")
    @ConfigurationProperties(prefix = "spring.db2.datasource")
    public DataSource sqlServerDataSource(){

        return DataSourceBuilder.create().build();
    }
}
