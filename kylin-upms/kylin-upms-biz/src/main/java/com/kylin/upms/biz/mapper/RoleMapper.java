package com.kylin.upms.biz.mapper;

import com.kylin.upms.biz.entity.Role;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mht
 * @since 2019-09-15
 */
public interface RoleMapper extends BaseMapper<Role> {

     List<Role> getRoleByUserName(String userName);

     //List<Role> getRolesList();
}
