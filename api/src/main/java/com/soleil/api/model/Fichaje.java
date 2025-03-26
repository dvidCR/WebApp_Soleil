package com.soleil.api.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "fichaje")
public class Fichaje {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "hora_entrada")
	private LocalDate hora_entrada;
	
	@Column(name = "hora_salida")
	private LocalDate hora_salida;
	
	@Column(name = "empleado")
	@NotNull(message = "Tienes que poner el id del empleado")
	private int empleado;
	
	public Fichaje() {
		
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

	public int getEmpleado() {
		return empleado;
	}

	public void setEmpleado(int empleado) {
		this.empleado = empleado;
	}
	
}
