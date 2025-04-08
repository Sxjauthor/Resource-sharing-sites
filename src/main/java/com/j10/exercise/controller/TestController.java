package com.j10.exercise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/8 9:43
 */
@Controller
public class TestController {
    @RequestMapping("/comment")
    public String comment() {
        return "comment";
    }

    @RequestMapping("/customer")
    public String customer() {
        return "customerList";
    }
}
