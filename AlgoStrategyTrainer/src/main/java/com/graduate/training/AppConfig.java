package com.graduate.training;


import com.graduate.training.entities.Strategy;
import com.graduate.training.service.StrategyAlgo;
import com.graduate.training.service.StrategyService;
import com.graduate.training.service.TwoMovingAveragesAlgo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.util.FileSystemUtils;
import javax.jms.ConnectionFactory;
import java.io.File;



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
        StrategyService service = context.getBean(StrategyService.class);
        service.addStrategy(
                new Strategy("BollingerBands", "C", true, 100, null, null, .1, 10, 2.0 ));
    }

}
