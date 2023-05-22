package com.costa.luiz.kafka16bits.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

public class ConsumerConfiguration {

    @Bean("consumerProperties")
    public Map<String, Object> consumerProperties() {
        return Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                GROUP_ID_CONFIG, "16-bits-spring-boot",//""spring-ccloud",
                ENABLE_AUTO_COMMIT_CONFIG, false,
                SESSION_TIMEOUT_MS_CONFIG, 15000,
                KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class,
                VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory(@Qualifier("consumerProperties") Map<String, Object> consumerProperties) {
        return new DefaultKafkaConsumerFactory<>(consumerProperties);
    }
}
