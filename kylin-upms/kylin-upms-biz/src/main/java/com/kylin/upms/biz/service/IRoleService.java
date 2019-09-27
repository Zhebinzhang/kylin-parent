package com.kylin.upms.biz.service;

import com.kylin.upms.biz.entity.Role;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mht
 * @since 2019-09-15
 */
public interface IRoleService extends IService<Role> {
    List<Role> getRoleByUserName(String userName);
    //List<Role> getRolesList();
}
