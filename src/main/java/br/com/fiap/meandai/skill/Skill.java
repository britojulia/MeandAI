package br.com.fiap.meandai.skill;

import br.com.fiap.meandai.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "nome não pode ser nulo")
    private String nome;
    @NotBlank(message = "nível não pode ser nulo")
    private String nivel; // Iniciante, Intermediário, Avançado

    @ManyToOne
    private User user;
}
