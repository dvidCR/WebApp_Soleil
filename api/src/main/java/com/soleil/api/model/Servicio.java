package com.soleil.api.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "servicio")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Servicio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_servicio;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "fecha_cita")
	@NotNull(message = "Tienes que poner poner la fecha")
	private Date fecha_cita;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dni_empleado", nullable = true)
	@JsonBackReference(value = "empleado-servicio")
	private Empleado dni_empleado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dni_paciente", nullable = true)
	@JsonBackReference(value = "paciente-servicio")
	private Paciente dni_paciente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tratamiento", nullable = false)
	@JsonBackReference(value = "tratamiento-servicio")
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

	public Integer getId_servicio() {
		return id_servicio;
	}

	public void setId_servicio(Integer id_servicio) {
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

	public Tratamiento getId_tratamiento() {
		return id_tratamiento;
	}

	public void setId_tratamiento(Tratamiento id_tratamiento) {
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
