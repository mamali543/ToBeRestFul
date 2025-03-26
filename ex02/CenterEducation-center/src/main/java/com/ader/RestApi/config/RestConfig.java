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
public class RestConfig {

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        //RepositoryRestConfigurer is an interface that allows you to customize Spring Data REST's behavior.
        return new RepositoryRestConfigurer() {
            @Override
            public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
                // Expose IDs for these entities in REST responses
                config.exposeIdsFor(User.class, Course.class, Lesson.class);
                
                // Set base path for REST API (optional)
                //This configures all Spring Data REST endpoints to be prefixed with /api
                config.setBasePath("/api");
            }
        };
    }
} 