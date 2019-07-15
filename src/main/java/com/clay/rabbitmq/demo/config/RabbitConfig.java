package com.clay.rabbitmq.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * @Auther: yuyao
 * @Date: 2019/7/3 16:16
 * @Description:
 */
//@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
//@EnableConfigurationProperties(RabbitConfig.class)
public class RabbitConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String host;

    private int port;

    private String username;

    private String password;


    public static final String EXCHANGE_A = "my-mq-exchange_A";
    public static final String EXCHANGE_B = "my-mq-exchange_B";
    public static final String EXCHANGE_C = "my-mq-exchange_C";


    public static final String QUEUE_A = "QUEUE_A";
    public static final String QUEUE_B = "QUEUE_B";
    public static final String QUEUE_C = "QUEUE_C";

    public static final String ROUTINGKEY_A = "spring-boot-routingKey_A";
    public static final String ROUTINGKEY_B = "spring-boot-routingKey_B";
    public static final String ROUTINGKEY_C = "spring-boot-routingKey_C";


    public static final String FANOUT_EXCHANGE = "fanout_exchange";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }

    @Bean
    public DirectExchange exchangeA() {
        return new DirectExchange(EXCHANGE_A);
    }

//    @Bean
//    public DirectExchange exchangeB() {
//        return new DirectExchange(EXCHANGE_B);
//    }
//
//    @Bean
//    public DirectExchange exchangeC() {
//        return new DirectExchange(EXCHANGE_C);
//    }


    //广播模式
//    @Bean
//    FanoutExchange fanoutExchange() {
//        return new FanoutExchange(RabbitConfig.FANOUT_EXCHANGE);
//    }


    @Bean
    public Queue queueA() {
        return new Queue(QUEUE_A, true); //队列持久
    }

    @Bean
    public Queue queueB() {
        return new Queue(QUEUE_B, true); //队列持久
    }

    @Bean
    public Queue queueC() {
        return new Queue(QUEUE_C, true); //队列持久
    }

    @Bean
    public Binding bindingA() {
        return BindingBuilder.bind(queueA()).to(exchangeA()).with(RabbitConfig.ROUTINGKEY_A);
//        return BindingBuilder.bind(queueA()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingB(){
        return BindingBuilder.bind(queueB()).to(exchangeA()).with(RabbitConfig.ROUTINGKEY_A);
//        return BindingBuilder.bind(queueB()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingC(){
        return BindingBuilder.bind(queueC()).to(exchangeA()).with(RabbitConfig.ROUTINGKEY_A);
//        return BindingBuilder.bind(queueC()).to(fanoutExchange());
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}