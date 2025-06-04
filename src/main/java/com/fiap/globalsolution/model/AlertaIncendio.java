package com.fiap.globalsolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "ALERTA_INCENDIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertaIncendio {

    @Id
    @Column(name = "ID_ALERTA")
    // Consider using a sequence or auto-increment strategy
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alerta_seq")
    // @SequenceGenerator(name = "alerta_seq", sequenceName = "SEQ_ALERTA_INCENDIO", allocationSize = 1)
    private Long id;

    @NotNull(message = "ID da leitura gatilho não pode ser nulo")
    @Column(name = "ID_LEITURA_GATILHO", nullable = false)
    private Long idLeituraGatilho; // Store only the ID

    /* Alternatively, map the relationship directly:
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_LEITURA_GATILHO", nullable = false)
    private LeituraSensor leituraGatilho;
    */

    @Column(name = "ID_EQUIPE_DESIGNADA") // Nullable based on table definition
    private Long idEquipeDesignada; // Store only the ID

    /* Alternatively, map the relationship directly:
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EQUIPE_DESIGNADA")
    private EquipeResposta equipeDesignada;
    */

    @NotBlank(message = "Nível de severidade não pode ser vazio")
    @Size(max = 20, message = "Nível de severidade deve ter no máximo 20 caracteres")
    @Column(name = "NIVEL_SEVERIDADE", nullable = false, length = 20)
    private String nivelSeveridade;

    @NotBlank(message = "Status do alerta não pode ser vazio")
    @Size(max = 20, message = "Status do alerta deve ter no máximo 20 caracteres")
    @Column(name = "STATUS_ALERTA", nullable = false, length = 20)
    private String statusAlerta;
}

