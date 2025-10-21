package com.example.entity;

import java.io.Serializable;
import java.time.LocalTime;

import com.example.entity.enums.DiaSemana;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
    name = "Horarios_Atencion", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"ID_MEDICO", "DiaSemana", "HorarioEntrada", "HorarioSalida"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Horario implements Serializable{
	
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Horario")
    private Integer idHorario;

    @ManyToOne
    @JoinColumn(name = "ID_MEDICO", nullable = false)
    private Medico medico;

    @Enumerated(EnumType.STRING)
    @Column(name = "Dia_Semana", nullable = false)
    private DiaSemana diaSemana;

    @Column(name = "Horario_Entrada", nullable = false)
    private LocalTime horarioEntrada;

    @Column(name = "Horario_Salida", nullable = false)
    private LocalTime horarioSalida;
}
