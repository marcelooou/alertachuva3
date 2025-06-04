package com.fiap.globalsolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "AREA_MONITORADA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaMonitorada {

    @Id
    @Column(name = "ID_AREA")
    // Consider using a sequence or auto-increment strategy if applicable in Oracle
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "area_seq")
    // @SequenceGenerator(name = "area_seq", sequenceName = "SEQ_AREA_MONITORADA", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Nome da área não pode ser vazio")
    @Size(max = 100, message = "Nome da área deve ter no máximo 100 caracteres")
    @Column(name = "NOME_AREA", nullable = false, length = 100)
    private String nomeArea;

    @NotNull(message = "Latitude do centro não pode ser nula")
    @Column(name = "LATITUDE_CENTRO", nullable = false, precision = 9, scale = 6)
    private BigDecimal latitudeCentro;

    @NotNull(message = "Longitude do centro não pode ser nula")
    @Column(name = "LONGITUDE_CENTRO", nullable = false, precision = 9, scale = 6)
    private BigDecimal longitudeCentro;

    @NotNull(message = "Raio não pode ser nulo")
    @Positive(message = "Raio deve ser positivo")
    @Column(name = "RAIO_KM", nullable = false, precision = 5, scale = 2)
    private BigDecimal raioKm;

    // Se houver relacionamento com Sensor (OneToMany), adicionar aqui
    // @OneToMany(mappedBy = "areaMonitorada", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<Sensor> sensores;
}

