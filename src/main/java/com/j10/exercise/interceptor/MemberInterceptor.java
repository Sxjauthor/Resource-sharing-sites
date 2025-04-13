package com.j10.exercise.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/13 14:28
 */
@Slf4j
public class MemberInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        if(req.getSession().getAttribute("member")!=null||req.getSession().getAttribute("manager")!=null){
            return true;
        }else{
            resp.sendRedirect(req.getContextPath()+"/login.html?t=3");
        }
        log.info("被MemberInterceptor拦截-------------------");
        return false;
    }
}
