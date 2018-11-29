package com.hsh.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author hushihai
 * @version V1.0, 2018/11/8
 */
@Configuration

// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = SecondDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "secondSqlSessionFactory")
public class SecondDataSourceConfig {
    /**  精确到 cluster 目录，以便跟其他数据源隔离 */
    static final String PACKAGE = "com.hsh.dao.second";
    private static final String MAPPER_LOCATION = "classpath:mapper/second/*.xml";

    @Value("${spring.datasource.second.url}")
    private String secondUrl;

    @Value("${spring.datasource.second.username}")
    private String secondUser;

    @Value("${spring.datasource.second.password}")
    private String secondPassword;

    @Value("${spring.datasource.second.driverClass}")
    private String secondDriverClass;

    @Bean(name = "secondDataSource")
    public DataSource secondDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(secondDriverClass);
        dataSource.setUrl(secondUrl);
        dataSource.setUsername(secondUser);
        dataSource.setPassword(secondPassword);
        return dataSource;
    }

    @Bean(name = "secondTransactionManager")
    public DataSourceTransactionManager secondTransactionManager() {
        return new DataSourceTransactionManager(secondDataSource());
    }

    @Bean(name = "secondSqlSessionFactory")
    public SqlSessionFactory secondSqlSessionFactory(@Qualifier("secondDataSource") DataSource secondDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(secondDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(SecondDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}