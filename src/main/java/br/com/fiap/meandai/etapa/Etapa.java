package br.com.fiap.meandai.etapa;

import br.com.fiap.meandai.trilha.Trilha;
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
    private String descricao;
    private boolean concluida;

    @ManyToOne
    private Trilha trilha;
}
