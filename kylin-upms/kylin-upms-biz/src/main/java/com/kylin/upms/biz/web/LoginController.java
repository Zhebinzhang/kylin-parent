package com.kylin.upms.biz.web;


import com.kylin.upms.biz.vo.ResEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


    @CrossOrigin(origins = {"http://localhost:8080"})
  @RequestMapping("/login_p")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResEntity loginP(){
        return ResEntity.error("尚未登录");
    }
}
