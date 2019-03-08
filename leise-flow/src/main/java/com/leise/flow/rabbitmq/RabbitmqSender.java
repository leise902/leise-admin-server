package com.leise.flow.rabbitmq;

import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqSender {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(AbstractExchange exchange, String message) {
        rabbitAdmin.declareExchange(exchange);
        rabbitTemplate.setExchange(exchange.getName());
        rabbitTemplate.send(new Message(message.getBytes(), new MessageProperties()));

    }

    public void sendMessage(AbstractExchange exchange, String routingKey, String message) {
        rabbitAdmin.declareExchange(exchange);
        rabbitTemplate.setExchange(exchange.getName());
        rabbitTemplate.send(routingKey, new Message(message.getBytes(), new MessageProperties()));
    }

}
