package com.tutsnnd.reggie_program.filter;

import com.alibaba.fastjson.JSON;
import com.tutsnnd.reggie_program.common.BaseContext;
import com.tutsnnd.reggie_program.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "loginCheckFilter" , urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER =new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String requestUrl = request.getRequestURI();

        HttpServletResponse response =(HttpServletResponse)servletResponse;
        String[] urls= new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs"
        };
        boolean check = checkLogin(urls,requestUrl);

        if (check) {
            log.info("登录登出或静态资源网站放行 {}",requestUrl);
            filterChain.doFilter(request,response);
            return;
        }
        if (request.getSession().getAttribute("employee")!=null){
            Long empId=(Long)request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            log.info("线程id为{}",BaseContext.getCurrentId());
            log.info("已登录，直接放行{}",requestUrl);
            filterChain.doFilter(request,response);
            return;
        }
        if (request.getSession().getAttribute("user")!=null){
            Long userId=(Long)request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            log.info("线程id为{}",BaseContext.getCurrentId());
            log.info("已登录，直接放行{}",requestUrl);
            filterChain.doFilter(request,response);
            return;
        }

        log.info("以拦截未登录请求{}",request.getRequestURI());
       response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));



    }
    public  boolean checkLogin(String[] urls,String requestUrl){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestUrl);
            if (match){
                return true;
            }
        }
        return false;
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
