package com.sourabhs.ibm.mq.spring.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @JmsListener(destination =  "${servers.mq.topic1}", containerFactory = "jmsListenerFactory")
    public void receiveMessage(String message) {
        System.out.println("DEV.QUEUE.1 received ~" + message + "~");
    }
}
