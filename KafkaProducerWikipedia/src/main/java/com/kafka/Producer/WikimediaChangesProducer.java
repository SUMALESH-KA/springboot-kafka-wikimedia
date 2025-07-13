package com.kafka.Producer;

import com.kafka.EventHandler.WikimediaEventHandler;
import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Service
public class WikimediaChangesProducer {

    private static final Logger logger = Logger.getLogger(WikimediaChangesProducer.class.getName());

    private final KafkaTemplate<String, String> kafkaTemplate;

    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendStreamToKafka() throws InterruptedException {
        String topic = "WikimediaChanges";

        EventHandler eventHandler = new WikimediaEventHandler(kafkaTemplate, topic) {
            @Override
            public void onError(Throwable throwable) {

            }
        };
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";

        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(url));
        EventSource eventSource = builder.build();

        eventSource.start();

        // Keep application running for 1 minutes to stream events
        TimeUnit.MINUTES.sleep(10);
    }
}
