package com.j10.exercise.controller;

import com.j10.exercise.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping("/main.html")
    public String main() {
        return "main";
    }

    @RequestMapping("/default.html")
    public String defaultPage() {
        return "default";
    }

    @RequestMapping("/more.html")
    public String more() {
        return "more";
    }

    @RequestMapping("/info.html")
    public String info() {
        return "info";
    }

    @RequestMapping("/myFav.html")
    public String myFav() {
        return "myFav";
    }

    @RequestMapping("/noticeDetail.html")
    public String noticeDetail() {
        return "noticeDetail";
    }

    @RequestMapping("/r1Detail.html")
    public String r1Detail() {
        return "r1Detail";
    }

    @RequestMapping("/r2Detail.html")
    public String r2Detail() {
        return "r2Detail";
    }

    @RequestMapping("/r3Detail.html")
    public String r3Detail() {
        return "r3Detail";
    }

    @RequestMapping("/search.html")
    public String search() {
        return "search";
    }

    @RequestMapping("/setlocale")
    public void setLocale(String lan,HttpServletRequest req) {
        req.getSession().setAttribute("locale",lan);
    }
}
