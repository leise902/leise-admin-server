package com.leise.flow.initializer;

import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.leise.flow.config.ApplicationConfig;
import com.leise.flow.rabbitmq.FlowSyncReceiver;
import com.rabbitmq.client.Channel;

@Component
@Order(3)
@ConditionalOnProperty(prefix = "leise.application", name = "enableSyncFlow", havingValue="true")
public class FlowSyncInitializer implements CommandLineRunner {

    @Autowired
    private SimpleMessageListenerContainer simpleMessageListenerContainer;

    @Autowired
    private FlowSyncReceiver flowSyncReceiver;

    @Autowired
    private ApplicationConfig applicationConfig;

    @Override
    public void run(String... args) throws Exception {
        String accessKey = applicationConfig.getAccessKey();
        Channel channel = simpleMessageListenerContainer.getConnectionFactory().createConnection().createChannel(false);
        channel.queueDeclare(accessKey, true, false, false, null);
        simpleMessageListenerContainer.setMessageConverter(new Jackson2JsonMessageConverter());
        simpleMessageListenerContainer.setQueueNames(new String[] { accessKey });
        simpleMessageListenerContainer.setMessageListener(new MessageListenerAdapter(flowSyncReceiver));
    }
}
