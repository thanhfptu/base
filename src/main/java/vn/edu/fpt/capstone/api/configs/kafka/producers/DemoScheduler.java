package vn.edu.fpt.capstone.api.configs.kafka.producers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DemoScheduler {

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public void setKafkaTemplate(@Qualifier(ImportProducerConfig.KAFKA_TEMPLATE) KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

}
