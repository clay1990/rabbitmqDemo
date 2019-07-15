package com.test;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: yuyao
 * @Date: 2019/7/3 17:50
 * @Description:
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        CachingConnectionFactory  factory = new CachingConnectionFactory();
        factory.setHost("192.168.1.67");
        factory.setPort(AMQP.PROTOCOL.PORT);
        factory.setUsername("mq");
        factory.setPassword("123456");


        Connection connection =  factory.createConnection();
        Channel channel = connection.createChannel(false);

        String EXCHANGE_NAME = "exchange.direct";
        String QUEUE_NAME = "queue_name";
        String ROUTING_KEY = "key";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

        String message = "Hello RabbitMQ:";
        for (int i = 0; i < 5; i++) {
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, (message + i).getBytes("UTF-8"));
        }

        channel.close();
        connection.close();
    }
}