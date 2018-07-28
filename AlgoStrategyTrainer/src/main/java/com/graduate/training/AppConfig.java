package com.graduate.training;

import com.graduate.training.entities.Order;
import com.graduate.training.messaging.ActiveMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.util.FileSystemUtils;

import javax.jms.ConnectionFactory;
import java.io.File;
import java.time.LocalDateTime;



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

    public static void main(String[] args) throws InterruptedException {
        FileSystemUtils.deleteRecursively(new File("activemq-data"));
        ConfigurableApplicationContext context = SpringApplication.run(AppConfig.class, args);
        ActiveMQSender sender = context.getBean(ActiveMQSender.class);
        Order newOrder = new Order(true, 1, 10.1, 120,
                "GE", LocalDateTime.now());
        Thread.sleep(10000);
        sender.send(newOrder.toString());
        newOrder = new Order(true, 2, 10.1, 120,
                "GE", LocalDateTime.now());
        sender.send(newOrder.toString());
        newOrder = new Order(true, 3, 10.1, 120,
                "GE", LocalDateTime.now());
        sender.send(newOrder.toString());

    }

}
