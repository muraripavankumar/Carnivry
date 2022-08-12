package com.stackroute.rabbitMq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfigure {

    public static final String exchangeName="user_exchange";

    public static final String routingKey11= "key11";
    public static final String routingKey12= "key12";


    @Bean
    public DirectExchange exchange()
    {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Queue queue11()
    {
        return new Queue("payment_registration_queue");
    }

    @Bean
    public Queue queue12()
    {
        return new Queue("payment_notification_queue");
    }


    @Bean
    public Jackson2JsonMessageConverter getDataConverter()
    {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate getRabbitTemplate(final ConnectionFactory connectionFactory)
    {
        RabbitTemplate rabbitTemplate= new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(getDataConverter());
        return rabbitTemplate;
    }

    @Bean
    public Binding binding11(Queue queue11, DirectExchange exchange)
    {
        return BindingBuilder.bind(queue11).to(exchange).with(routingKey11);
    }

    @Bean
    public Binding binding12(Queue queue12, DirectExchange exchange)
    {
        return BindingBuilder.bind(queue12).to(exchange).with(routingKey12);
    }


}
