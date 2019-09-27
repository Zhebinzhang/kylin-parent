package com.kylin.upms.biz.service.impl;

import com.kylin.upms.biz.entity.Menu;
import com.kylin.upms.biz.mapper.MenuMapper;
import com.kylin.upms.biz.service.IMenuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mht
 * @since 2019-09-15
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> getMenuAll() {
        return this.baseMapper.getMenuAll();
    }

    @Override
    public List<Menu> getMenuBuUserID(String username) {
        return this.baseMapper.getMenuBuUserID(username);
    }

    @Override
    public List<Menu> getAllMenu() {
        return this.baseMapper.getAllMenu();
    }
}
