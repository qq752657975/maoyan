package com.ygb.config;


import com.ygb.dbBean.MysqlParams;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;



@Configuration
public class MysqlConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlConfig.class);
    @Bean
    public DataSource dataSource(MysqlParams bean){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(bean.getUrl());
        hikariConfig.setUsername(bean.getUser());
        hikariConfig.setDriverClassName(bean.getDriver());
        hikariConfig.setPassword(bean.getPassword());
        hikariConfig.setAutoCommit(false);
        hikariConfig.addDataSourceProperty("cachePrepStmts","true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize","250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit","2048");
        HikariDataSource ds = new HikariDataSource(hikariConfig);
        return ds;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws IOException {
        LOGGER.info(dataSource.toString());
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver =
                new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("classpath*:/Mapper/*.xml"));
        bean.setConfigLocation(new ClassPathResource("mybatis.xml"));
        return bean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer scanner = new MapperScannerConfigurer();
        scanner.setBasePackage("com.ygb.Dao");
        scanner.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return scanner;
    }

    //配置事务管理器
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource){
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource);
        return manager;
    }



}
