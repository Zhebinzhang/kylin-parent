package com.kylin.upms.biz.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
@TableName("user_role")
@NoArgsConstructor
public class UserRole extends Model<UserRole> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer uid;

    private Integer rid;

    public UserRole(Integer uid, Integer rid) {
        this.uid = uid;
        this.rid = rid;
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
