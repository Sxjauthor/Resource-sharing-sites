package com.j10.exercise.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.j10.exercise.interceptor.ManagerInterceptor;
import com.j10.exercise.interceptor.MemberInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/10 19:22
 */
@Configuration
public class MyConfiguration implements WebMvcConfigurer {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));//自动分页插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());//乐观锁插件
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MemberInterceptor()).addPathPatterns("/noticeMem/**","/resDetail/**");
        registry.addInterceptor(new ManagerInterceptor()).addPathPatterns("/admin/**","/role/**","/customer/**","/sort/**","/res/**","/notice/**");
    }
}
