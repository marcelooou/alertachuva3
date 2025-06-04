package com.fiap.globalsolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "EQUIPE_RESPOSTA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipeResposta {

    @Id
    @Column(name = "ID_EQUIPE")
    // Consider using a sequence or auto-increment strategy
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "equipe_seq")
    // @SequenceGenerator(name = "equipe_seq", sequenceName = "SEQ_EQUIPE_RESPOSTA", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Nome da equipe não pode ser vazio")
    @Size(max = 100, message = "Nome da equipe deve ter no máximo 100 caracteres")
    @Column(name = "NOME_EQUIPE", nullable = false, length = 100)
    private String nomeEquipe;

    @NotBlank(message = "Base de operação não pode ser vazia")
    @Size(max = 100, message = "Base de operação deve ter no máximo 100 caracteres")
    @Column(name = "BASE_OPERACAO", nullable = false, length = 100)
    private String baseOperacao;

    // Se houver relacionamento com AlertaIncendio (OneToMany), adicionar aqui
    // @OneToMany(mappedBy = "equipeDesignada", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<AlertaIncendio> alertasDesignados;
}

