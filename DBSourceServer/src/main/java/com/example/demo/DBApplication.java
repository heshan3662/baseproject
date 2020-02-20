package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableEurekaClient
@RestController
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableSwagger2
public class DBApplication {

    public static void main(String[] args) {
        SpringApplication.run(DBApplication.class, args);
    }

}