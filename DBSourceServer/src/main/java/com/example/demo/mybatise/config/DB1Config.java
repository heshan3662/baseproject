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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.example.demo.mybatise.dao.db1.mapper", sqlSessionFactoryRef = "db1SessionFactory")
public class DB1Config {

    @Primary
    @Bean(name = "db1Mybatise")
    @ConfigurationProperties(prefix = "spring.db1-mybatise.datasource")
    public DataSource db1DataSource() {

        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "db1TransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("db1Mybatise") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "db1SessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("db1Mybatise") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(configuration);
        bean.setDataSource(dataSource);
        ResourcePatternResolver rsourcePatternResolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(rsourcePatternResolver.getResources("com/example/demo/mybatise/dao/db1/*.xml"));
        return bean.getObject();
    }

    @Primary
    @Bean(name = "db1Template")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("db1SessionFactory") SqlSessionFactory sqlSessionFactory) {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }
}
