package com.j10.exercise.controller;

import com.j10.exercise.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/8 11:05
 */
@Controller
public class PagesController {

    @RequestMapping(path = {"/","login.html"})
    public String login() {
        return "login";
    }

    @RequestMapping("/index.html")
    public String index(HttpServletRequest req) {
        ServletContext app = req.getServletContext();
        app.setAttribute("head", Constants.HEAD_PATH);
        app.setAttribute("icon", Constants.ICON_PATH);
        app.setAttribute("doc", Constants.DOC_PATH);
        app.setAttribute("img", Constants.IMG_PATH);
        app.setAttribute("soft", Constants.SOFT_PATH);
        return "index";
    }

    @RequestMapping("/register.html")
    public String register() {
        return "register";
    }

    @RequestMapping("/admin/main.html")
    public String main(HttpServletRequest req) {
        ServletContext app = req.getServletContext();
        app.setAttribute("head", Constants.HEAD_PATH);
        app.setAttribute("icon", Constants.ICON_PATH);
        app.setAttribute("doc", Constants.DOC_PATH);
        app.setAttribute("img", Constants.IMG_PATH);
        app.setAttribute("soft", Constants.SOFT_PATH);
        return "admin/main";
    }

    @RequestMapping("/admin/default.html")
    public String defaultPage() {
        return "admin/default";
    }

    @RequestMapping("/role")
    public String role() {
        return "admin/role";
    }

    @RequestMapping("/admin/noticeadd.html")
    public String noticeAdd() {
        return "admin/noticeadd";
    }

    @RequestMapping("/setlocale")
    public void setLocale(String lan,HttpServletRequest req) {
        req.getSession().setAttribute("locale",lan);
    }
}
