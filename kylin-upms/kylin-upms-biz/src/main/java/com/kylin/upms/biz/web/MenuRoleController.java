package com.kylin.upms.biz.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kylin.upms.biz.dto.UserDto;
import com.kylin.upms.biz.entity.MenuRole;
import com.kylin.upms.biz.service.IMenuRoleService;
import com.kylin.upms.biz.vo.ResEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
@RequestMapping("/menuRole")
public class MenuRoleController {

    @Autowired
    IMenuRoleService iMenuRoleService;
    @RequestMapping(method = RequestMethod.GET,value = "/MenuById")
    public ResEntity roleById(UserDto userDto){
        Integer id = userDto.getId();
        List<Integer> list=new ArrayList<>();
        if(null!=id&&!"".equals(id)){
            EntityWrapper<MenuRole> wrapper = new EntityWrapper<>();
            wrapper.eq("rid",id);
            List<MenuRole> menuRoles = iMenuRoleService.selectList(wrapper);
            menuRoles.forEach(menuRole -> list.add(menuRole.getMid()));
            System.out.println(list);
            return  ResEntity.ok(list);
        }
        return ResEntity.ok(list);
    }
}
