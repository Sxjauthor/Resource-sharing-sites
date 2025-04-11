package com.j10.exercise.resolver;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/8 10:16
 */
@Component("localeResolver")
public class MyLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String locale = request.getParameter("lan");
        if (StringUtils.hasText(locale)&&locale.contains("_")) {
            String[] split = locale.split("_");
            return new Locale(split[0], split[1]);
        }
        String loc = (String) request.getSession().getAttribute("locale");
        if (StringUtils.hasText(loc)&&loc.contains("_")) {
            String[] split = loc.split("_");
            return new Locale(split[0], split[1]);
        }else{
            return Locale.getDefault(); //返回服务器所在地区国际化信息
        }
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
