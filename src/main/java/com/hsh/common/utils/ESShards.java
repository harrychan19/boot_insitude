package com.hsh.common.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author hushihai
 * @version V1.0, 2018/11/12
 */
@Data
@NoArgsConstructor
public class ESShards {
    private Integer total;
    private Integer successful;
    private Integer skipped;
    private Integer failed;
    private List<Map<String, Object>> failures;

    //get、set方法
}