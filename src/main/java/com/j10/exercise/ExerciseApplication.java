package com.j10.exercise;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;

@SpringBootApplication
@MapperScan(basePackages={"com.j10.exercise.mapper"})
public class ExerciseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExerciseApplication.class, args);

    }

}
