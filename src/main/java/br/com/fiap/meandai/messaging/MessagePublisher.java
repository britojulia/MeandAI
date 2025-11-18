package br.com.fiap.meandai.messaging;

import br.com.fiap.meandai.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    public MessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendTrilhaCreated(Object data) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.TRILHA_EXCHANGE,
                RabbitConfig.TRILHA_ROUTING_KEY,
                data
        );
    }

    public void sendSkillCreated(Object data) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.SKILL_EXCHANGE,
                RabbitConfig.SKILL_ROUTING_KEY,
                data
        );
    }
}
