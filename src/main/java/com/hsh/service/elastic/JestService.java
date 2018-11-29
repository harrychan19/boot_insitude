package com.hsh.service.elastic;

import com.google.gson.Gson;
import com.hsh.common.utils.ESDoc;
import com.hsh.common.utils.ElasticSearchResult;
import com.hsh.common.utils.Pagination;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.cluster.Health;
import io.searchbox.cluster.NodesStats;
import io.searchbox.core.*;
import io.searchbox.core.search.sort.Sort;
import io.searchbox.indices.*;
import io.searchbox.indices.mapping.GetMapping;
import io.searchbox.indices.mapping.PutMapping;
import io.searchbox.indices.settings.UpdateSettings;
import io.searchbox.params.Parameters;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * es jest服务类
 */

@Service
public class JestService {

    @Autowired
    JestClient jestClient;
     private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String SCROLL = "1m";
    private final static int BATCH_SIZE = 5000;
    private final static int MAX_SIZE = 10000;

    /**
     * 创建索引
     *
     * @param indexName
     * @return
     * @throws Exception
     */
    public boolean createIndex(String indexName) throws Exception {

        JestResult jr = jestClient.execute(new CreateIndex.Builder(indexName).build());
        return jr.isSucceeded();
    }

    /**
     * Put映射
     *
     * @param indexName
     * @param typeName
     * @param source
     * @return
     * @throws Exception
     */
    public boolean createIndexMapping(String indexName, String typeName, String source) throws Exception {

        PutMapping putMapping = new PutMapping.Builder(indexName, typeName, source).build();
        JestResult jr = jestClient.execute(putMapping);
        return jr.isSucceeded();
    }

    /**
     * 设置最大查询数量
     *
     * @throws IOException
     */
    public void updateSetting() throws IOException {
        String body = "{ \"index\" : { \"max_result_window\" : 100000000} }";
        UpdateSettings.Builder builder = new UpdateSettings.Builder(body);
        jestClient.execute(builder.build());
    }

    /**
     * Get映射
     *
     * @param indexName
     * @param typeName
     * @return
     * @throws Exception
     */
    public String getIndexMapping(String indexName, String typeName) throws Exception {

        GetMapping getMapping = new GetMapping.Builder().addIndex(indexName).addType(typeName).build();
        JestResult jr = jestClient.execute(getMapping);
        return jr.getJsonString();
    }

    /**
     * 索引文档
     *
     * @param indexName
     * @param typeName
     * @param objs
     * @return
     * @throws Exception
     */
    public boolean indexList(String indexName, String typeName, List<Object> objs) throws Exception {

        Bulk.Builder bulk = new Bulk.Builder().defaultIndex(indexName).defaultType(typeName);
        for (Object obj : objs) {
            Index index = new Index.Builder(obj).build();
            bulk.addAction(index);
        }
        BulkResult br = jestClient.execute(bulk.build());
        return br.isSucceeded();
    }

    /**
     * 索引文档
     *
     * @param indexName
     * @param typeName
     * @param obj
     * @return
     * @throws Exception
     */
    public boolean index(String indexName, String typeName, Object obj) throws Exception {

        Bulk.Builder bulk = new Bulk.Builder().defaultIndex(indexName).defaultType(typeName);
        Index index = new Index.Builder(obj).build();
        bulk.addAction(index);
        BulkResult br = jestClient.execute(bulk.build());
        return br.isSucceeded();
    }

    /**
     * 搜索文档
     *
     * @param indexName
     * @param typeName
     * @param query
     * @return
     * @throws Exception
     */
    public <T> List<T> search(String indexName, String typeName, String query, Class<T> eClass) throws Exception {
        Search search = new Search.Builder(query)
                .addIndex(indexName)
                .addType(typeName)
                .build();
        SearchResult result = jestClient.execute(search);
        return result.getSourceAsObjectList(eClass, false);
    }

    /**
     * Count文档
     *
     * @param indexName
     * @param typeName
     * @param query
     * @return
     * @throws Exception
     */
    public Double count(String indexName, String typeName, String query) throws Exception {

        Count count = new Count.Builder()
                .addIndex(indexName)
                .addType(typeName)
                .query(query)
                .build();
        CountResult results = jestClient.execute(count);
        return results.getCount();
    }

