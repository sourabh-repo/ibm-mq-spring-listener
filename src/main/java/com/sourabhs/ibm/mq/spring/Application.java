package com.sourabhs.ibm.mq.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsOperations;

@SpringBootApplication
@EnableJms
@EnableAutoConfiguration(exclude={ DataSourceAutoConfiguration.class})
public class Application {

  public static void main(String[] args) {
    // Launch the application
    ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
    // Send a message
    JmsOperations jmsTemplate = context.getBean("jmsTemplate", JmsOperations.class);
    jmsTemplate.convertAndSend("DEV.QUEUE.1", "Hello IBM MQ Started");
  }
}