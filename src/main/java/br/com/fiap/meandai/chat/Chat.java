package br.com.fiap.meandai.chat;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Chat {
    private String sender; // "user" ou "ia"
    private String content;
}
