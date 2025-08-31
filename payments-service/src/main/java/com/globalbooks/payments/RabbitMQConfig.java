package com.globalbooks.payments;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue paymentsQueue() {
        return new Queue("payments-queue", true); // Durable queue
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue("dead-letter-queue", true);
    }
}