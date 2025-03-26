package com.ader.RestApi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.ader.RestApi.pojo.User;
import com.ader.RestApi.pojo.Course;
import com.ader.RestApi.pojo.Lesson;

//This RestConfig class configures how Spring Data REST behaves in your application
@Configuration
//@Configuration: Tells Spring this is a configuration class that provides beans to the application context
public class RestConfig {

} 