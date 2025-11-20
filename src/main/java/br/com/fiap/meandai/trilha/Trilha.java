package br.com.fiap.meandai.trilha;

import br.com.fiap.meandai.etapa.Etapa;
import br.com.fiap.meandai.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(columnDefinition = "TEXT")
    private String conteudoGeradoIA; // texto inteiro da resposta da IA

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "trilha", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Etapa> etapas;


}
