package com.hsh.common.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ibm
 */
@Configuration
public class DruidDataSourceConfig {

    @Bean(name = "primaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.primary")
    public DataSource primaryDatasource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave")
    public DataSource slaveDataSource() {
        return DruidDataSourceBuilder.create().build();
    }
    @Bean(name = "logDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.job-log")
    public DataSource logDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource primaryDataSource, DataSource slaveDataSource,DataSource logDataSource) {
        Map<String, DataSource> targetDataSources = new HashMap<>(3);
        targetDataSources.put(DataSourceNames.FIRST, primaryDataSource);
        targetDataSources.put(DataSourceNames.SECOND, slaveDataSource);
        targetDataSources.put(DataSourceNames.JOB_LOG, logDataSource);
        return new DynamicDataSource(primaryDataSource, targetDataSources);
    }
}