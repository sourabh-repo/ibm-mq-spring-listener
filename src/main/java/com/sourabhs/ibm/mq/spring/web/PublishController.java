package com.sourabhs.ibm.mq.spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublishController {

  @Autowired
  private JmsOperations jmsTemplate;

  @GetMapping(value = "/availability")
  public @ResponseBody String healthChecker() {
    return "<html><body>Welcome to IBM MQ Pub Sub Processing...</body></html>";
  }

  @GetMapping(value = "/publish1")
  public ResponseEntity<String> publish1(@RequestParam String message) {
    jmsTemplate.convertAndSend("DEV.QUEUE.1", message);
    return new ResponseEntity<>("Message Published to Queue 1", HttpStatus.ACCEPTED);
  }

  @GetMapping(value = "/publish2")
  public ResponseEntity<String> publish2(@RequestParam String message) {
    jmsTemplate.convertAndSend("DEV.QUEUE.2", message);
    return new ResponseEntity<>("Message Published to Queue 2", HttpStatus.ACCEPTED);
  }

}