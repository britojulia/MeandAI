package br.com.fiap.meandai.user;

import br.com.fiap.meandai.enuns.TypesEnum;
import br.com.fiap.meandai.trilha.Trilha;
import jakarta.persistence.*;
import org.springframework.context.annotation.Role;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;
    private String areaAtual; // Ex: "Marketing"
    private String objetivo;  // Ex: "Migrar para Tecnologia"

    @Enumerated(EnumType.STRING)
    private TypesEnum.UserRole role; // USER ou ADMIN

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Trilha> trilhas;
}
