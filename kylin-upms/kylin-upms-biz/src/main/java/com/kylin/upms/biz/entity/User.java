package com.kylin.upms.biz.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

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
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * hrID
     */
    @Id
    @TableId(value = "id", type = IdType.AUTO)
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
     * 住宅电话
     */
    @Field(index = true,analyzer = "ik_max_word")
    private String telephone;

    /**
     * 联系地址
     */
    @Field(index = true,analyzer = "ik_max_word")
    private String address;
    @Field(index = true,analyzer = "ik_max_word")
    private Integer enabled;

    /**
     * 用户名
     */
    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String username;

    /**
     * 密码
     */
    @Field(index = true,analyzer = "ik_max_word")
    private String password;
    @Field(index = true,analyzer = "ik_max_word")
    private String userface;
    @Field(index = true,analyzer = "ik_max_word")
    private String remark;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
