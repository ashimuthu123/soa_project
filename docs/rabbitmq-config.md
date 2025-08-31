# RabbitMQ Configuration for GlobalBooks SOA

## Overview
RabbitMQ serves as the Enterprise Service Bus (ESB) for asynchronous messaging between services in the GlobalBooks SOA architecture.

## Exchange Definitions

### 1. Global Exchange
- **Name**: `globalbooks-exchange`
- **Type**: Topic
- **Durable**: true
- **Auto-delete**: false

### 2. Dead Letter Exchange
- **Name**: `globalbooks-dlx`
- **Type**: Direct
- **Durable**: true
- **Auto-delete**: false

## Queue Definitions

### 1. Order Processing Queue
- **Name**: `order-processing-queue`
- **Durable**: true
- **Auto-delete**: false
- **Arguments**:
  - `x-message-ttl`: 300000 (5 minutes)
  - `x-dead-letter-exchange`: `globalbooks-dlx`
  - `x-dead-letter-routing-key`: `order-processing-dlq`

### 2. Payment Processing Queue
- **Name**: `payment-processing-queue`
- **Durable**: true
- **Auto-delete**: false
- **Arguments**:
  - `x-message-ttl`: 600000 (10 minutes)
  - `x-dead-letter-exchange`: `globalbooks-dlx`
  - `x-dead-letter-routing-key`: `payment-processing-dlq`

### 3. Shipping Processing Queue
- **Name**: `shipping-processing-queue`
- **Durable**: true
- **Auto-delete**: false
- **Arguments**:
  - `x-message-ttl`: 900000 (15 minutes)
  - `x-dead-letter-exchange`: `globalbooks-dlx`
  - `x-dead-letter-routing-key`: `shipping-processing-dlq`

### 4. Dead Letter Queues
- **Order DLQ**: `order-processing-dlq`
- **Payment DLQ**: `payment-processing-dlq`
- **Shipping DLQ**: `shipping-processing-dlq`

## Routing Keys

### Order Service
- `order.created` - New order created
- `order.updated` - Order status updated
- `order.cancelled` - Order cancelled

### Payment Service
- `payment.requested` - Payment processing requested
- `payment.processed` - Payment completed
- `payment.failed` - Payment failed

### Shipping Service
- `shipping.requested` - Shipping requested
- `shipping.confirmed` - Shipping confirmed
- `shipping.delivered` - Package delivered

## Producer Configuration

### Orders Service Producer
```java
@Configuration
public class RabbitMQConfig {
    
    @Bean
    public Queue orderProcessingQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 300000);
        args.put("x-dead-letter-exchange", "globalbooks-dlx");
        args.put("x-dead-letter-routing-key", "order-processing-dlq");
        
        return new Queue("order-processing-queue", true, false, false, args);
    }
    
    @Bean
    public TopicExchange globalbooksExchange() {
        return new TopicExchange("globalbooks-exchange", true, false);
    }
    
    @Bean
    public Binding orderProcessingBinding() {
        return BindingBuilder.bind(orderProcessingQueue())
                           .to(globalbooksExchange())
                           .with("order.created");
    }
}
```

## Consumer Configuration

### Payment Service Consumer
```java
@Component
public class PaymentMessageConsumer {
    
    @RabbitListener(queues = "payment-processing-queue")
    public void processPayment(Order order) {
        try {
            // Process payment logic
            PaymentResult result = paymentService.processPayment(order);
            
            if (result.isSuccess()) {
                // Send success message
                rabbitTemplate.convertAndSend("globalbooks-exchange", 
                                           "payment.processed", result);
            } else {
                // Send failure message
                rabbitTemplate.convertAndSend("globalbooks-exchange", 
                                           "payment.failed", result);
            }
        } catch (Exception e) {
            // Log error and send to DLQ
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
```

## Error Handling Strategy

### 1. Dead Letter Routing
- Failed messages are automatically routed to dead letter queues
- Each service has its own DLQ for isolation
- DLQ messages are retained for manual inspection

### 2. Retry Policy
- **Order Processing**: 3 retries with exponential backoff
- **Payment Processing**: 2 retries with 30-second intervals
- **Shipping Processing**: 2 retries with 60-second intervals

### 3. Circuit Breaker Pattern
- Services implement circuit breaker to prevent cascade failures
- After 5 consecutive failures, circuit opens for 60 seconds
- Half-open state allows limited traffic to test recovery

### 4. Message Persistence
- All messages are persistent (delivery mode 2)
- Publisher confirms enabled for guaranteed delivery
- Queue persistence ensures messages survive broker restarts

## Monitoring and Alerting

### 1. Queue Metrics
- Message count and rate
- Consumer count and status
- Message processing time

### 2. Error Alerts
- DLQ message count thresholds
- Consumer failure rates
- Circuit breaker state changes

### 3. Health Checks
- Queue connectivity
- Consumer availability
- Message processing latency

## Security Configuration

### 1. Authentication
- Username/password authentication
- VHost isolation for different environments

### 2. Authorization
- Service-specific users with limited permissions
- Read-only access for monitoring users

### 3. SSL/TLS
- Encrypted connections between services
- Certificate-based authentication for production

## Performance Tuning

### 1. Connection Pooling
- Connection pool size: 10-20 per service
- Channel pool size: 50-100 per connection

### 2. Message Batching
- Batch size: 100-500 messages
- Batch timeout: 100ms

### 3. Consumer Prefetch
- Prefetch count: 10-50 messages per consumer
- Ensures fair distribution across consumers
