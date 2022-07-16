package com.lq.pwd.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsServiceImpl;
    @Autowired
    UserDetailsService ggg;
//    @Bean
//    public UserDetailsService ggg(){
//        return (s)-> User.builder()
//                .username("hehe")
//                .password("1111")
//                .authorities("hy")
//                .build();
//    }
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 访问控制
// authorizeRequests()：对请求进行验证
// antMatchers()：设置请求路径
// permitAll()：直接许可，即不需要验证
// anyRequest()：除了此前配置过的URL以外的所有请求
// authenticated()：必须已经验证
// formLogin()：通过登录表单来验证用户登录
// csrf()：跨域攻击
// disable()：禁止
        String loginUrl = "/login.html";
// 处理登录表单的URL
        String loginProcessingUrl = "/login";
// 不需要登录即可请求的URL（白名单）
        String[] urls = {
                loginUrl,
                loginProcessingUrl,
                "/register.html",//注册页面
                "/pwd/usr/add",         //注册请求
                "/components/**",
                "/assets/**",
                "/css/**",
                "/img/**",
                "/js/**",
                "/npm/**",
                "/favicon.ico",
                "/package-lock.json"
        };
        http.authorizeRequests()
                .antMatchers(urls).permitAll()
                .anyRequest().authenticated();
        http.formLogin()
                .loginPage(loginUrl)
                .loginProcessingUrl(loginProcessingUrl)
//                .usernameParameter("name") // 修改认证所需的用户名的参数名（默认为username）
//                .passwordParameter("passwd") // 修改认证所需的密码的参数名（默认为password）
                // 定义登录成功的处理逻辑（可以跳转到某一个页面，也可以返会一段 JSON）

//                .successHandler((req, resp, auth) -> {
//                    // 我们可以跳转到指定页面
//                    // resp.sendRedirect("/index");
//
//                    // 也可以返回一段JSON提示
//                    // 获取当前登录用户的信息，在登录成功后，将当前登录用户的信息一起返回给客户端
//                    Object principal = auth.getPrincipal();
//                    resp.setContentType("application/json;charset=utf-8");
//                    PrintWriter out = resp.getWriter();
//                    resp.setStatus(200);
//
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("status", 200);
//                    map.put("msg", principal);
//                    ObjectMapper om = new ObjectMapper();
//                    out.write(om.writeValueAsString(map));
//                    out.flush();
//                    out.close();
//                }
//                )
                // 定义登录失败的处理逻辑（可以跳转到某一个页面，也可以返会一段 JSON）
//                .failureHandler((req, resp, e) -> {
//                    resp.setContentType("application/json;charset=utf-8");
//                    PrintWriter out = resp.getWriter();
//                    resp.setStatus(200);
//                    Map<String, Object> map = new HashMap<>();
//                    // 通过异常参数可以获取登录失败的原因，进而给用户一个明确的提示。
//                    map.put("code", 401);
//                    if (e instanceof LockedException) {
//                        map.put("msg", "账户被锁定，登录失败!");
//                    }else if(e instanceof BadCredentialsException){
//                        map.put("msg","账户名或密码输入错误，登录失败!");
//                    }else if(e instanceof DisabledException){
//                        map.put("msg","账户被禁用，登录失败!");
//                    }else if(e instanceof AccountExpiredException){
//                        map.put("msg","账户已过期，登录失败!");
//                    }else if(e instanceof CredentialsExpiredException){
//                        map.put("msg","密码已过期，登录失败!");
//                    }else{
//                        map.put("msg","登录失败!");
//                    }
//                    ObjectMapper mapper = new ObjectMapper();
//                    out.write(mapper.writeValueAsString(map));
//                    out.flush();
//                    out.close();
//                })
        ;
        http.csrf().disable();
    }


}

