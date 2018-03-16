package com.wjl;


import com.wjl.domain.*;
import com.wjl.repository.*;
import org.apache.lucene.search.join.ScoreMode;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.lucene.search.function.FiltersFunctionScoreQuery;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortMode;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ScrolledPage;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EstestApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println("hello world");
    }

    @Autowired
    private ArticleSearchRepository articleSearchRepository;

    @Autowired
    private PeopleRepository peopleRepository;


    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    NestTestRepository nestTestRepository;


//
//    @Test
//    public void pc(){
//        client.index("").
//    }


    @Autowired
    ParentsRepository parentsRepository;

    @Test
    public void repositoryParentSave() {
        Parents parents1 = new Parents();
        parents1.setId(1);
        parents1.setAge(1);
        parents1.setName("p1");

        Parents parents2 = new Parents();
        parents2.setId(2);
        parents2.setAge(2);
        parents2.setName("p2");
        List<Parents> parentsList = new ArrayList<>();

        parentsList.add(parents1);
        parentsList.add(parents2);
        parentsRepository.saveAll(parentsList);
    }

    @Autowired
    Child1Repository child1Repository;

    @Autowired
    Child2Repository child2Repository;

    @Test
    public void repositoryChildSave() {
        Child1 child1 = new Child1();
        child1.setParentId("1");
        child1.setId(2);
        child1.setAge(2);
        child1.setChildName("c12");

        child1Repository.save(child1);

        Child2 child2 = new Child2();
        child2.setParentId("2");
        child2.setId(3);
        child2.setAge(3);
        child2.setChildName("c22");

        child2Repository.save(child2);
    }


    @Test
    public void haschild() {
        QueryBuilder hsc = hasChildQuery("employee", rangeQuery("dob").gte("1980-01-01"), ScoreMode.None);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(hsc);
        nativeSearchQueryBuilder.withIndices("company").withTypes("branch");
        List<Branch> branches = elasticsearchTemplate.queryForList(nativeSearchQueryBuilder.build(), Branch.class);
        System.out.println(branches);
    }


    @Test
    public void childSave() {
        Employee employee = new Employee();
        employee.setName("haha");
        employee.setDob("1992-04-02");
        employee.setHobby("dsdsff");
//        Map<String, String> source=new HashMap<>();
//        source.put("name","ceshiname");
//        source.put("dob","1990-08-01");
//        source.put("hobby","ceshihobby");
//       IndexResponse indexResponse =client.prepareIndex("company","employee","10").setParent("paris").setSource(source).execute().actionGet();

        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setIndexName("company");
        indexQuery.setType("employee");
        indexQuery.setId("11");
        indexQuery.setObject(employee);
        indexQuery.setParentId("paris");
        elasticsearchTemplate.index(indexQuery);
    }

    @Test
    public void nestTest() {
        List<GameUser> gameUserList = new ArrayList<>();
        GameUser gameUser1 = new GameUser();
        gameUser1.setGameName("账号1");
        gameUser1.setGameLevel(1);
        gameUser1.setProfession("牧师");
        gameUserList.add(gameUser1);
        GameUser gameUser2 = new GameUser();
        gameUser2.setGameName("账号2");
        gameUser2.setGameLevel(3);
        gameUser2.setProfession("法师");
        gameUserList.add(gameUser2);
        GameUser gameUser3 = new GameUser();
        gameUser3.setGameName("账号3");
        gameUser3.setGameLevel(15);
        gameUser3.setProfession("术士");
        gameUserList.add(gameUser3);
        NestTest nestTest = new NestTest();
        nestTest.setId(2L);
        nestTest.setUserName("崔斯特");
        nestTest.setGameUserList(gameUserList);
        nestTestRepository.save(nestTest);
    }

    @Test
    public void nestQuery() {
        BoolQueryBuilder boolQueryBuilder = boolQuery();
        //    BoolFilterBuilder boolFilterBuilder = boolFilter();
        //    boolQueryBuilder.must(nestedQuery("gameUserList",termQuery("gameUserList.gameName", "账号1"),ScoreMode.None));
        //  boolFilterBuilder.must(nestedFilter("gameUserList", boolFilter().must(termFilter("gameUserList.gameName", "账号1"))));
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //  nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        SortBuilder sortBuilder = null;
        sortBuilder = SortBuilders.fieldSort("gameUserList.gameLevel").setNestedPath("gameUserList").setNestedFilter(termQuery("gameUserList.gameName", 3)).order(SortOrder.DESC).sortMode(SortMode.SUM);
        nativeSearchQueryBuilder.withSort(sortBuilder);
        List<NestTest> nestTests = elasticsearchTemplate.queryForList(nativeSearchQueryBuilder.withIndices("nestindex").withTypes("nesttype").build(), NestTest.class);
        System.out.println(nestTests);
        //elasticsearchTemplate.bulkIndex();
    }

    @Test
    public void templateSave() {
//        CriteriaQuery criteriaQuery = new CriteriaQuery(new Criteria());
//        criteriaQuery.addIndices("report");
//        criteriaQuery.addTypes("storereport");
//        criteriaQuery.setPageable(new PageRequest(0, 10));
//        Sort sort = new Sort(Sort.Direction.ASC, "totalGoods");
//        criteriaQuery.addSort(sort);
//
//        List<EsDailyStoreReport> esDailyStoreReports = elasticsearchTemplate.queryForList(criteriaQuery, EsDailyStoreReport.class);


        Tutorial tutorial = new Tutorial();
        //tutorial.setId(5L);
        tutorial.setName("ceshisss");
        IndexQuery query = new IndexQuery();
        query.setId("1");
        query.setIndexName("test");
        query.setType("test");
        query.setObject(tutorial);
        elasticsearchTemplate.index(query);
//
//		List<IndexQuery> queries=new ArrayList<>();
//		for(int i=0;i<10;i++){
//			IndexQuery  query=new IndexQuery();
//		//	query.setId(String.valueOf(i));
//			query.setObject(tutorial);
//			queries.add(query);
//		}
//	elasticsearchTemplate.bulkIndex(queries);
        //elasticsearchTemplate.delete("test","test","1");
        //elasticsearchTemplate.deleteIndex("tests");


    }


    @Test
    public void testSaveArticleIndex() {
        Author author = new Author();
        author.setId(2L);
        author.setName("tianshouzhi");
        author.setRemark("java developer");

        Tutorial tutorial = new Tutorial();
        //	tutorial.setId(2L);
        tutorial.setName("elastic search");

        List<Article> articles = new ArrayList<>();
        Article article = new Article();
//		article.setId("1");
        article.setTitle("ccc");
        article.setAbstracts("bbb");
        article.setTutorial(tutorial);
        article.setAuthor(author);
        article.setContent("2222222");
        long t = 149973977000l;

        article.setPostTime(new Date(t));
        article.setClickCount(1L);


        articles.add(article);
        Article article1 = article;
        articles.add(article1);
        articleSearchRepository.deleteAll();
        //	articleSearchRepository.deleteById(3L);
        // Iterable<Article> result = articleSearchRepository.saveAll(articles);

        //    Iterator iterator=result.iterator();
        //      System.out.print(11);

//        IndexQuery query=new IndexQuery();
//        query.setId("22");
////        query.setIndexName("test");
////        query.setType("test");
//        query.setObject(article);
//        elasticsearchTemplate.index(query);

    }

    @Test
    public void testSearch() {
        List<Article> articleList = articleSearchRepository.findByTitleLike("integreate");
        System.out.print(articleList.toString());
//		String queryString="springboot";//搜索关键字
//		QueryStringQueryBuilder builder=new QueryStringQueryBuilder(queryString);
//		Iterable<Article> searchResult = articleSearchRepository.search(builder);
//		Iterator<Article> iterator = searchResult.iterator();
//		while(iterator.hasNext()){
//			System.out.println(iterator.next());
//		}
    }

    @Autowired
    @Qualifier("esClient")
    TransportClient client;

