
package com.kafka.Consumer;

import com.kafka.Model.WikimediaData;
import com.kafka.repository.WikimediaDataRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class WikimediaConsumer {

    private static final Logger logger = Logger.getLogger(WikimediaConsumer.class.getName());
    private static final String TOPIC_NAME = "WikimediaChanges";

    // Add your database repository
    private final WikimediaDataRepository dataRepository;

    public WikimediaConsumer(WikimediaDataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @KafkaListener(topics = TOPIC_NAME, groupId = "Wikimedia")
    public void consume(String eventMessage) {
        logger.info("Event message received: " + eventMessage);

        // Create entity and save to database
        WikimediaData wikimediaData = new WikimediaData();
        wikimediaData.setWikiEventData(eventMessage);

        dataRepository.save(wikimediaData);
    }
}