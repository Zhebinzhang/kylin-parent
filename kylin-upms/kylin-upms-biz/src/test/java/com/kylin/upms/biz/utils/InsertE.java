package com.kylin.upms.biz.utils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kylin.upms.biz.entity.User;
import com.kylin.upms.biz.service.IUserService;
import com.kylin.upms.biz.service.impl.UserServiceImpl;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InsertE {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void test_selectObjects() {

        List<User> list = iUserService.selectList(new EntityWrapper<>());
        for (User user : list) {
            IndexQuery query = new IndexQueryBuilder().withId(user.getId().toString()).withObject(user).build();
            elasticsearchTemplate.index(query);
        }

    }

    @Test
    public void liuzhitong() {
        // 创建Pageable对象
        Pageable pageable = PageRequest.of(1, 3, Sort.by(Sort.Direction.ASC, "id"));

        // 高亮拼接的前缀与后缀
        String preTags = "<font color=\"red\">";
        String postTags = "</font>";

        // 查询内容
        String username = "刘";
        String fieldNames[] = { "name", "username" };

        // 创建queryBuilder对象
        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(username, fieldNames);

        // 创建SearchQuery对象
        SearchQuery query = new NativeSearchQueryBuilder().withQuery(queryBuilder)
                .withHighlightFields(new HighlightBuilder.Field(fieldNames[0]).preTags(preTags).postTags(postTags),
                        new HighlightBuilder.Field(fieldNames[1]).preTags(preTags).postTags(postTags))
                .withPageable(pageable).build();

        // 执行分页查询
        AggregatedPage<User> pageInfo = elasticsearchTemplate.queryForPage(query, User.class,
                new SearchResultMapper() {

                    @Override
                    public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz,
                                                            Pageable pageable) {

                        //定义查询出内容存储的集合
                        List<User> content = new ArrayList<>();

                        // 获取高亮的结果
                        SearchHits searchHits = response.getHits();
                        // 判断高亮结果
                        if (searchHits != null) {
                            // 获取高亮中所有内容
                            SearchHit[] hits = searchHits.getHits();
                            // 判断结果是否大于0
                            if (hits.length > 0) {

                                for (SearchHit hit : hits) {
                                    User user = new User();

                                    // 高亮结果的主键id值
                                    String id = hit.getId();
                                    // 主键id
                                    user.setId(Integer.parseInt(id));

                                    // 获取第一个字段的高亮内容
                                    HighlightField highlightField1 = hit.getHighlightFields().get(fieldNames[0]);

                                    if (highlightField1 != null) {
                                        String hight_value1 = highlightField1.getFragments()[0].toString();
                                        user.setName(hight_value1);
                                    }else {
                                        String value = (String) hit.getSourceAsMap().get(fieldNames[0]);
                                        user.setName(value);
                                    }

                                    // 获取第二个字段的高亮内容
                                    HighlightField highlightField2 = hit.getHighlightFields().get(fieldNames[1]);
                                    if (highlightField2 != null) {
                                        String hight_value2 = highlightField2.getFragments()[0].toString();
                                        user.setUsername(hight_value2);
                                    }else {
                                        String value = (String) hit.getSourceAsMap().get(fieldNames[0]);
                                        user.setUsername(value);
                                    }

                                    content.add(user);
                                }
                            }
                        }


                        return new AggregatedPageImpl<>((List<T>)content);

                    }
                });


        // 查询结果
        System.out.println("当前页" + pageInfo.getNumber() + "@@@总记录数" + pageInfo.getTotalElements() + "@@@总页数"
                + pageInfo.getTotalPages());

        // 当前页数据
        List<User> list = pageInfo.getContent();
        list.forEach(System.out::println);


    }

}
