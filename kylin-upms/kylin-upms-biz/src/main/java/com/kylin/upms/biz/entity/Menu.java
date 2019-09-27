package com.kylin.upms.biz.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;
import java.util.List;


import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class Menu extends Model<Menu> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String url;

    private String path;

    private String component;

    private String name;

    @TableField("iconCls")
    private String iconCls;

    @TableField("keepAlive")
    private Integer keepAlive;

    @TableField("requireAuth")
    private Integer requireAuth;

    @TableField("parentId")
    private Integer parentId;

    private Integer enabled;

    @TableField("create_time")
    private Date createTime;

    @TableField(exist = false) //表示该属性不为数据库表字段，但又是必须使用的。
    private  List<Role> roleList;

    @TableField(exist = false) //表示该属性不为数据库表字段，但又是必须使用的。
    private List<Menu> menuList;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
