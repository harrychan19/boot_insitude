package com.hsh.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hushihai
 * @version V1.0, 2018/12/14
 */
@Configuration
public class DynamicDataSource  extends AbstractRoutingDataSource {

    /** 用来保存数据源与获取数据源 */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    public DynamicDataSource(DataSource defaultTargetDataSource, Map<String, DataSource> targetDataSources) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(new HashMap<>(targetDataSources));
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSource();
    }

    public static void setDataSource(String dataSource) {
        CONTEXT_HOLDER.set(dataSource);
    }

    private static String getDataSource() {
        return CONTEXT_HOLDER.get();
    }

    public static void clearDataSource() {
        CONTEXT_HOLDER.remove();
    }
}
