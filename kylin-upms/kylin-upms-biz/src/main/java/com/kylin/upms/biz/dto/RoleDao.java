package com.kylin.upms.biz.dto;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.kylin.upms.biz.entity.Role;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: zhebin
 * @Date: 2019/9/19 21:23
 */
@Data
@Accessors(chain = true)
public class RoleDao extends Page {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;

    /**
     * 角色名称
     */
    @TableField("nameZh")
    private String nameZh;
}
