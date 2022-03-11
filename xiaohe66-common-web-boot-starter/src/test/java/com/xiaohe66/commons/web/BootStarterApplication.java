package com.xiaohe66.commons.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author xiaohe
 * @since 2022.03.11 12:01
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class BootStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootStarterApplication.class, args);
    }

}
