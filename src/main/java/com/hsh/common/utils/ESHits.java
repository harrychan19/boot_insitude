package com.hsh.common.utils;

import com.google.gson.annotations.SerializedName;
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
public class ESHits {
    private Integer total;
    @SerializedName(value = "maxScore", alternate = "max_score")
    private Double maxScore;
    private List<Map<String, Object>> hits;

    //get、set方法
}