package com.example.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import com.example.entity.enums.EstadoCita;

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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Cita")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cita implements Serializable{
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Cita")
    private Integer idCita;

    @ManyToOne
    @JoinColumn(name = "ID_Medico", nullable = false)
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "ID_Paciente", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "Id_Usuario", nullable = false)
    private Usuario usuario; 

    @Column(name = "Fecha", nullable = false)
    private LocalDate fecha;
    
    @Column(name = "Hora", nullable = false)
    private LocalTime hora;

    @Column(name = "Motivo", length = 255)
    private String motivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "Estado", length = 20)
    private EstadoCita estado = EstadoCita.PENDIENTE;
}
