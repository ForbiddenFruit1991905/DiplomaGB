package ru.example.notes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.example.notes.aspects.LogAspect ;

@Configuration
@EnableAspectJAutoProxy
public class ProjectConfig {

    @Bean
    public LogAspect aspect(){
        return new LogAspect();
    }
}
