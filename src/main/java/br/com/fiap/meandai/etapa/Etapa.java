package br.com.fiap.meandai.etapa;

import br.com.fiap.meandai.trilha.Trilha;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Etapa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{etapa.nome.notblank}")
    private String nome; // Ex: "Lógica de Programação"

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private boolean concluida;

    @ManyToOne
    @JoinColumn(name = "trilha_id")
    @JsonBackReference
    private Trilha trilha;
}
