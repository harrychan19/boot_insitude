package com.hsh.common.utils;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hushihai
 * @version V1.0, 2018/11/12
 */
@Data
@NoArgsConstructor
public class ElasticSearchResult {
    private Integer took;
    @SerializedName(value = "timeOut", alternate = {"time_out"})
    private Boolean timeOut;
    @SerializedName(value = "shards", alternate = {"_shards"})
    private ESShards shards;
    private ESHits hits;

    //get、set方法
}