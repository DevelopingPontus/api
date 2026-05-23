package com.example.api.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.example.api.demo.common.configuration.ConfigProperties;


@SpringBootApplication
@EnableCaching
@EnableWebSecurity
@ConfigurationPropertiesScan
@EnableConfigurationProperties(ConfigProperties.class)
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	}

	
}
