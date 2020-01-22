package com.example.demo.mybatise.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.example.demo.mybatise.dao.db2.mapper", sqlSessionFactoryRef = "db2SessionFactory")
public class DB2Config {


    @Bean(name = "db2Mybatise")
    @ConfigurationProperties(prefix = "spring.db2-mybatise.datasource")
    public DataSource mysqlDataSource() {

        return DataSourceBuilder.create().build();
    }

    @Bean(name = "db2TransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("db2Mybatise") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "db2SessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("db2Mybatise") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(configuration);
        bean.setDataSource(dataSource);
        ResourcePatternResolver rsourcePatternResolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(rsourcePatternResolver.getResources("classpath*:com/example/demo/mybatise/dao/db2/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "db2Template")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("db2SessionFactory") SqlSessionFactory sqlSessionFactory) {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }
}
