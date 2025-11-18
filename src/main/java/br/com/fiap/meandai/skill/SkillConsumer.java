package br.com.fiap.meandai.skill;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import br.com.fiap.meandai.config.RabbitConfig;

@Component
public class SkillConsumer {

    @RabbitListener(queues = RabbitConfig.SKILL_QUEUE)
    public void consumeSkill(Object data) {
        System.out.println(" RECEBIDO (SKILL): " + data);
    }
}
