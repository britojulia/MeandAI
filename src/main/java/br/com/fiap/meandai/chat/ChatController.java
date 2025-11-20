package br.com.fiap.meandai.chat;

import br.com.fiap.meandai.user.User;
import br.com.fiap.meandai.user.UserService;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpSession;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatClient chatClient;
    private final UserService userService;

    @GetMapping
    public String abrirChat(@RequestParam(required = false) Long userId,
                            Model model,
                            HttpSession session) {

        if (userId != null) {
            User user = userService.getUserById(userId);
            model.addAttribute("user", user);
        }

        // cria histórico se não existir
        if (session.getAttribute("historico") == null) {
            session.setAttribute("historico", new ArrayList<Chat>());
        }

        return "chat";
    }

    @PostMapping
    public String enviarMensagem(@RequestParam Long userId,
                                 @RequestParam String mensagem,
                                 Model model,
                                 HttpSession session) {

        User user = userService.getUserById(userId);

        // Recupera histórico sem warnings
        Object historicoObj = session.getAttribute("historico");
        List<Chat> historico;

        if (historicoObj instanceof List<?> lista) {
            historico = new ArrayList<>();
            for (Object o : lista) {
                if (o instanceof Chat c) historico.add(c);
            }
        } else {
            historico = new ArrayList<>();
        }

        // adiciona mensagem
        historico.add(new Chat("user", mensagem));

        // monta histórico para prompt
        StringBuilder textoHistorico = new StringBuilder();
        for (Chat m : historico) {
            textoHistorico.append(m.getSender().equals("user") ? "Usuário: " : "IA: ");
            textoHistorico.append(m.getContent()).append("\n");
        }

        String prompt = """
        Você é uma IA especialista em carreira.
        Responda com até 8 linhas e de forma amigável.
        Só use caracteres simples e números.
        Histórico:
        %s
        Responda a última mensagem.
    """.formatted(textoHistorico);

        String resposta = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        historico.add(new Chat("ia", resposta));

        session.setAttribute("historico", historico);
        model.addAttribute("historico", historico);

        return "chat";
    }





}
