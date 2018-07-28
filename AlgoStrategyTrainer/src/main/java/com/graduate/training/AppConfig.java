package com.graduate.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.ConnectionFactory;


@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages = "com.graduate.training")
@Import(SwaggerConfig.class)
public class AppConfig {

    @Bean
    public JmsListenerContainerFactory<?> initJmsContainerF(ConnectionFactory connF) {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connF);
        return factory;
    }

    public static void main(String[] args) {
        SpringApplication.run(AppConfig.class, args);
    }

}
