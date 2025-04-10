package com.j10.exercise.controller;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/3/3 12:09
 */
@Controller
public class CaptchaController{
    @RequestMapping("/captcha")
    public void captcha(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //生成一个图片 响应回客户端
        //GUI SWING里的功能，这里使用hutool工具
        LineCaptcha lineCaptcha= CaptchaUtil.createLineCaptcha(80,40,4,8);
        String code = lineCaptcha.getCode();
        req.getSession().setAttribute("code",code);
        resp.setContentType("image/jpeg");
        lineCaptcha.write(resp.getOutputStream());
    }
}