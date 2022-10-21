package vn.edu.fpt.capstone.api.configs.kafka.producers;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseProducerConfig {

    private final String brokers;

    protected BaseProducerConfig(String brokers) {
        this.brokers = brokers;
    }

    protected ProducerFactory<String, String> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    protected KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> factory) {
        return new KafkaTemplate<>(factory);
    }

}
