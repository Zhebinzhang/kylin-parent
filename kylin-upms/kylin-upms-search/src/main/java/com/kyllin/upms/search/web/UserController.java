package com.kyllin.upms.search.web;


import com.kyllin.upms.search.dto.UserDto;
import com.kyllin.upms.search.entity.User;
import com.kyllin.upms.search.service.UserServiceImpl;
import com.kyllin.upms.search.vo.ResEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @CrossOrigin(origins = {"http://localhost:8080"})
    @RequestMapping(method = RequestMethod.GET,value = "/page1")
    public ResEntity get(UserDto userDto){
        AggregatedPage<User> selectuser = userService.selectuser(userDto.getPageNum()-1, userDto.getUsername());
        System.out.println(userDto.getUsername());
        System.out.println(userDto.getPageNum());
        System.out.println("77777777"+selectuser.getNumber()+"======"+selectuser.getTotalElements());
        return ResEntity.ok("查询成功",selectuser);
    }
}
