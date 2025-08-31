package com.globalbooks.orders.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange("orders-exchange", true, false);
    }

    @Bean
    public Queue paymentsQueue() {
        return QueueBuilder.durable("payments-queue")
                .withArgument("x-dead-letter-exchange", "orders-dlx")
                .withArgument("x-dead-letter-routing-key", "payment-failed")
                .build();
    }

    @Bean
    public Queue shippingQueue() {
        return QueueBuilder.durable("shipping-queue")
                .withArgument("x-dead-letter-exchange", "orders-dlx")
                .withArgument("x-dead-letter-routing-key", "shipping-failed")
                .build();
    }

    @Bean
    public FanoutExchange deadLetterExchange() {
        return new FanoutExchange("orders-dlx");
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable("orders-dlq").build();
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue())
                .to(deadLetterExchange());
    }

    @Bean
    public Binding paymentBinding() {
        return BindingBuilder.bind(paymentsQueue())
                .to(orderExchange())
                .with("order.payment");
    }

    @Bean
    public Binding shippingBinding() {
        return BindingBuilder.bind(shippingQueue())
                .to(orderExchange())
                .with("order.shipping");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        template.setConfirmCallback((correlation, ack, reason) -> {
            if (!ack) {
                // Handle nack
                System.err.println("Message send failed: " + reason);
            }
        });
        template.setReturnsCallback(returned -> {
            // Handle returned message
            System.err.println("Message returned: " + returned.getMessage());
        });
        return template;
    }
}
