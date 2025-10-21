package com.example.entity;

import java.io.Serializable;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name="Paciente")
public class Paciente implements Serializable {
	
	private static final long serialVersionUID = 1L;
	//comentario
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_PACIENTE")
	private Integer idPaciente;
	
	@Column(name="Nombres")
	private String nombre;
	
	@Column(name="Apellidos")
	private String apellido;
	

	@Column(name = "DNI", nullable = false, unique = true)
	private String dni;
	
	@Column(name="fecha_nacimiento")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaNacimiento;
	
	@Column(name="Telefono")
	private String telefono;
	



	
	

}
