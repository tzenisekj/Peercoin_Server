package com.peercoin.manager.config;

import com.peercoin.core.messaging.Exchanges;
import com.peercoin.core.messaging.Queues;
import com.peercoin.core.messaging.RoutingKeys;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SuppressWarnings("unused")
public class MessagingConfig {
    private static final String topicExchangeName = Exchanges.PEERCOIN;
    private static final String cryptoQueue = Queues.CRYPTO_QUEUE;

    @Value("${rabbit.username}")
    private String rabbitUsername;
    @Value("${rabbit.password}")
    private String rabbitPassword;
    @Value("${rabbit.host}")
    private String rabbitHost;

    @Bean
    TopicExchange topicExchange(){
        TopicExchange topicExchange=new TopicExchange(topicExchangeName);
        return topicExchange;
    }
    @Bean
    CachingConnectionFactory cachingConnectionFactory() {
        CachingConnectionFactory connectionFactory= new CachingConnectionFactory(rabbitHost);
        connectionFactory.setUsername(rabbitUsername);
        connectionFactory.setPassword(rabbitPassword);
        return connectionFactory;
    }
    @Bean
    ConnectionFactory connectionFactory() {
        return cachingConnectionFactory();
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }
}
