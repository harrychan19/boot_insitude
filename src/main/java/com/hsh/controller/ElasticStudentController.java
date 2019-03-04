package com.hsh.controller;

import com.hsh.common.config.ElasticIndexConfig;
import com.hsh.common.config.RestResult;
import com.hsh.common.config.ResultCode;
import com.hsh.common.utils.EsPage;
import com.hsh.domain.elastic.ElasticStudent;
import com.hsh.service.elastic.JestService;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hushihai
 * @version V1.0, 2018/11/11
 */
@RestController
@RequestMapping(value = "/elastic")
@SuppressWarnings("unchecked")
public class ElasticStudentController {

    @Autowired
    JestService jestService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /** 增加 */
    @PostMapping("/insertElasticStudent")
    public RestResult insertElasticStudent(@RequestBody ElasticStudent student){
        try {
            jestService.index(ElasticIndexConfig.STU_INDEX, ElasticIndexConfig.STU, student);
        } catch (Exception e) {
            e.printStackTrace();
            return RestResult.failed(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
        return RestResult.success();
    }

    @GetMapping("/getById")
    public RestResult getById(Long id){
        ElasticStudent student;
        try {
            student = jestService.getById(ElasticIndexConfig.STU_INDEX, ElasticIndexConfig.STU, id.toString(), ElasticStudent.class);
        } catch (Exception e) {
            e.printStackTrace();
            return RestResult.failed(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
        return RestResult.success(student);
    }

    @DeleteMapping("/deleteById")
    public RestResult deleteById(Long id){
        try {
            jestService.delete(ElasticIndexConfig.STU_INDEX,
                    ElasticIndexConfig.STU,
                    id.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return RestResult.failed(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
        return RestResult.success();
    }

    @PutMapping(value = "/update")
    public RestResult update(@RequestBody ElasticStudent student){
        try {
            jestService.index(ElasticIndexConfig.STU_INDEX, ElasticIndexConfig.STU, student);
        } catch (Exception e) {
            e.printStackTrace();
            return RestResult.failed(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
        return RestResult.success();
    }

    @GetMapping("/getStudentList")
    public RestResult getStudentList(String name){
        EsPage esPage;
        try {
            //es搜索默认第一页页码是0
            SearchSourceBuilder builder = new SearchSourceBuilder();
            QueryBuilder query = QueryBuilders.termQuery("name",name);
            builder.query(query);
            SearchResult result = jestService.blurSearch(ElasticIndexConfig.STU_INDEX,
                    ElasticIndexConfig.STU,
                    "name",
                    name);
            Long total = result.getTotal();
            List<ElasticStudent> students = result.getSourceAsObjectList(ElasticStudent.class, false);
            esPage = new EsPage(0,5,total.intValue(),students);
        } catch (Exception e) {
            logger.error("es查询错误",e);
            return RestResult.failed(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
        return RestResult.success(esPage);
    }


}
