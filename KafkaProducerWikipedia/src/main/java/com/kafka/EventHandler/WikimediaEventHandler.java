package com.kafka.EventHandler;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.logging.Logger;

public abstract class WikimediaEventHandler implements EventHandler {

    private static final Logger logger = Logger.getLogger(WikimediaEventHandler.class.getName());
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final String topic;

    public WikimediaEventHandler(KafkaTemplate<String,String> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void onOpen() {
        // Called when the connection is opened
        System.out.println("Connection opened");
    }

    @Override
    public void onClosed() {
        // Called when the connection is closed
        System.out.println("Connection closed");
    }


    @Override
    public void onMessage(String event, MessageEvent messageEvent) {

        logger.info("Event: " + event);
        logger.info("Message: " + messageEvent.getData());

        String data = messageEvent.getData();
        kafkaTemplate.send(topic, data);
    }

    @Override
    public void onComment(String comment) {
        // Cal
    }
}