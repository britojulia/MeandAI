package br.com.fiap.meandai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("""
                        Você é uma IA especialista em carreira e criação de trilhas de aprendizado.
                        Gere trilhas e etapas claras, objetivas e práticas para mudança de carreira do usuario com base nas skills e objetivos dele.
                        Fale apenas sobre carreira e transições de carreiras e assuntos correlacionados a isso.
                        """)
                .build();
    }
}
