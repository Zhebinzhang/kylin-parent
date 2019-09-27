package com.kylin.upms.biz.service;

import com.kylin.upms.biz.entity.Menu;
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
public interface IMenuService extends IService<Menu> {

    List<Menu> getMenuAll();
    List<Menu> getMenuBuUserID(String username);
    List<Menu> getAllMenu();

}
