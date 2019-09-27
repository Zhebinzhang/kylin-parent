package com.kylin.upms.biz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kylin.upms.biz.entity.Role;
import com.kylin.upms.biz.entity.User;
import com.kylin.upms.biz.entity.UserSecurity;
import com.kylin.upms.biz.mapper.UserMapper;
import com.kylin.upms.biz.service.IRoleService;
import com.kylin.upms.biz.service.IUserService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mht
 * @since 2019-09-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService, UserDetailsService {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    IRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        logger.info("用户名为:{}",s);
        EntityWrapper wrapper = new EntityWrapper(new User());
        wrapper.eq("username",s);
        User user = this.selectOne(wrapper);
        if (user==null){
            throw  new UsernameNotFoundException("用户不存在");
        }
        List<Role> roleByUserName = roleService.getRoleByUserName(s);
        String[] roles = new String[roleByUserName.size()];
        int i = 0;
        for (Role role:roleByUserName){
            roles[i] = role.getName();
            i++;
        }

        UserSecurity userSecurity = new UserSecurity(s,user.getPassword(), AuthorityUtils.createAuthorityList(roles));
        return userSecurity;
    }


    //高亮
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public AggregatedPage<User> selectuser(Integer pageNum,String key) {
        // 创建Pageable对象
        Pageable pageable = PageRequest.of(pageNum, 3, Sort.by(Sort.Direction.ASC, "id"));

        // 高亮拼接的前缀与后缀
        String preTags = "<font color=\"red\">";
        String postTags = "</font>";

        // 查询内容
        String username =key;
        if(null==key || "".equals(key)){
            // 创建SearchQuery对象
            SearchQuery query = new NativeSearchQueryBuilder().withPageable(pageable).build();
            // 执行分页查询
            AggregatedPage<User> pageInfo = elasticsearchTemplate.queryForPage(query, User.class);
            return  pageInfo;

        }
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
        return  pageInfo;
    }
}