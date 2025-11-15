package br.com.fiap.meandai.trilha;

import br.com.fiap.meandai.etapa.Etapa;
import br.com.fiap.meandai.user.User;
import jakarta.persistence.*;
import java.time.LocalDate;
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
public class Trilha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;// Ex: "Trilha de Transição para Tech"
    private LocalDate dataCriacao;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "trilha", cascade = CascadeType.ALL)
    private List<Etapa> etapas;

    @Transient
    private List<String> etapasForm; // recebe os nomes do formulário
}
