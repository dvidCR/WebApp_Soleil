package com.soleil.api.model;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "ingreso")
public class Ingreso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "servicio")
	@NotNull(message = "Tienes que poner un servicio")
	@Size(min = 1, max = 30)
	private String servicio;
	
	@Column(name = "dia")
	@NotNull(message = "Tienes que poner un dia")
	private Date dia;
	
	@Column(name = "fisioterapeuta")
	@NotNull(message = "Tienes que asignar a un empleado")
	private int fisioterapeuta;
	
	@Column(name = "paciente")
	@NotNull(message = "Tienes que poner el nombre del paciete")
	private int paciente;
	
	@Column(name = "pago")
	@NotNull(message = "Tienes que poner el tipo de pago")
	private String pago;
	
	@Column(name = "tarifa")
	@NotNull(message = "Tienes que poner el tipo de tarifa")
	private Double tarifa;
	
	@Column(name = "cantidad")
	@NotNull(message = "Tienes que poner la cantidad de veces que quiere estos servicios")
	private int cantidad;
	
	public Ingreso() {
		
	}

	public Ingreso(String servicio, Date dia, int fisioterapeuta, int paciente, String pago, Double tarifa, int cantidad) {
		this.servicio = servicio;
		this.dia = dia;
		this.fisioterapeuta = fisioterapeuta;
		this.paciente = paciente;
		this.pago = pago;
		this.tarifa = tarifa;
		this.cantidad = cantidad;
	}

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public Date getDia() {
		return dia;
	}

	public void setDia(Date dia) {
		this.dia = dia;
	}

	public int getFisioterapeuta() {
		return fisioterapeuta;
	}

	public void setFisioterapeuta(int fisioterapeuta) {
		this.fisioterapeuta = fisioterapeuta;
	}

	public int getPaciente() {
		return paciente;
	}

	public void setPaciente(int paciente) {
		this.paciente = paciente;
	}

	public String getPago() {
		return pago;
	}

	public void setPago(String pago) {
		this.pago = pago;
	}

	public Double getTarifa() {
		return tarifa;
	}

	public void setTarifa(Double tarifa) {
		this.tarifa = tarifa;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
}
