package com.stackroute.rabbitMQ;

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
    public static final String routingKey1= "key1";
    public static final String routingKey2= "key2";
    public static final String routingKey3= "key3";


    @Bean
    public DirectExchange exchange()
    {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Queue queue1()
    {
        return new Queue("registration_authentication_queue");
    }

    @Bean
    public Queue queue2()
    {
        return new Queue("queue_2");
    }


    @Bean
    public Queue queue3()
    {
        return new Queue("registration_suggestion_queue");
    }
    @Bean
    public Queue queue4(){return new Queue("registration_suggestion_update_user_queue");}

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
    public Binding binding1(Queue queue1, DirectExchange exchange)
    {
        return BindingBuilder.bind(queue1).to(exchange).with(routingKey1);
    }

    @Bean
    public Binding binding2(Queue queue2, DirectExchange exchange)
    {
        return BindingBuilder.bind(queue2).to(exchange).with(routingKey2);
    }


    @Bean
    public Binding binding3(Queue queue3, DirectExchange exchange)
    {
        return BindingBuilder.bind(queue3).to(exchange).with(routingKey3);
    }
}
