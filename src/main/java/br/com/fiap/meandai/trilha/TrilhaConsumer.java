package br.com.fiap.meandai.trilha;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import br.com.fiap.meandai.config.RabbitConfig;

@Component
public class TrilhaConsumer {

    @RabbitListener(queues = RabbitConfig.TRILHA_QUEUE)
    public void consumeTrilha(Object data) {
        System.out.println(" RECEBIDO (TRILHA): " + data);
    }
}
