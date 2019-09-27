package com.kyllin.upms.search.entity;


import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author Mht
 * @since 2019-09-15
 */
@Data
@Accessors(chain = true)
@Document(indexName="usersearch",type="user")
public class User implements  Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * hrID
     */
    @Id
    private Integer id;

    /**
     * 姓名
     */
    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String name;

    /**
     * 手机号码
     */
    @Field(index = true,analyzer = "ik_max_word")
    private String phone;


    /**
     * 用户名
     */
    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String username;




    private String userface;






}
