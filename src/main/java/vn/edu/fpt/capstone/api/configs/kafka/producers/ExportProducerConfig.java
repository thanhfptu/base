package vn.edu.fpt.capstone.api.configs.kafka.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import vn.edu.fpt.capstone.api.configs.kafka.KafkaConfig;

@Configuration
public class ExportProducerConfig extends BaseProducerConfig {

    public static final String TOPIC = "foms_export";
    public static final String PRODUCER_FACTORY = "exportProducerFactory";
    public static final String KAFKA_TEMPLATE = "exportKafkaTemplate";

    @Autowired
    protected ExportProducerConfig(KafkaConfig kafkaConfig) {
        super(kafkaConfig.getBrokers());
    }

    @Bean(name = PRODUCER_FACTORY)
    public ProducerFactory<String, String> exportProducerFactory() {
        return super.producerFactory();
    }

    @Bean(name = KAFKA_TEMPLATE)
    public KafkaTemplate<String, String> importKafkaTemplate(
            @Qualifier(PRODUCER_FACTORY) ProducerFactory<String, String> factory) {
        return super.kafkaTemplate(factory);
    }

}
