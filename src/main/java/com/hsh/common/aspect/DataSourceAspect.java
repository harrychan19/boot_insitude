package com.hsh.common.aspect;

import com.hsh.common.annos.DataSource;
import com.hsh.common.config.DataSourceNames;
import com.hsh.common.config.DynamicDataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author hushihai
 * @version V1.0, 2018/12/14
 */

@Aspect
@Component
public class DataSourceAspect implements Ordered {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(* com.hsh.service..*.*(..))")
    public void dataSourcePointCut() {

    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        DataSource ds = method.getAnnotation(DataSource.class);
        //以方法级别为准，如方法未注解数据源，则以类为准
        if (ds != null) {
            ds = point.getTarget().getClass().getAnnotation(DataSource.class);
        }
        //如均未添加注解，则以主数据源为准
        if (ds == null) {
            DynamicDataSource.setDataSource(DataSourceNames.FIRST);
            logger.debug("set datasource is " + DataSourceNames.FIRST);
        } else {
            DynamicDataSource.setDataSource(ds.name());
            logger.debug("set datasource is " + ds.name());
        }
        try {
            return point.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
            logger.debug("clean datasource");
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}