package br.com.fiap.meandai.user;

import br.com.fiap.meandai.enuns.TypesEnum;
import br.com.fiap.meandai.trilha.Trilha;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.context.annotation.Role;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Entity
@Table(name = "userAI")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String avatarUrl;

    private String areaAtual; // Ex: "Marketing"

    private String objetivo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Trilha> trilhas;

    public User(OAuth2User principal) {
        this.name = principal.getAttributes().get("name").toString();
        this.email = principal.getAttributes().get("email").toString();
        this.avatarUrl = principal.getAttributes().get("avatar_url").toString();
    }
}
