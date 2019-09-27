package com.kylin.upms.biz.web;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kylin.upms.biz.entity.Menu;
import com.kylin.upms.biz.entity.UserSecurity;
import com.kylin.upms.biz.service.IMenuService;
import com.kylin.upms.biz.vo.ResEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mht
 * @since 2019-09-15
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IMenuService iMenuService;

    @RequestMapping("/getMenuByUserID")
   public ResEntity getMenuByUserID(){
        logger.info(JSON.toJSONString(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        UserSecurity user = (UserSecurity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Menu> menus = iMenuService.getMenuBuUserID(user.getUsername());
       return ResEntity.ok(menus);
   }

    @RequestMapping("/getMenusList")
    public ResEntity get(){
        logger.info(JSON.toJSONString(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        UserSecurity user = (UserSecurity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Menu> menus = iMenuService.getMenuBuUserID(user.getUsername());
        return ResEntity.ok(menus);
    }


    //three
    @RequestMapping(value = "/getAllM")
    public ResEntity getAllM(){
        List<Menu> menuAll = iMenuService.getAllMenu();
        return ResEntity.ok(menuAll);
    }
}
