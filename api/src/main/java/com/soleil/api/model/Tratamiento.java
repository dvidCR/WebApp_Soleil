package com.soleil.api.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tratamiento")
public class Tratamiento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_tratamiento;
	
	@Column(name = "tipo_tratamiento")
	@NotNull(message = "Tienes que poner el tipo de tratamiento")
	private String tipo_tratamiento;
	
	@Column(name = "descripcion")
	@NotNull(message = "El tratamiento tiene que tener una descripcion de lo que porporciona")
	private String descripcion;
	
	@Size(min = 9, max = 9)
	@JoinColumn(name = "dni_paciente", nullable = false)
	@NotNull(message = "Tienes que poner el dni del paciente")
	@ManyToOne(fetch = FetchType.EAGER)
	private Paciente dni_paciente;
	
	@OneToMany(mappedBy = "id_tratamiento", cascade = CascadeType.ALL)
	private List<Servicio> servicio;
	
	public Tratamiento() {
		
	}
	
	public Tratamiento(int id_tratamiento) {
		this.id_tratamiento = id_tratamiento;
	}
	
	public Tratamiento(String tipo_tratamiento, String descripcion, Paciente dni_paciente) {
		this.tipo_tratamiento = tipo_tratamiento;
		this.descripcion = descripcion;
		this.dni_paciente = dni_paciente;
	}

	public int getId_tratamiento() {
		return id_tratamiento;
	}

	public void setId_tratamiento(int id_tratamiento) {
		this.id_tratamiento = id_tratamiento;
	}

	public String getTipo_tratamiento() {
		return tipo_tratamiento;
	}

	public void setTipo_tratamiento(String tipo_tratamiento) {
		this.tipo_tratamiento = tipo_tratamiento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Paciente getDni_paciente() {
		return dni_paciente;
	}

	public void setDni_paciente(Paciente dni_paciente) {
		this.dni_paciente = dni_paciente;
	}

	public List<Servicio> getServicio() {
		return servicio;
	}

	public void setServicio(List<Servicio> servicio) {
		this.servicio = servicio;
	}
	
}