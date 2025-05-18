package com.soleil.api.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

public class ServicioDTO {
	
	private int id_servicio;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Tienes que poner poner la fecha")
	private Date fecha_cita;
	
	private String dni_empleado;

	private String dni_paciente;

	private int id_tratamiento;

	@NotNull(message = "Tienes que poner el tipo de pago")
	private String modo_pago;
	
	@NotNull(message = "Tienes que poner el tipo de tarifa")
	private Double tarifa;
	
	@NotNull(message = "Tienes que poner un concepto")
	private String concepto;
	
	@NotNull(message = "Tienes que poner la cantidad de sesiones")
	private int num_sesiones;

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

	public String getDni_empleado() {
		return dni_empleado;
	}

	public void setDni_empleado(String dni_empleado) {
		this.dni_empleado = dni_empleado;
	}

	public String getDni_paciente() {
		return dni_paciente;
	}

	public void setDni_paciente(String dni_paciente) {
		this.dni_paciente = dni_paciente;
	}

	public int getId_tratamiento() {
		return id_tratamiento;
	}

	public void setId_tratamiento(int id_tratamiento) {
		this.id_tratamiento = id_tratamiento;
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