//    @Before
//    @SuppressWarnings({"unchecked"})
//    public void before() throws UnknownHostException, InterruptedException, ExecutionException {
//        Settings esSettings = Settings.builder()
//                .put("cluster.name", "elasticsearch") //设置ES实例的名称
//                .put("client.transport.sniff", true) //自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中
//                .build();
//        client = new PreBuiltTransportClient(esSettings);//初始化client较老版本发生了变化，此方法有几个重载方法，初始化插件等。
//        //此步骤添加IP，至少一个，其实一个就够了，因为添加了自动嗅探配置
//        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
//    }


    @Test
    public void index() throws Exception {

        Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("name", "广告信息11");
        infoMap.put("title", "我的广告22");
        infoMap.put("createTime", new Date());
        infoMap.put("count", 1022);
        // IndexRequest  indexRequest=new IndexRequest("hi","childhi").parent("parenthi");
        client.admin().indices().preparePutMapping("orderindex").setType("orderproduct").setSource("{\n" +
                "    \"ordermain\": {},\n" +
                "    \"orderproduct\": {\n" +
                "      \"_parent\": {\n" +
                "        \"type\": \"ordermain\" \n" +
                "      }\n" +
                "    }\n" +
                "  }").get();

//        IndexResponse indexResponse = client.prepareIndex("test", "info", "100").setSource(infoMap).execute().actionGet();
//        System.out.println("id:" + indexResponse.getId());


//        XContentBuilder mapping = jsonBuilder()
//                .startObject("orderproduct")
//                .startObject("_parent").field("type", "ordermain").endObject()
//                .endObject();
//        PutMappingRequest mappingRequest = Requests.putMappingRequest("orderindex").type("orderproduct").source(mapping);
//        client.admin().indices().putMapping(mappingRequest).actionGet();


    }


    @Test
    public void get() throws Exception {
        try {
            GetResponse response = client.prepareGet("test", "info", "100")
                    .execute().actionGet();
            System.out.println("response.getId():" + response.getId());
            System.out.println("response.getSourceAsString():" + response.getSourceAsString());
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }


    }


    @Test
    public void query() throws Exception {
        String startDay = "2017-07-02";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date startTime = simpleDateFormat.parse(startDay);
//        Long sjc = startTime.getTime();

        String endtDay = "2017-07-03";
//        Date endTime = simpleDateFormat.parse(endtDay);
//        Long ejc = endTime.getTime();

        // String storId = "00002";
        String storId = "";
        QueryBuilder rangeQueryBuilder;
        if (StringUtils.isEmpty(endtDay)) {
            if (StringUtils.isEmpty(storId)) {
                rangeQueryBuilder = QueryBuilders.termQuery("saleDate", startDay);
            } else {
                rangeQueryBuilder = QueryBuilders.boolQuery()
                        .must(QueryBuilders.termQuery("saleDate", startDay))
                        .must(QueryBuilders.termQuery("storeId", storId));
            }

        } else {
            if (StringUtils.isEmpty(storId)) {
                rangeQueryBuilder = QueryBuilders.rangeQuery("saleDate").gte(startDay).lte(endtDay);
            } else {
                rangeQueryBuilder = QueryBuilders.boolQuery()
                        .must(QueryBuilders.termQuery("storeId", storId))
                        .must(QueryBuilders.rangeQuery("saleDate").gte(startDay).lte(endtDay));
            }
        }
        TermsAggregationBuilder teamAgg = AggregationBuilders.terms("group_by_saleDate").field("saleDate").size(20).order(Terms.Order.aggregation("orderSum ", true));
        TermsAggregationBuilder storeAgg = AggregationBuilders.terms("group_by_store").field("storeId").size(20);
        SumAggregationBuilder orderSb = AggregationBuilders.sum("orderSum").field("totalOrder");
        SumAggregationBuilder saleSb = AggregationBuilders.sum("saleSum").field("totalSales");

        SearchRequestBuilder sbuilder = client.prepareSearch("report").setTypes("storereport");
        sbuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        sbuilder.addSort("totalSales", SortOrder.DESC);
        sbuilder.addSort("totalGoods", SortOrder.DESC);
        sbuilder.setQuery(rangeQueryBuilder);
        sbuilder.setSize(10);
        sbuilder.setFrom(0);

        sbuilder.addAggregation(saleSb).addAggregation(orderSb).addAggregation(teamAgg.subAggregation(saleSb).subAggregation(orderSb).subAggregation(storeAgg));

        SearchResponse searchResponse = sbuilder
                //  .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                //   .setQuery(rangeQueryBuilder)
                //    .addSort("totalGoods", SortOrder.DESC)
                // .setFrom(0)
                //   .setSize(5)
                //   .addAggregation(saleSb)
                //    .addAggregation(orderSb)
                //     .addAggregation(teamAgg.subAggregation(saleSb).subAggregation(orderSb).subAggregation(storeAgg))
                .execute()
                .actionGet();
        SearchHits hits = searchResponse.getHits();
        Map<String, Aggregation> aggMap = searchResponse.getAggregations().asMap();
        double totalOrder = ((InternalSum) aggMap.get("orderSum")).getValue();
        //sum值获取方法
        double totalSales = ((InternalSum) aggMap.get("saleSum")).getValue();
        LongTerms longTerms = (LongTerms) aggMap.get("group_by_saleDate");
        Iterator<org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket> teamBucketIt = longTerms.getBuckets().iterator();
        while (teamBucketIt.hasNext()) {

            MultiBucketsAggregation.Bucket buck = teamBucketIt.next();
            //saleDate
            Long team = (Long) buck.getKey();
            //记录数
            long count = buck.getDocCount();
            //得到所有子聚合
            Map subaggmap = buck.getAggregations().asMap();
            //sum值获取方法
            double totalOrders = ((InternalSum) subaggmap.get("orderSum")).getValue();
            //sum值获取方法
            double totalSaless = ((InternalSum) subaggmap.get("saleSum")).getValue();

            StringTerms stringTerms = (StringTerms) subaggmap.get("group_by_store");
            Iterator<org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket> storeBucketIt = stringTerms.getBuckets().iterator();
            while (storeBucketIt.hasNext()) {
                MultiBucketsAggregation.Bucket store = storeBucketIt.next();
                //saleDate
                String storeId = (String) store.getKey();
                //记录数
                long storeDocCount = store.getDocCount();
            }
        }
        System.out.println("查到记录数：" + hits.getTotalHits());
        SearchHit[] searchHists = hits.getHits();
        if (searchHists.length > 0) {
            for (SearchHit hit : searchHists) {
                String storeIdAndName = (String) hit.getSource().get("storeIdAndName");
                Integer totalGoods = (Integer) hit.getSource().get("totalGoods");
//                Long saleDate = (Long) hit.getSource().get("saleDate");
//                Date sd = new Date(saleDate);
//                String sdstr = simpleDateFormat.format(sd);
                //System.out.format("storeIdAndName:%s ,saleDate:%s,totalGoods :%d \n", storeIdAndName, sdstr, totalGoods);
            }
        }
    }


    /**
     * 查看集群信息
     */
    @Test
    public void testInfo() {
        List<DiscoveryNode> nodes = client.connectedNodes();
        for (DiscoveryNode node : nodes) {
            System.out.println(node.getHostAddress());
        }
    }


    @Test
    public void delete() {

        // DeleteResponse response = client.prepareDelete("report", "storereport", "AV0Nlc5DpJ7hcj_S6Se3").get();

//
//        BulkByScrollResponse response =
//                DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
//                        .filter(QueryBuilders.termQuery("storeId", "00194"))
//                        .source("report")
//
//
//                        .get();
//
//        long deleted = response.getDeleted();

//        StringBuilder b = new StringBuilder();
//
//        b.append("{\"query\":{\"match_all\":{}}}");
//
//        DeleteByQueryRequestBuilder response = new DeleteByQueryRequestBuilder(client,DeleteByQueryAction.INSTANCE);
//        response.setIndices("report").setTypes("storereport").setSource(b.toString())
//
//                .execute()
//
//                .actionGet();


    }

    @Test
    public void update() throws IOException, ExecutionException, InterruptedException {
//        UpdateRequest updateRequest = new UpdateRequest();
//        updateRequest.index("report");
//        updateRequest.type("storereport");
//        updateRequest.id("AV0Nlc5DpJ7hcj_S6Se8");
//        updateRequest.doc(jsonBuilder()
//                .startObject()
//                .field("storeId", "00072")
//                .endObject());
//        client.update(updateRequest).get();

//        client.prepareUpdate("report", "storereport", "AV0Nlc5DpJ7hcj_S6Se8")
//                .setScript(new Script("ctx._source.storeId = \"00072\""  , ScriptService.ScriptType.INLINE, null, null))
//                .get();

        client.prepareUpdate("report", "storereport", "AV0Nlc5DpJ7hcj_S6Se8")
                .setDoc(jsonBuilder()
                        .startObject()
                        .field("storeId", "00071")
                        .endObject())
                .get();
    }

    @Test
    public void savePeople() {


        List<People> peopleList = new ArrayList<>();
        People people = new People();
        people.setName("小明");
        people.setAge(1);
        people.setBirthDay(new Date(2010, 9, 2));
        peopleList.add(people);

        People people1 = new People();
        people1.setName("小红");
        people1.setAge(2);
        people1.setBirthDay(new Date(2014, 3, 1));
        peopleList.add(people1);

        People people2 = new People();
        people2.setName("小刚");
        people2.setAge(3);
        people2.setBirthDay(new Date(2012, 11, 10));
        peopleList.add(people2);

        //   peopleRepository.saveAll(peopleList);
        // List<People> peopleList1=peopleRepository.findByName("小红");
        //  List<People> peopleList1=peopleRepository.findByNameLike("小");
        Pageable pageable = PageRequest.of(0, 1);
        AggregatedPage<People> peopleList1 = peopleRepository.findByName("小", pageable);
        List<People> people3 = peopleList1.getContent();
        long num = peopleList1.getTotalElements();

        System.out.print(peopleList1);

        // peopleRepository.deleteAll(peopleList1);
    }


    @Test
    public void scrollQuery() {
        //    peopleRepository.deleteAll();

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withIndices("goodreport")
                .withTypes("goodtype")
                .withPageable(new PageRequest(0, 1000))
                .build();
        //  String scrollId = elasticsearchTemplate.scan(searchQuery,1000,false);
        Page<EsDailyGoodReport> peoplePage = elasticsearchTemplate.startScroll(100000L, searchQuery, EsDailyGoodReport.class);
        List<EsDailyGoodReport> sampleEntities = new ArrayList<>();
        List<EsDailyGoodReport> content = peoplePage.getContent();
        sampleEntities.addAll(content);
        boolean hasRecords = true;
        while (hasRecords) {
            peoplePage = elasticsearchTemplate.continueScroll(((ScrolledPage) peoplePage).getScrollId(), 100000L, EsDailyGoodReport.class);
            List<EsDailyGoodReport> esDailyGoodReports = peoplePage.getContent();
            if (!CollectionUtils.isEmpty(esDailyGoodReports)) {
                sampleEntities.addAll(esDailyGoodReports);
                //  hasRecords = peoplePage.hasNext();
            } else {
                hasRecords = false;
            }
        }
    }


    public void setAlign() {
        Aligins aligins = new Aligins();
        aligins.setAges(10);
        aligins.setDate("2016");
        client.prepareIndex("algin", "info", "100").setSource();
    }


    @Test
    public void deleteQuery() {
        DeleteQuery dq = new DeleteQuery();
        QueryBuilder rangeQueryBuilder = QueryBuilders.termQuery("saleDate", "2017-06-26");
        dq.setQuery(rangeQueryBuilder);
        dq.setScrollTimeInMillis(100000L);
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.print("开始时间：" + sm.format(new Date()));
        elasticsearchTemplate.delete(dq, EsDailyStoreReport.class);
        System.out.print("结束时间：" + sm.format(new Date()));
    }

    @Autowired
    EsPlatformGoodsRepository esPlatformGoodsRepository;

    @Test
    public void updateQuery() throws IOException {
//        String id="1890";
//        String status="1";
//        UpdateQuery updateQuery=new UpdateQuery();
//        updateQuery.setIndexName("platgoods");
//        updateQuery.setType("platgoodstype");
//        updateQuery.setId(id);
//        UpdateRequest uRequest = new UpdateRequest();
//        uRequest.doc(jsonBuilder().startObject().field("status", status).endObject());
//        updateQuery.setUpdateRequest(uRequest);
//        elasticsearchTemplate.update(updateQuery);
        elasticsearchTemplate.refresh("platgoods");

//        Optional<EsPlatformGoods> esPlatformGoods=esPlatformGoodsRepository.findById(Integer.valueOf(id));
//        EsPlatformGoods esPlatformGoods1=esPlatformGoods.get();
//        esPlatformGoods1.setStatus(Integer.valueOf(status));
//        esPlatformGoodsRepository.save(esPlatformGoods1);

//        List<EsPlatformGoods>   esPlatformGoods=esPlatformGoodsRepository.findByIdIn(new Integer[]{1898,1899});
//System.out.println(esPlatformGoods);
    }

    @Test
    public void excel() throws IOException {

        //创建HSSFWorkbook对象
        Workbook wb = new XSSFWorkbook();
//创建HSSFSheet对象
        Sheet sheet = wb.createSheet("sheet0");
//创建HSSFRow对象
        Row row = sheet.createRow(0);
//创建HSSFCell对象
        Cell cell = row.createCell(0);
//设置单元格的值
        cell.setCellValue("单元格中的中文");
//输出Excel文件
        FileOutputStream output = new FileOutputStream("d:\\workbooks.xlsx");
        wb.write(output);
        output.flush();
    }


    @Test
    public void findAllGoodsCode() {
        try {


            NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
            TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("allgoods").field("sjGoodsCode").size(1000000);
            AggregatedPage<EsPlatformGoods> esPlatformGoodsAggregatedPage = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder
                    .withIndices("platgoods")
                    .withTypes("platgoodstype")
                    .addAggregation(termsAggregationBuilder)
                    .build(), EsPlatformGoods.class
            );
            Map<String, Aggregation> aggMap = esPlatformGoodsAggregatedPage.getAggregations().asMap();
            Aggregation aggregation = aggMap.get("allgoods");
            MultiBucketsAggregation multiBucketsAggregation = (MultiBucketsAggregation) aggregation;
            List<MultiBucketsAggregation.Bucket> buckets = (List<MultiBucketsAggregation.Bucket>) multiBucketsAggregation.getBuckets();
            List<String> goods = new ArrayList<>();
            buckets.forEach(bucket -> {
                goods.add(bucket.getKeyAsString());
            });
            System.out.print("1111");

        } catch (Exception ex) {

        }
    }


    public static <T, K> List<K> listcopy(List<T> srcList, Class<K> clazz) throws IllegalAccessException, InstantiationException {
        String typeNam = srcList.getClass().getTypeName();
        if (srcList instanceof ArrayList) {
            System.out.println("1111");
        }
        if (srcList.getClass().equals(ArrayList.class)) {
            System.out.println("2222");
        }
        if (srcList.getClass().equals(List.class)) {
            System.out.println("3333");
        }
        if (CollectionUtils.isEmpty(srcList)) {
            return null;
        }
        List<K> kList = new ArrayList<>();
        int length = srcList.size();
        for (int i = 0; i < length; i++) {
            T src = srcList.get(i);
            Map<String, Object> srcMap = new HashMap<String, Object>();
            Class<?> srcClass = src.getClass();
            for (; srcClass != Object.class; srcClass = srcClass.getSuperclass()) {
                Field[] srcFields = srcClass.getDeclaredFields();
                for (Field fd : srcFields) {
                    try {
                        fd.setAccessible(true);
                        srcMap.put(fd.getName(), fd.get(src)); //获取属性值
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            K dest = clazz.newInstance();
            Class<?> destClass = dest.getClass();
            for (; destClass != Object.class; destClass = destClass.getSuperclass()) {
                Field[] destFields = destClass.getDeclaredFields();
                String typeClass;
                for (Field fd : destFields) {
                    Object value = srcMap.get(fd.getName());
                    if (value == null) {
                        continue;
                    }
                    try {
                        typeClass = fd.getType().getSimpleName();

                        if (typeClass.equals("Integer")) {
                            value = Integer.parseInt(value.toString());
                        } else if (typeClass.equals("String")) {
                            value = value.toString();
                        }
                        fd.setAccessible(true);
                        fd.set(dest, value); //给属性赋值
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            kList.add(dest);
        }
        return kList;
    }


    public static void copy(Object src, Object dest) {

        Map<String, Object> srcMap = new HashMap<String, Object>();
        Field[] srcFields = src.getClass().getDeclaredFields();

        for (Field fd : srcFields) {
            try {
                fd.setAccessible(true);
                srcMap.put(fd.getName(), fd.get(src)); //获取属性值
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Field[] destFields = dest.getClass().getDeclaredFields();
        String typeClass;
        for (Field fd : destFields) {
            Object value = srcMap.get(fd.getName());
            if (value == null) {
                continue;
            }
            try {
                typeClass = fd.getType().getSimpleName();

                if (typeClass.equals("Integer")) {
                    value = Integer.parseInt(value.toString());
                } else if (typeClass.equals("String")) {
                    value = value.toString();
                }
                fd.setAccessible(true);
                fd.set(dest, value); //给属性赋值
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void copy() throws InstantiationException, IllegalAccessException {
        List<CopyOne> copyOneList = new ArrayList<>();
        CopyOne copyOne = new CopyOne();
        copyOne.setAge("1");
        copyOne.setName("haha");
        copyOne.setAges(22);
        copyOne.setDate("234323");
        Relation relation1 = new Relation();
        relation1.setAge(21);
        relation1.setName("hhhh");
        copyOne.setRelation(relation1);
        copyOneList.add(copyOne);
        CopyOne copyOne2 = new CopyOne();
        copyOne2.setAge("2");
        copyOne2.setName("hehe");
        copyOne2.setAges(33);
        copyOne2.setDate("5454654");
        Relation relation2 = new Relation();
        relation2.setAge(31);
        relation2.setName("eeeeee");
        copyOne2.setRelation(relation2);
        copyOneList.add(copyOne2);
        List<CopyTwo> copyTwoList = listcopy(copyOneList, CopyTwo.class);
//        copy(copyOneList, copyTwoList);
        System.out.println(copyTwoList);
    }


    @Test
    public void functionScore() {
        QueryBuilder queryBuilder = QueryBuilders.termQuery("name", "fruite");
        FunctionScoreQueryBuilder.FilterFunctionBuilder[] filterFunctionBuilders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[3];
        filterFunctionBuilders[0] = new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.termQuery("isnew", "1"), ScoreFunctionBuilders.weightFactorFunction(3));
        filterFunctionBuilders[1] = new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.existsQuery("promotion"), ScoreFunctionBuilders.weightFactorFunction(5));
        filterFunctionBuilders[2] = new FunctionScoreQueryBuilder.FilterFunctionBuilder(ScoreFunctionBuilders.scriptFunction("double  sales=doc['sales'].value; return 2*(sales/(sales+1));"));
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(queryBuilder, filterFunctionBuilders).scoreMode(FiltersFunctionScoreQuery.ScoreMode.SUM);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withIndices("sp").withTypes("xx").withQuery(functionScoreQueryBuilder).withSearchType(SearchType.QUERY_THEN_FETCH);
        AggregatedPage<XX> agg = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(), XX.class);
        List<XX> xx = agg.getContent();
        System.out.println(xx);


    }

}



