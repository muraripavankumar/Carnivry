package com.stackroute.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class MessageConfiguration {
    private String exchangeName="user_exchange";
    private String eventQueue="management_notification_queue";
    private String eventQueue1="management_suggestion_queue";
    private String eventQueue2="management_suggestion_update_event_queue";


    @Bean
    public Queue getQueue(){
        return new Queue(eventQueue);
    }

    @Bean
    public DirectExchange getDirectExchange(){
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Jackson2JsonMessageConverter getProducerJacksonConverterData(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(getProducerJacksonConverterData());
        return rabbitTemplate;
    }

    @Bean
    public Binding bindingUser(@Qualifier("getQueue") Queue queue, DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with("user_routing");
    }

    @Bean
    public Queue getQueue1(){
        return new Queue(eventQueue1);
    }
    @Bean
    public Binding bindingUser1(@Qualifier("getQueue1") Queue queue, DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with("management_routing");
    }

    @Bean
    public Queue getQueue2(){
        return new Queue(eventQueue2);
    }
    @Bean
    public Binding bindingUser2(@Qualifier("getQueue2") Queue queue,DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with("management_update_event_routing");
    }


}
