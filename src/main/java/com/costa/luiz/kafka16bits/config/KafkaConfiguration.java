package com.costa.luiz.kafka16bits.config;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

import static org.apache.kafka.clients.CommonClientConfigs.RETRIES_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.BUFFER_MEMORY_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;

public class KafkaConfiguration {

    @Bean("producerFactory")
    public ProducerFactory<Integer, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(
                Map.of(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                        RETRIES_CONFIG, 0,
                        BUFFER_MEMORY_CONFIG, 33554432,
                        KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                        VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
                ));
    }

    @Bean
    public KafkaTemplate<Integer, String> kafkaTemplate(@Qualifier("producerFactory") ProducerFactory<Integer, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
