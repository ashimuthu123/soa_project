package com.globalbooks.shipping;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue shippingQueue() {
        return new Queue("shipping-queue", true);
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue("dead-letter-queue", true);
    }
}