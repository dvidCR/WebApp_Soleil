package com.soleil.api.model;


import java.util.Date;

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
@Table(name = "servicio")
public class Servicio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_servicio;
	
	@Column(name = "fecha_cita")
	@NotNull(message = "Tienes que poner poner la fecha")
	private Date fecha_cita;
	
	@Column(name = "dni_empleado")
	@Size(min = 9, max = 9)
	@JoinColumn(name = "empleado", nullable = false)
	@NotNull(message = "Tienes que poner el dni del empleado")
	@ManyToOne(fetch = FetchType.EAGER)
	private Empleado dni_empleado;
	
	@Column(name = "dni_paciente")
	@Size(min = 9, max = 9)
	@JoinColumn(name = "paciente", nullable = false)
	@NotNull(message = "Tienes que poner el dni del paciente")
	@ManyToOne(fetch = FetchType.EAGER)
	private Paciente dni_paciente;
	
	@Column(name = "id_tratamiento")
	@JoinColumn(name = "tratamiento", nullable = false)
	@NotNull(message = "Tienes que poner el id del tratamiento")
	@ManyToOne(fetch = FetchType.EAGER)
	private Tratamiento id_tratamiento;
	
	@Column(name = "modo_pago")
	@NotNull(message = "Tienes que poner el tipo de pago")
	private String modo_pago;
	
	@Column(name = "tarifa")
	@NotNull(message = "Tienes que poner el tipo de tarifa")
	private Double tarifa;
	
	@Column(name = "concepto")
	@NotNull(message = "Tienes que poner un concepto")
	private String concepto;
	
	@Column(name = "num_sesiones")
	@NotNull(message = "Tienes que poner la cantidad de sesiones")
	private int num_sesiones;
	
	public Servicio() {
		
	}

	public Servicio(Date fecha_cita, Empleado dni_empleado, Paciente dni_paciente, Tratamiento id_tratamiento, String modo_pago, Double tarifa, String concepto, int num_sesiones) {
		this.fecha_cita = fecha_cita;
		this.dni_empleado = dni_empleado;
		this.dni_paciente = dni_paciente;
		this.id_tratamiento = id_tratamiento;
		this.modo_pago = modo_pago;
		this.tarifa = tarifa;
		this.concepto = concepto;
		this.num_sesiones = num_sesiones;
	}

	public int getId_servicio() {
		return id_servicio;
	}

	public void setId_servicio(int id_servicio) {
		this.id_servicio = id_servicio;
	}

	public Date getFecha_cita() {
		return fecha_cita;
	}

	public void setFecha_cita(Date fecha_cita) {
		this.fecha_cita = fecha_cita;
	}

	public Empleado getDni_empleado() {
		return dni_empleado;
	}

	public void setDni_empleado(Empleado dni_empleado) {
		this.dni_empleado = dni_empleado;
	}

	public Paciente getDni_paciente() {
		return dni_paciente;
	}

	public void setDni_paciente(Paciente dni_paciente) {
		this.dni_paciente = dni_paciente;
	}

	public String getModo_pago() {
		return modo_pago;
	}

	public void setModo_pago(String modo_pago) {
		this.modo_pago = modo_pago;
	}

	public Double getTarifa() {
		return tarifa;
	}

	public void setTarifa(Double tarifa) {
		this.tarifa = tarifa;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public int getNum_sesiones() {
		return num_sesiones;
	}

	public void setNum_sesiones(int num_sesiones) {
		this.num_sesiones = num_sesiones;
	}	
	
}
