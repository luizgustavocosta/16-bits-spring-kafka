package com.costa.luiz.kafka16bits;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

@Component
class Producer {
    private final KafkaTemplate<Integer, String> template;

    Faker faker;

    @Autowired
    Producer(KafkaTemplate<Integer, String> template) {
        this.template = template;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void generate() {
        faker = Faker.instance();
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        Flux<String> quotes = Flux.fromStream(Stream.generate(() -> faker.hobbit().quote()));

        Flux.zip(interval, quotes)
                .map(it -> template.send("hobbit", faker.random().nextInt(42), it.getT2()))
                .blockLast();

    }
}

@Component
@Slf4j
class Consumer {
    @KafkaListener(topics = {"hobbit"}, groupId = "16-bits-spring-boot-kafka")
    public void consume(ConsumerRecord<Integer, String> consumerRecord) {
        log.info("Message received {} and the key is {}", consumerRecord.value(), consumerRecord.key());
    }
}