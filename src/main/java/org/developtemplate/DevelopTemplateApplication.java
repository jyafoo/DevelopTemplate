package org.developtemplate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class DevelopTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevelopTemplateApplication.class, args);
    }

}
