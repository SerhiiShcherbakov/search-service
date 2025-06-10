package com.serhiishcherbakov.searchservice;

import com.serhiishcherbakov.searchservice.config.RabbitProperties;
import com.serhiishcherbakov.searchservice.config.SecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({RabbitProperties.class, SecurityProperties.class})
public class SearchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchServiceApplication.class, args);
    }

}
