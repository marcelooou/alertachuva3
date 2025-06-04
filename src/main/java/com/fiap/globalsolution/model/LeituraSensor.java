package com.fiap.globalsolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "LEITURA_SENSOR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeituraSensor {

    @Id
    @Column(name = "ID_LEITURA")
    // Consider using a sequence or auto-increment strategy
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leitura_seq")
    // @SequenceGenerator(name = "leitura_seq", sequenceName = "SEQ_LEITURA_SENSOR", allocationSize = 1)
    private Long id;

    @NotNull(message = "ID do sensor não pode ser nulo")
    @Column(name = "ID_SENSOR", nullable = false)
    private Long idSensor; // Store only the ID

    /* Alternatively, map the relationship directly:
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SENSOR", nullable = false)
    private Sensor sensor;
    */

    @NotNull(message = "Timestamp da leitura não pode ser nulo")
    @PastOrPresent(message = "Timestamp da leitura deve ser no passado ou presente")
    @Column(name = "TIMESTAMP_LEITURA", nullable = false)
    private LocalDateTime timestampLeitura;

    @NotBlank(message = "Tipo de medição não pode ser vazio")
    @Size(max = 50, message = "Tipo de medição deve ter no máximo 50 caracteres")
    @Column(name = "TIPO_MEDICAO", nullable = false, length = 50)
    private String tipoMedicao;

    @NotNull(message = "Valor da medição não pode ser nulo")
    @PositiveOrZero(message = "Valor da medição deve ser positivo ou zero")
    @Column(name = "VALOR_MEDICAO", nullable = false, precision = 7, scale = 2)
    private BigDecimal valorMedicao;

    // Se houver relacionamento com AlertaIncendio (OneToOne ou OneToMany), adicionar aqui
    // @OneToOne(mappedBy = "leituraGatilho", cascade = CascadeType.ALL, orphanRemoval = true)
    // private AlertaIncendio alertaIncendio;
}

