package com.soleil.api.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "fichaje")
public class Fichaje {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_fichaje;
	
	@Column(name = "hora_entrada")
	private LocalDate hora_entrada;
	
	@Column(name = "hora_salida")
	private LocalDate hora_salida;
	
	@Column(name = "dni_empleado")
	@Size(min = 9, max = 9)
	@JoinColumn(name = "empleado", nullable = false)
	@NotNull(message = "Tienes que poner el dni del empleado")
	@ManyToOne(fetch = FetchType.EAGER)
	private Empleado dni_empleado;
	
	public Fichaje() {
		
	}
	
	public Fichaje(LocalDate hora_entrada, LocalDate hora_salida, Empleado dni_empleado) {
		this.hora_entrada = hora_entrada;
		this.hora_salida = hora_salida;
		this.dni_empleado = dni_empleado;
	}

	public LocalDate getHora_entrada() {
		return hora_entrada;
	}

	public void setHora_entrada(LocalDate hora_entrada) {
		this.hora_entrada = hora_entrada;
	}

	public LocalDate getHora_salida() {
		return hora_salida;
	}

	public void setHora_salida(LocalDate hora_salida) {
		this.hora_salida = hora_salida;
	}

	public Empleado getEmpleado() {
		return dni_empleado;
	}

	public void setEmpleado(Empleado dni_empleado) {
		this.dni_empleado = dni_empleado;
	}
	
}
