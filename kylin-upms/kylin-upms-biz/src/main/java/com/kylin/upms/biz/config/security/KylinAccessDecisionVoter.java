package com.kylin.upms.biz.config.security;

import com.alibaba.fastjson.JSON;
import com.kylin.upms.biz.exception.LoginNoneException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;


@Component
public class KylinAccessDecisionVoter implements AccessDecisionManager {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
            //collection :数据过滤器中传过来的 请求的url 对应的角色信息
        if (authentication instanceof AnonymousAuthenticationToken){
            throw  new LoginNoneException("尚未登录");
        }
        logger.debug("当前登录的角色信息为：{}", JSON.toJSONString(authentication.getAuthorities()));
        Iterator<ConfigAttribute> iterator = collection.iterator();
        while (iterator.hasNext()){
            ConfigAttribute next = iterator.next();
            if (next.getAttribute().equalsIgnoreCase("ROLE_LOGIN")){
                logger.debug("放行的角色：{}", "ROLE_LOGIN");
                return;
            }
            if (next.getAttribute().equalsIgnoreCase("ROLE_NONE")){
                throw  new AccessDeniedException("您没有配置url，请联系开发人员配置url");
            }
            //  authentication 认证成功后的角色信息
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                // 如果能够匹配上 则放行 如果匹配不上则提示权限不足
                if (next.getAttribute().equalsIgnoreCase(authority.getAuthority())){
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足，请联系管理员");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
