package com.kylin.upms.biz.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.kylin.upms.biz.entity.Menu;
import com.kylin.upms.biz.entity.Role;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: zhebin
 * @Date: 2019/9/20 23:40
 */
public class MenuDto extends Page{
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

}
