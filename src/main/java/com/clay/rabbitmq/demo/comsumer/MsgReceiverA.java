package com.clay.rabbitmq.demo.comsumer;

import com.clay.rabbitmq.demo.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: yuyao
 * @Date: 2019/7/3 16:22
 * @Description:
 */
@Component
@RabbitListener(queues = RabbitConfig.QUEUE_A)
public class MsgReceiverA {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void process(String content) {
        logger.info("接收处理队列A当中的消息： " + content);
    }

}