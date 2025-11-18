package br.com.fiap.meandai.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    // trilhas
    public static final String TRILHA_QUEUE = "trilha.queue";
    public static final String TRILHA_EXCHANGE = "trilha.exchange";
    public static final String TRILHA_ROUTING_KEY = "trilha.created";

    // skill
    public static final String SKILL_QUEUE = "skill.queue";
    public static final String SKILL_EXCHANGE = "skill.exchange";
    public static final String SKILL_ROUTING_KEY = "skill.created";

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter jsonMessageConverter) {

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }

    @Bean
    public Queue trilhaQueue() {
        return new Queue(TRILHA_QUEUE, true);
    }

    @Bean
    public DirectExchange trilhaExchange() {
        return new DirectExchange(TRILHA_EXCHANGE);
    }

    @Bean
    public Binding trilhaBinding() {
        return BindingBuilder.bind(trilhaQueue())
                .to(trilhaExchange())
                .with(TRILHA_ROUTING_KEY);
    }

    @Bean
    public Queue skillQueue() {
        return new Queue(SKILL_QUEUE, true);
    }

    @Bean
    public DirectExchange skillExchange() {
        return new DirectExchange(SKILL_EXCHANGE);
    }

    @Bean
    public Binding skillBinding() {
        return BindingBuilder.bind(skillQueue())
                .to(skillExchange())
                .with(SKILL_ROUTING_KEY);
    }



}
