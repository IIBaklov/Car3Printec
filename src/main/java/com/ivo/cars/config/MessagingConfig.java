package com.ivo.cars.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {
	public static final String QUEUE="Hello Car";
	public static final String EXCHANGE="Hello Car exchange";
	public static final String ROUTINGKEYS="Hello Car RoutingKey";
	 @Autowired
	    private ConnectionFactory connectionFactory;

	@Bean
	public Queue queue() {
		return new Queue("Hello Car",false,false,false);
	}
	
	@Bean
	public TopicExchange exchange() {
		return new TopicExchange("Hello Car exchange");
		
	}

	@Bean
	public Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("Hello Car RoutingKey");
	}
	
	@Bean
	public MessageConverter messageConverter() {
        final Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        return converter;
    }
	
	@Bean
    public RabbitTemplate rabbitTemplate() {
        final RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

}
