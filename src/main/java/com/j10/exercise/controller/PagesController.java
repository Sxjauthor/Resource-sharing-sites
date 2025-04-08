package com.j10.exercise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String index() {
        return "index";
    }

    @RequestMapping("/register.html")
    public String register() {
        return "register";
    }
}
