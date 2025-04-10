package com.j10.exercise.controller;

import com.j10.exercise.bean.Manager;
import com.j10.exercise.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/10 16:53
 */
@Controller
public class Login2Controller {
    @Autowired
    private ManagerService managerService;

    //管理员登出
    @GetMapping("/login2")
    public String logout(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        session.removeAttribute("manager");
        session.invalidate();
        model.addAttribute("t",0);
        return "login";
    }

    @PostMapping("/login2")
    public String login2(Manager manager, String captcha, HttpServletRequest req, HttpServletResponse resp,Model model) {
        System.out.println("进入login2=================");
        String username = manager.getUsername();
        String password = manager.getPassword();
        Manager success=null;
        //判空
        if(username==null||username.length()==0||password==null||password.length()==0){
            String msg="请输入用户名或密码";
            model.addAttribute("msg",msg);
            return "login";
        }
        //先看验证码
        String code = (String) req.getSession().getAttribute("code");
        if(captcha!=null&&captcha.length()>0){
            if(!captcha.equalsIgnoreCase(code)){
                //验证码不正确
                String msg="验证码不正确";
                model.addAttribute("msg",msg);
                return "login";
            }
        }else{
            //请输入验证码
            String msg="请输入验证码";
            model.addAttribute("msg",msg);
            return "login";
        }
        //查询数据库
        success=managerService.login(manager);
        if(success==null){
            //登录失败
            String msg="用户名或密码错误";
            model.addAttribute("msg",msg);
            return "login";
        }else{
            //登陆成功
            //cookie 重定向到首页
            String remember = req.getParameter("remember");
            if("1".equals(remember)){
                Cookie yhm=new Cookie("yhm",username);
                yhm.setMaxAge(3600*24*7);
                resp.addCookie(yhm);
            }
            HttpSession session = req.getSession();
            session.setAttribute("manager",success);
            return "redirect:/main.html";
        }
    }
}
