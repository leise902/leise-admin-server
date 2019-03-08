package com.leise.flow.rabbitmq;

import org.springframework.stereotype.Component;

@Component
public class FlowSyncReceiver {

    public void handleMessage(String message) {
        System.out.println(message);
    }

}
