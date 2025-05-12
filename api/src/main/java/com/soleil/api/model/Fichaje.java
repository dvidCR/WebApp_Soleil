package com.soleil.api.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "fichaje")
public class Fichaje {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_fichaje;
	
	@Column(name = "fecha")
	private LocalDate fecha;
	
	@Column(name = "hora_entrada")
	private LocalTime hora_entrada;
	
	@Column(name = "hora_salida")
	private LocalTime hora_salida;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dni_empleado", nullable = false)
	@JsonBackReference(value = "empleado-fichaje")
	private Empleado dni_empleado;

	
	public Fichaje() {
		
	}
	
	public Fichaje(LocalDate fecha, LocalTime hora_entrada, Empleado dni_empleado) {
		this.fecha = fecha;
		this.hora_entrada = hora_entrada;
		this.dni_empleado = dni_empleado;
	}
	
	public Fichaje(LocalTime hora_salida) {
		this.hora_salida = hora_salida;
	}
	
	public Fichaje(LocalDate fecha, LocalTime hora_entrada, LocalTime hora_salida, Empleado dni_empleado) {
		this.fecha = fecha;
		this.hora_entrada = hora_entrada;
		this.hora_salida = hora_salida;
		this.dni_empleado = dni_empleado;
	}

	public int getId_fichaje() {
		return id_fichaje;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHora_entrada() {
		return hora_entrada;
	}

	public void setHora_entrada(LocalTime hora_entrada) {
		this.hora_entrada = hora_entrada;
	}

	public LocalTime getHora_salida() {
		return hora_salida;
	}

	public void setHora_salida(LocalTime hora_salida) {
		this.hora_salida = hora_salida;
	}

	public Empleado getEmpleado() {
		return dni_empleado;
	}

	public void setEmpleado(Empleado dni_empleado) {
		this.dni_empleado = dni_empleado;
	}
	
}
