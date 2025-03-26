package com.soleil.api.model;


import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "clientes")
public class Ingresos {
	
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
	
	public Ingresos() {
		
	}

	public Ingresos(String servicio, Date dia, int fisioterapeuta, int paciente, String pago, Double tarifa, int cantidad) {
		this.servicio = servicio;
		this.dia = dia;
		this.fisioterapeuta = fisioterapeuta;
		this.paciente = paciente;
		this.pago = pago;
		this.tarifa = tarifa;
		this.cantidad = cantidad;
	}
	
	
	
}
