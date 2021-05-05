package com.dorm.backend;

import com.ulisesbocchio.jasyptspringboot.environment.StandardEncryptableEnvironment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class WebappApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(WebappApplication.class)
                .environment(new StandardEncryptableEnvironment())
                .run(args);
    }

}
