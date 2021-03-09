package com.sky.erp;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 该类和方法的作用是可以让项目使用外部的tomcat，可以把项目打包后放到外部tomcat中运行
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ErpApplication.class);
    }

}
