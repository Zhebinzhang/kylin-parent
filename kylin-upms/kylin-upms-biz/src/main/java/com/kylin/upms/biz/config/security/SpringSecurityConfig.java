package com.kylin.upms.biz.config.security;


import com.alibaba.fastjson.JSON;
import com.kylin.upms.biz.exception.LoginNoneException;
import com.kylin.upms.biz.vo.ResEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
  * sheng
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig  extends WebSecurityConfigurerAdapter{

    @Autowired
    KylinAccessDecisionVoter kylinAccessDecisionVoter;

    @Autowired
    KylinFilterInvocationSecurityMetadataSource kylinFilterInvocationSecurityMetadataSource;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/login_p");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().
                withObjectPostProcessor(new FilterSecurityInterceptorObjectPostProcessor()).
                anyRequest().authenticated().antMatchers("/login_p","/logout","/login").permitAll().and().
                formLogin().loginPage("/login_p").loginProcessingUrl("/oauth/login").permitAll().
                successHandler(new MyAuthenticationSuccessHandler()).
                failureHandler(new MyAuthenticationFailureHandler()).permitAll().
                and().logout().logoutUrl("/my/logout").
                addLogoutHandler(new MyLogoutHandler()).deleteCookies("JSESSIONID").permitAll().
                and().csrf().disable().cors().configurationSource(CorsConfigurationSource()).
                and().exceptionHandling().accessDeniedHandler(new MyAccessDeniedHandler());
    }
    @Bean
    public CorsConfigurationSource CorsConfigurationSource() {
        CorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://www.kylinvue.com","http://localhost:8080"));   //同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；        corsConfiguration.addAllowedHeader("*");//header，允许哪些header，本案中使用的是token，此处可将*替换为token；
        corsConfiguration.addAllowedMethod("*");   //允许的请求方法，PSOT、GET等
        corsConfiguration.setAllowCredentials(true);
        ((UrlBasedCorsConfigurationSource) source).registerCorsConfiguration("/**", corsConfiguration); //配置允许跨域访问的url
        return source;
    }

    private static class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
            httpServletResponse.setContentType("application/json;charset=utf-8");
            PrintWriter writer = httpServletResponse.getWriter();
            writer.write(JSON.toJSONString(ResEntity.ok("登录成功", SecurityContextHolder.getContext().getAuthentication().getPrincipal())));
            writer.flush();
            writer.close();
        }
    }

    private static class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
            ResEntity entity = null;
            httpServletResponse.setContentType("application/json;charset=utf-8");
            PrintWriter writer = httpServletResponse.getWriter();
            if (e instanceof UsernameNotFoundException){
                entity = ResEntity.error("用户名不存在");
            }else if (e instanceof AccountExpiredException){
                entity = ResEntity.error("用户过期");
            }else if( e instanceof LockedException){
                entity = ResEntity.error("用户被锁定");
            }else if (e instanceof BadCredentialsException){
                entity = ResEntity.error("密码错误");
            }
            writer.write(JSON.toJSONString(entity));
            writer.flush();
            writer.close();
        }
    }

    private static class MyLogoutHandler implements LogoutHandler {

        @Override
        public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
            httpServletResponse.setContentType("application/json;charset=utf-8");
            PrintWriter writer = null;
            try {
                writer = httpServletResponse.getWriter();
                writer.write(JSON.toJSONString(ResEntity.ok("注销成功")));
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

        private static class MyAccessDeniedHandler implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
            httpServletResponse.setContentType("application/json;charset=utf-8");
            PrintWriter writer = httpServletResponse.getWriter();
            writer.write(JSON.toJSONString(ResEntity.error("失败", e.getMessage())));
            writer.flush();
            writer.close();
        }
    }
    private class FilterSecurityInterceptorObjectPostProcessor implements ObjectPostProcessor<FilterSecurityInterceptor> {
        @Override
        public <O extends FilterSecurityInterceptor> O postProcess(O o) {
            o.setSecurityMetadataSource(kylinFilterInvocationSecurityMetadataSource);
            o.setAccessDecisionManager(kylinAccessDecisionVoter);
            return o;
        }
    }
}