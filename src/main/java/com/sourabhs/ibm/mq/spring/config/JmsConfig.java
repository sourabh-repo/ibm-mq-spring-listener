package com.sourabhs.ibm.mq.spring.config;

import com.ibm.mq.jms.MQTopicConnectionFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import static com.ibm.msg.client.wmq.common.CommonConstants.WMQ_CM_CLIENT;

@Configuration
public class JmsConfig {

  @Value("${servers.mq.host-name}")
  private String host;

  @Value("${servers.mq.port}")
  private Integer port;

  @Value("${servers.mq.queue-manager}")
  private String queueManager;

  @Value("${servers.mq.channel}")
  private String channel;

  // @Value("${servers.mq.queue}")
  // private String queue;

  @Value("${servers.mq.topic}")
  private String topic;

  @Value("${servers.mq.user}")
  private String username;

  @Value("${servers.mq.password}")
  private String password;

  @Value("${servers.mq.timeout}")
  private long receiveTimeout;

  @Value("${servers.mq.clientId}")
  private String clientId;

  @Value("${servers.mq.clientId2}")
  private String clientId2;

  @Bean
  public MQTopicConnectionFactory mqTopicConnectionFactory() {
    MQTopicConnectionFactory mqTopicConnectionFactory = new MQTopicConnectionFactory();
    try {
      mqTopicConnectionFactory.setHostName(host);
      mqTopicConnectionFactory.setQueueManager(queueManager);
      mqTopicConnectionFactory.setPort(port);
      mqTopicConnectionFactory.setChannel(channel);
      mqTopicConnectionFactory.setTransportType(WMQ_CM_CLIENT);
      mqTopicConnectionFactory.setClientReconnectTimeout(0);
      mqTopicConnectionFactory.setCCSID(1208);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return mqTopicConnectionFactory;
  }

  @Bean
  @Primary
  public UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter(
      MQTopicConnectionFactory mqTopicConnectionFactory) {
    UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter = new UserCredentialsConnectionFactoryAdapter();
    userCredentialsConnectionFactoryAdapter.setTargetConnectionFactory(mqTopicConnectionFactory);
    userCredentialsConnectionFactoryAdapter.setUsername(username);
    userCredentialsConnectionFactoryAdapter.setPassword(password);
    return userCredentialsConnectionFactoryAdapter;
  }

  @Bean
  public CachingConnectionFactory cachingConnectionFactory(
      UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter) {
    CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
    cachingConnectionFactory.setTargetConnectionFactory(userCredentialsConnectionFactoryAdapter);
    cachingConnectionFactory.setSessionCacheSize(1000);
    cachingConnectionFactory.setReconnectOnException(true);
    return cachingConnectionFactory;
  }

  @Bean
  public PlatformTransactionManager jmsTransactionManager(CachingConnectionFactory cachingConnectionFactory) {
    JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
    jmsTransactionManager.setConnectionFactory(cachingConnectionFactory);
    return jmsTransactionManager;
  }

  @Bean
  public JmsOperations jmsTemplate(CachingConnectionFactory cachingConnectionFactory) {
    JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
    jmsTemplate.setPubSubDomain(true);
    jmsTemplate.setReceiveTimeout(receiveTimeout);
    return jmsTemplate;
  }

  @Bean
  public JmsListenerContainerFactory<?> listenerFactory(ConnectionFactory connectionFactory,
                                                             DefaultJmsListenerContainerFactoryConfigurer configurer) {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    configurer.configure(factory, connectionFactory);
    factory.setPubSubDomain(true);
    factory.setConcurrency("1");
    factory.setSubscriptionShared(false);
    factory.setSubscriptionDurable(true);
    factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
    factory.setClientId(clientId);
    return factory;
  }

  @Bean
  public JmsListenerContainerFactory<?> jmsListenerFactory(ConnectionFactory connectionFactory,
                                                        DefaultJmsListenerContainerFactoryConfigurer configurer) {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    configurer.configure(factory, connectionFactory);
    factory.setPubSubDomain(true);
    factory.setConcurrency("1");
    factory.setSubscriptionShared(false);
    factory.setSubscriptionDurable(true);
    factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
    factory.setClientId(clientId2);
    return factory;
  }
}