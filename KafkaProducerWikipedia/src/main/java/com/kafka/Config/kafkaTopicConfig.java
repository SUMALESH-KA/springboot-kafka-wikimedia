package com.kafka.Config;

import org.apache.kafka.clients.admin   .NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class kafkaTopicConfig {

    public static final String TOPIC_NAME = "wikimediaChanges";

    public NewTopic kafkaTopic() {
        return TopicBuilder.name(TOPIC_NAME)
                .build();
    }
}