    /**
     * Get文档
     *
     * @param indexName
     * @param typeName
     * @param id
     * @return
     * @throws Exception
     */
    public <T> T getById(String indexName, String typeName, String id, Class<T> clazz) throws Exception {

        Get get = new Get.Builder(indexName, id).type(typeName).build();
        JestResult result = jestClient.execute(get);
        if (result.isSucceeded()) {
            return result.getSourceAsObject(clazz);
        } else {
            return null;
        }

    }

    /**
     * Delete索引
     *
     * @param indexName
     * @return
     * @throws Exception
     */
    public boolean delete(String indexName) throws Exception {

        JestResult jr = jestClient.execute(new DeleteIndex.Builder(indexName).build());
        return jr.isSucceeded();
    }

    /**
     * Delete文档
     *
     * @param indexName
     * @param typeName
     * @param id
     * @return
     * @throws Exception
     */
    public boolean delete(String indexName, String typeName, String id) throws Exception {

        DocumentResult dr = jestClient.execute(new Delete.Builder(id).index(indexName).type(typeName).build());
        return dr.isSucceeded();
    }


    /**
     * 判断索引目录是否存在
     *
     * @throws Exception
     */
    public boolean indicesExists(String indexName) throws Exception {
        IndicesExists indicesExists = new IndicesExists.Builder(indexName).build();
        JestResult result = jestClient.execute(indicesExists);
        if (result.isSucceeded()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 查看集群健康信息
     *
     * @throws Exception
     */
    public JestResult health() throws IOException {

        Health health = new Health.Builder().build();
        JestResult result = jestClient.execute(health);
        return result;
    }

    /**
     * 节点状态
     *
     * @throws Exception
     */
    public JestResult nodesStats() throws IOException {
        NodesStats nodesStats = new NodesStats.Builder().build();
        JestResult result = jestClient.execute(nodesStats);
        return result;
    }

    /**
     * 创建索引
     *
     * @param indexName
     * @param settings
     * @return 执行结果json
     * @throws IOException
     */
    public JestResult createIndex(String indexName, String settings) throws IOException {
        CreateIndex.Builder builder = new CreateIndex.Builder(indexName);
        if (StringUtils.isNotBlank(settings)) {
            builder.settings(Settings.builder().loadFromSource(settings));
        }
        CreateIndex createIndex = builder.build();
        JestResult result = jestClient.execute(createIndex);
        return result;
    }

    /**
     * 创建索引映射
     *
     * @param indexName
     * @param type
     * @param mappings
     * @return
     * @throws IOException
     */
    public JestResult putMapping(String indexName, String type, String mappings)
            throws IOException {
        PutMapping putMapping = new PutMapping.Builder(indexName, type, mappings).build();
        JestResult result = jestClient.execute(putMapping);
        return result;
    }

    /**
     * 判断索引是否存在
     *
     * @param indexName
     * @return 结果json
     * @throws IOException
     */
    public JestResult isExists(String indexName) throws IOException {
        IndicesExists indicesExists = new IndicesExists.Builder(indexName).build();
        JestResult result = jestClient.execute(indicesExists);
        return result;
    }

    /**
     * 优化索引
     *
     * @return
     */
    public JestResult optimizeIndex() {
        Optimize optimize = new Optimize.Builder().build();
        JestResult result = null;
        try {
            result = jestClient.execute(optimize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 刷新缓存
     *
     * @return
     */
    public JestResult clearCache() {
        ClearCache closeIndex = new ClearCache.Builder().build();
        JestResult result = null;
        try {
            result = jestClient.execute(closeIndex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 添加数据
     *
     * @param indexName
     * @param type
     * @param doc       要索引的文档
     * @return 执行结果json
     * @throws IOException
     */
    public JestResult insertDocument(String indexName, String type, ESDoc doc) throws IOException {
        Index index = new Index.Builder(doc).index(indexName).type(type).build();
        JestResult result = jestClient.execute(index);
        return result;
    }

    /**
     * 批量索引
     *
     * @param indexName
     * @param type
     * @param docs
     * @return
     * @throws IOException
     */
    public <T> JestResult bulkIndex(String indexName, String type, List<T> docs)
            throws IOException {
        Bulk.Builder builder = new Bulk.Builder().defaultIndex(indexName).defaultType(type);
        for (T doc : docs) {
            builder.addAction(new Index.Builder(doc).build());
        }

        JestResult result = jestClient.execute(builder.build());
        return result;
    }

    /**
     * 批量索引
     *
     * @param docs
     * @return
     * @throws IOException
     */
    public JestResult bulkIndex(List<ESDoc> docs)
            throws IOException {
        Bulk.Builder builder = new Bulk.Builder();
        for (ESDoc doc : docs) {
            builder.addAction(new Index.Builder(doc).index(doc.getIndex()).type(doc.getType()).build());
        }

        JestResult result = jestClient.execute(builder.build());
        return result;
    }

    /**
     * 删除文档
     *
     * @param indexName
     * @param type
     * @param id
     * @return
     * @throws Exception
     */
    public JestResult deleteDocument(String indexName, String type, String id) throws Exception {
        Delete delete = new Delete.Builder(id).index(indexName).type(type).build();
        JestResult result = jestClient.execute(delete);
        return result;
    }

    /**
     * 更新文档内容
     *
     * @param indexName
     * @param type
     * @param doc
     * @return
     * @throws IOException
     */
    public JestResult updateDocument(String indexName, String type, ESDoc doc)
            throws IOException {
        Update update = new Update.Builder(doc).index(indexName).type(type).id(doc.getDocId()).build();
        JestResult result = jestClient.execute(update);
        return result;
    }

    /**
     * 获取指定文档
     *
     * @param indexName
     * @param type
     * @param docId
     * @return
     * @throws IOException 从返回结果获取对象
     *                     Article article = result.getSourceAsObject(Article.class)；
     */
    public JestResult getDocument(String indexName, String type, String docId) throws IOException {
        Get get = new Get.Builder(indexName, docId).type(type).build();
        JestResult result = jestClient.execute(get);
        return result;
    }

    /**
     * 简单搜索
     *
     * @param indexName
     * @param type
     * @param query     查询json串
     * @return
     * @throws IOException 如何取出搜索结果
     *                     List<SearchResult.Hit<Article, Void>> hits = result.getHits(Article.class);
     *                     or
     *                     List<Article> articles = result.getSourceAsObjectList(Article.class);
     */
    public SearchResult simpleSearch(String indexName, String type, String query)
            throws IOException {
        Search search = new Search.Builder(query)
                // multiple index or types can be added.
                .addIndex(indexName)
                .addType(type)
                .build();

        SearchResult result = jestClient.execute(search);
        return result;
    }

    /**
     * 检索所有
     *
     * @param indexName
     * @param type
     * @return
     * @throws IOException
     */
    public SearchResult searchAll(String indexName, String type) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        Search search = new Search.Builder(
                searchSourceBuilder.toString())
                .addIndex(indexName)
                .addType(type).build();
        SearchResult result = jestClient.execute(search);
        return result;
    }

    /**
     * 检索所有并根据指定列进行排序(降序)
     *
     * @param indexName
     * @param type
     * @param sortField
     * @return
     * @throws IOException
     */
    public SearchResult searchAllDesc(String indexName, String type, String sortField) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        Search search = new Search.Builder(
                searchSourceBuilder.toString()).addSort(new Sort(sortField, Sort.Sorting.DESC))
                .addIndex(indexName)
                .addType(type).build();
        SearchResult result = jestClient.execute(search);
        return result;
    }

    /**
     * 检索所有并根据指定列进行排序(升序)
     *
     * @param indexName
     * @param type
     * @param sortField
     * @return
     * @throws IOException
     */
    public SearchResult searchAllAsc(String indexName, String type, String sortField) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        Search search = new Search.Builder(
                searchSourceBuilder.toString()).addSort(new Sort(sortField, Sort.Sorting.ASC))
                .addIndex(indexName)
                .addType(type).build();
        SearchResult result = jestClient.execute(search);
        return result;
    }

    /**
     * 检索所有
     *
     * @param indexName
     * @param type
     * @return
     * @throws IOException
     */
    public SearchResult searchMax(String indexName, String type, String field) throws IOException {

        String query = "{\n" +
                "         \"size\": 0,\n" +
                "         \"aggs\": {\n" +
                "         \"maxCtime\": {\n" +
                "         \"max\": {\n" +
                "         \"field\": \"ctime\"\n" +
                "         }\n" +
                "         }\n" +
                "         }\n" +
                "         }";

        Search search = new Search.Builder(query)
                .addIndex(indexName)
                .addType(type)
                .build();

        SearchResult result = jestClient.execute(search);
        return result;
    }

    /**
     * 单属性全文匹配
     *
     * @param field
     * @param keyword
     * @return
     */
    public SearchResult searchInfoByField(String indexName, String type, String field, Object keyword) throws Exception {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        QueryBuilder queryBuilder = QueryBuilders.termQuery(field+".keyword", keyword);//单值完全匹配查询
        QueryBuilder queryBuilder = QueryBuilders.termQuery(field, keyword);//单值完全匹配查询
        searchSourceBuilder.query(queryBuilder).size(MAX_SIZE);
        String query = searchSourceBuilder.toString();
        System.out.println(query);

        Search search = new Search.Builder(query)
                .addIndex(indexName)
                .addType(type)
                .build();

        SearchResult result = jestClient.execute(search);
        return result;
    }

    /**
     * 模糊检索（含时间过滤）
     *
     * @param indexName
     * @param type
     * @param keyWord
     * @param field
     * @return
     * @throws IOException
     */
    public SearchResult blurSearch(String indexName, String type, String field, String keyWord, Long startTime, Long endTime) throws IOException {

        //方式五：查询query（用API进行查询是对应视图工具上的json参数进行查询）
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //时间过滤
        QueryBuilder timeQuery = QueryBuilders.rangeQuery("ctime").from(startTime).to(endTime);

        //文本过滤
        QueryBuilder contentBuilder = QueryBuilders.wildcardQuery(field, "*" + keyWord + "*");
        QueryBuilder boolQuery = QueryBuilders.boolQuery().must(timeQuery).must(contentBuilder);
        searchSourceBuilder.query(boolQuery).size(MAX_SIZE);

        String query = searchSourceBuilder.toString();
        System.out.println(query);

        Search search = new Search.Builder(query)
                .addIndex(indexName)
                .addType(type)
                .build();

        SearchResult result = jestClient.execute(search);
        return result;
    }

    /**
     * 获取小于指定long的时间节点数据
     *
     * @param indexName
     * @param type
     * @return
     * @throws IOException
     */
    public SearchResult getlteByCtime(String indexName, String type, Long startTime) throws IOException {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //时间过滤
        QueryBuilder timeQuery = QueryBuilders.rangeQuery("ctime").lte(startTime);
        QueryBuilder boolQuery = QueryBuilders.boolQuery().must(timeQuery);
        searchSourceBuilder.query(boolQuery).size(MAX_SIZE);

        String query = searchSourceBuilder.toString();
        System.out.println(query);

        Search search = new Search.Builder(query)
                .addIndex(indexName)
                .addType(type)
                .build();

        SearchResult result = jestClient.execute(search);
        return result;
    }

    /**
     * 获取大于指定long类型列节点数据
     *
     * @param indexName
     * @param type
     * @return
     * @throws IOException
     */
    public SearchResult getgteByField(String indexName, String type, String field, Long startTime) throws IOException {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //时间过滤
        QueryBuilder timeQuery = QueryBuilders.rangeQuery(field).gte(startTime);

        QueryBuilder boolQuery = QueryBuilders.boolQuery().must(timeQuery);

        searchSourceBuilder.query(boolQuery).size(MAX_SIZE);

        String query = searchSourceBuilder.toString();
        System.out.println(query);

        Search search = new Search.Builder(query)
                .addIndex(indexName)
                .addType(type)
                .build();

        SearchResult result = jestClient.execute(search);
        return result;
    }

    /**
     * 获取小于等于指定long类型列节点数据
     *
     * @param indexName
     * @param type
     * @return
     * @throws IOException
     */
    public SearchResult getlteByField(String indexName, String type, String field, Long startTime) throws IOException {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //时间过滤
        QueryBuilder timeQuery = QueryBuilders.rangeQuery(field).lte(startTime);
        QueryBuilder boolQuery = QueryBuilders.boolQuery().must(timeQuery);
        searchSourceBuilder.query(boolQuery).size(MAX_SIZE);

        String query = searchSourceBuilder.toString();
        System.out.println(query);

        Search search = new Search.Builder(query)
                .addIndex(indexName)
                .addType(type)
                .build();

        SearchResult result = jestClient.execute(search);
        return result;
    }

    /**
     * 模糊检索(无时间过滤)
     *
     * @param indexName
     * @param type
     * @param keyWord
     * @param field
     * @return
     * @throws IOException
     */
    public SearchResult blurSearch(String indexName, String type, String field, String keyWord) throws IOException {
        /*//方式一
        QueryBuilder queryBuilder = QueryBuilders.fuzzyQuery(field+".keyword", keyWord);
        Search search = new Search.Builder(queryBuilder.toString()).addIndex(indexName).addType(type).build();
        //方式二
        //Term term=new Term(field+".keyword", "*"+keyWord+"*");
        WildcardQuery query=new WildcardQuery(term);
        Search search = new Search.Builder(query.toString()).addIndex(indexName).addType(type).build();
        //方式三
        QueryBuilder queryBuilder = QueryBuilders.constantScoreQuery(QueryBuilders.termQuery(field+".keyword", "*"+keyWord+"*"));
        Search search = new Search.Builder(
                queryBuilder.toString())
                .addIndex(indexName)
                .addType(type).build();
        //方式四
        WildcardQueryBuilder queryBuilder = QueryBuilders.wildcardQuery(field+".keyword", "*"+keyWord+"*");
        Search search = new Search.Builder(queryBuilder.toString())
                .addIndex(indexName)
                .addType(type).build();*/
        //方式五：查询query（用API进行查询是对应视图工具上的json参数进行查询）
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //QueryBuilder queryBuilder = QueryBuilders.wildcardQuery(field+".keyword", "*"+keyWord+"*");
        QueryBuilder queryBuilder = QueryBuilders.wildcardQuery(field, "*" + keyWord + "*");
        //QueryBuilder queryBuilder = QueryBuilders.matchQuery(field,keyWord);
        searchSourceBuilder.query(queryBuilder).size(MAX_SIZE);
        String query = searchSourceBuilder.toString();
        System.out.println(query);

        Search search = new Search.Builder(query)
                .addIndex(indexName)
                .addType(type)
                .build();

        return jestClient.execute(search);
    }

    /**
     * @param indexName
     * @param type
     * @param query
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T extends ESDoc> List<T> scanAndScrollSearch(String indexName, String type, String query, Class<T> clazz)
            throws IOException {
        List<T> ret = new ArrayList<T>();
        Search search = new Search.Builder(query)
                .addIndex(indexName)
                .addType(type)
                .setParameter(Parameters.SIZE, BATCH_SIZE)
                .setParameter(Parameters.SCROLL, SCROLL)
                .build();
        SearchResult searchResult = jestClient.execute(search);
        if (!searchResult.isSucceeded()) {
            logger.error(searchResult.getErrorMessage());
            return ret;
        }
        String scrollId = searchResult.getJsonObject().get("_scroll_id").getAsString();

        ElasticSearchResult esr = new Gson().fromJson(searchResult.getJsonString(), ElasticSearchResult.class);
        logger.info("ES 搜索花费{}毫秒,超时:{},分片总数:{}, 成功执行的分片数:{},跳过的分片数:{},失败的分片数:{},命中数目:{},命中最高得分:{},本次取得{}条", esr.getTook(), esr.getTimeOut(), esr.getShards().getTotal(), esr.getShards().getSuccessful(), esr.getShards().getSkipped(), esr.getShards().getFailed(), esr.getHits().getTotal(), esr.getHits().getMaxScore(), esr.getHits().getHits().size());
        List<T> curList = searchResult.getSourceAsObjectList(clazz, false);
        int curPageSize = curList.size();
        ret.addAll(curList);
        while (curPageSize != 0 && ret.size() < MAX_SIZE) {
            SearchScroll scrollSearch = new SearchScroll.Builder(scrollId, SCROLL).build();
            JestResult scrollResult = jestClient.execute(scrollSearch);
            scrollId = scrollResult.getJsonObject().get("_scroll_id").getAsString();

            esr = new Gson().fromJson(scrollResult.getJsonString(), ElasticSearchResult.class);
            logger.info("ES 搜索花费{}毫秒,超时:{},分片总数:{}, 成功执行的分片数:{},跳过的分片数:{},失败的分片数:{},命中数目:{},命中最高得分:{},本次取得{}条", esr.getTook(), esr.getTimeOut(), esr.getShards().getTotal(), esr.getShards().getSuccessful(), esr.getShards().getSkipped(), esr.getShards().getFailed(), esr.getHits().getTotal(), esr.getHits().getMaxScore(), esr.getHits().getHits().size());
            curList = scrollResult.getSourceAsObjectList(clazz, false);
            curPageSize = curList.size();
            ret.addAll(curList);
        }
        return ret;
    }

    /**
     * @param index
     * @param type
     * @param id
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends ESDoc> T getESDoc(String index, String type, String id, Class<T> clazz) {
        try {
            JestResult result = getDocument(index, type, id);
            if (result.isSucceeded()) {
                T doc = result.getSourceAsObject(clazz);
                return doc;
            }
        } catch (IOException e) {
            logger.error("从ES读取{}:{}:{}失败 {}[{}]", index, type, id, e.getMessage(), e.getStackTrace());
        }
        return null;
    }

    /**
     * 搜索获得文档
     *
     * @param index
     * @param type
     * @param query
     * @param clazz
     * @return
     */
    public <T extends ESDoc> Pagination<T> searchPage(String index, String type, String query, Class<T> clazz) {
        Pagination<T> ret = null;
        try {
            SearchResult result = simpleSearch(index, type, query);
            if (result.isSucceeded()) {
                ElasticSearchResult esr = new Gson().fromJson(result.getJsonString(), ElasticSearchResult.class);
                logger.info("ES 搜索花费{}毫秒,超时:{},分片总数:{}, 成功执行的分片数:{},跳过的分片数:{},失败的分片数:{},命中数目:{},命中最高得分:{}", esr.getTook(), esr.getTimeOut(), esr.getShards().getTotal(), esr.getShards().getSuccessful(), esr.getShards().getSkipped(), esr.getShards().getFailed(), esr.getHits().getTotal(), esr.getHits().getMaxScore());
                if (esr.getShards().getFailed() == 0) {
                    ret = new Pagination<T>();
                    ret.setList(result.getSourceAsObjectList(clazz, false));
                    ret.setTotalSize(esr.getHits().getTotal());
                } else {
                    logger.error("搜索错误信息:{}", new Gson().toJson(esr.getShards().getFailures()));
                }
            } else {
                logger.error("ES查询失败{}[{}]", result.getErrorMessage(), result.getJsonString());
            }
        } catch (IOException e) {
            logger.error("搜索ES失败 {}[{}]", e.getMessage(), e.getStackTrace());
        }
        return ret;
    }

    public String generateQuery() {
        SearchSourceBuilder builder = new SearchSourceBuilder();

        TermQueryBuilder termQuery = QueryBuilders.termQuery("beat.hostname", "bjzw_99_138");
        TermsQueryBuilder termsQuery = QueryBuilders
                .termsQuery("response_code", "301", "302", "404", "500");
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("response_duration").gte(0).lte(1);
        RangeQueryBuilder rangeQuery1 = QueryBuilders.rangeQuery("response_duration").gte(2).lte(3);
        RangeQueryBuilder rangeQuery2 = QueryBuilders.rangeQuery("response_duration").gte(4).lte(5);
        RangeQueryBuilder rangeQuery3 = QueryBuilders.rangeQuery("response_duration").gte(8).lte(10);
        BoolQueryBuilder shouldQuery = QueryBuilders.boolQuery().should(rangeQuery1).should(rangeQuery2).should(rangeQuery3);
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery().filter(termQuery).filter(termsQuery).mustNot(rangeQuery).filter(shouldQuery);
        return builder.query(boolQuery).toString();
    }

}