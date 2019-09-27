package com.kylin.upms.biz.config.security;

import com.kylin.upms.biz.entity.Menu;
import com.kylin.upms.biz.entity.Role;
import com.kylin.upms.biz.service.IMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@RefreshScope
@Component
public class KylinFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    IMenuService iMenuService;

    @Value(value = "${urls}")
    private   String urls;

    AntPathMatcher antPathMatcher = new AntPathMatcher();
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
      logger.info("url:{}",urls);
       //获取请求的url
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        if (Arrays.asList(urls.split(",")).contains(requestUrl)){
            return SecurityConfig.createList("ROLE_LOGIN");
        }
        logger.debug("请求的url为:{}",requestUrl);
        //获取 数据库中所有的url  和url 对应的角色
        List<Menu> menuAll = iMenuService.getMenuAll();
        //根据 请求的url 和数据库中所有的url 进行匹配
        for (Menu menu:menuAll){
            logger.debug("数据库中的url是:{},请求的url为:{}",menu.getUrl(),requestUrl);
            if (antPathMatcher.match(menu.getUrl(),requestUrl) && !CollectionUtils.isEmpty(menu.getRoleList())){
                //如果匹配成功则把对应的角色信息放入到上下文中
                String[] roles = new String[menu.getRoleList().size()];
                int i = 0;
                for (Role role : menu.getRoleList()) {
                    roles[i] = role.getName();
                    i++;
                }
                logger.debug("url:{}对应的角色信息为:{}",requestUrl, Arrays.toString(roles));
                return SecurityConfig.createList(roles);
            }
        }
        logger.debug("请求的url：{}，没有匹配上角色信息,默认角色为:{}",requestUrl,"ROLE_NONE");
        return SecurityConfig.createList("ROLE_NONE");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
