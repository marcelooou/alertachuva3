package com.fiap.globalsolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "SENSOR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sensor {

    @Id
    @Column(name = "ID_SENSOR")
    // Consider using a sequence or auto-increment strategy
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sensor_seq")
    // @SequenceGenerator(name = "sensor_seq", sequenceName = "SEQ_SENSOR", allocationSize = 1)
    private Long id;

    @NotNull(message = "ID da área não pode ser nulo")
    @Column(name = "ID_AREA", nullable = false)
    private Long idArea; // Store only the ID, manage relationship in service/repository if needed

    /* Alternatively, map the relationship directly:
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AREA", nullable = false)
    private AreaMonitorada areaMonitorada;
    */

    @NotBlank(message = "Tipo do sensor não pode ser vazio")
    @Size(max = 50, message = "Tipo do sensor deve ter no máximo 50 caracteres")
    @Column(name = "TIPO_SENSOR", nullable = false, length = 50)
    private String tipoSensor;

    @NotNull(message = "Latitude não pode ser nula")
    @DecimalMin(value = "-90.0", message = "Latitude deve ser maior ou igual a -90")
    @DecimalMax(value = "90.0", message = "Latitude deve ser menor ou igual a 90")
    @Column(name = "LATITUDE", nullable = false, precision = 9, scale = 6)
    private BigDecimal latitude;

    @NotNull(message = "Longitude não pode ser nula")
    @DecimalMin(value = "-180.0", message = "Longitude deve ser maior ou igual a -180")
    @DecimalMax(value = "180.0", message = "Longitude deve ser menor ou igual a 180")
    @Column(name = "LONGITUDE", nullable = false, precision = 9, scale = 6)
    private BigDecimal longitude;

    @NotBlank(message = "Status não pode ser vazio")
    @Size(max = 20, message = "Status deve ter no máximo 20 caracteres")
    @Column(name = "STATUS", nullable = false, length = 20)
    private String status;

    // Se houver relacionamento com LeituraSensor (OneToMany), adicionar aqui
    // @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<LeituraSensor> leituras;
}

