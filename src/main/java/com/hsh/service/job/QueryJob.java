package com.hsh.service.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.springframework.stereotype.Component;

/**
 * @author hushihai
 * @version V1.0, 2018/12/23
 */
@Component
public class QueryJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("任务执行了");
    }
}
