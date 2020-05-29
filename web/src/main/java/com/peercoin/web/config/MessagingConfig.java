package com.peercoin.web.config;

import com.peercoin.core.messaging.Exchanges;
import com.peercoin.core.messaging.Queues;
import com.peercoin.core.messaging.RoutingKeys;
import com.peercoin.web.services.CurrencyService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SuppressWarnings("unused")
public class MessagingConfig {
    private static final String topicExchangeName = Exchanges.PEERCOIN;
    private static final String cryptoQueue = Queues.CRYPTO_QUEUE;
    private static final String fiatQueue = Queues.FIAT_QUEUE;
    private static final String paymentMethodQueue=Queues.FIAT_METHOD_QUEUE;

    @Value("${rabbit.username}")
    private String rabbitUsername;
    @Value("${rabbit.password}")
    private String rabbitPassword;
    @Value("${rabbit.host}")
    private String rabbitHost;

    @Bean
    Queue cryptoQueue() {
        return new Queue(cryptoQueue,true);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    Binding cryptoBinding(@Qualifier("cryptoQueue")Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with(RoutingKeys.CRYPTO_KEY);
    }

    @Bean
    Queue fiatQueue() {
        return new Queue(fiatQueue,true);
    }

    @Bean
    Binding fiatBinding(@Qualifier("fiatQueue")Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with(RoutingKeys.FIAT_KEY);
    }

    @Bean
    Queue paymentMethodQueue() {
        return new Queue(paymentMethodQueue, true);
    }

    @Bean
    Binding paymentMethodBinding(@Qualifier("paymentMethodQueue") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with(RoutingKeys.FIAT_METHOD_KEY);
    }

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

//    @Bean
//    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//                                             MessageListenerAdapter listenerAdapter) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(cryptoQueue,fiatQueue,paymentMethodQueue);
//        container.setMessageListener(listenerAdapter);
//        return container;
//    }

    @Bean
    MessageListenerAdapter listenerAdapter(CurrencyService receiver) {
        return new MessageListenerAdapter(receiver, "receiveCurrencyMessage");
    }
}
