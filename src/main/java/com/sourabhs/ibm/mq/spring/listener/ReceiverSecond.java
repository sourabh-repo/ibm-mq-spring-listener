package com.sourabhs.ibm.mq.spring.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ReceiverSecond {

    @JmsListener(destination = "${servers.mq.topic2}", containerFactory = "listenerFactory")
    public void receiveMessage(String message) {
        System.out.println("DEV.QUEUE.2 received ~" + message + "~");
    }
}
