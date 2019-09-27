package com.kylin.upms.biz.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @Author: zhebin
 * @Date: 2019/9/24 14:04
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(indexName = "item",type = "docs", shards = 1, replicas = 0)
public class Item {

    /**
     * @Description: @Id注解必须是springframework包下的
     * org.springframework.data.annotation.Id
     *@Author: https://blog.csdn.net/chen_2890
     */
    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title; //标题

    @Field(type = FieldType.Keyword)
    private String category;// 分类

    @Field(type = FieldType.Keyword)
    private String brand; // 品牌

    @Field(type = FieldType.Double)
    private Double price; // 价格

    @Field(index = false, type = FieldType.Keyword)
    private String images; // 图片地址
}