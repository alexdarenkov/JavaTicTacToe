package com.example.TicTacToe.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Разрешить CORS для всех эндпоинтов
                        .allowedOrigins("http://localhost:3000") // Разрешить запросы с этого origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Разрешенные HTTP-методы
                        .allowedHeaders("*") // Разрешить все заголовки
                        .allowCredentials(true); // Разрешить передачу куки и авторизационных данных
            }
        };
    }
}
