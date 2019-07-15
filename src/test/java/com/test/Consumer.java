package com.test;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: yuyao
 * @Date: 2019/7/3 18:02
 * @Description:
 */
public class Consumer {
    public static void main(String[] args) throws InterruptedException, IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("/");
        factory.setHost("192.168.1.67");
        factory.setPort(AMQP.PROTOCOL.PORT);    // 5672
        factory.setUsername("mq");
        factory.setPassword("123456");

        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        String EXCHANGE_NAME = "exchange.direct";
        String QUEUE_NAME = "queue_name";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "key");


//        GetResponse response = channel.basicGet(QUEUE_NAME, false);
//        byte[] body = response.getBody();
//        System.out.println(new String(body).toString());

        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("消费者收到消息：" + message);
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };

        channel.basicConsume(QUEUE_NAME, false, consumer);

        Thread.sleep(100000);
    }
}