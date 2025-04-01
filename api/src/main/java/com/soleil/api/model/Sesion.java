package com.soleil.api.model;

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

@Entity
@Table(name = "sesion")
public class Sesion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_sesion;
	
	@Column(name = "tipo_sesion")
	@NotNull(message = "Tienes que poner el tipo de sesion")
	private String tipo_sesion;
	
	@Column(name = "id_tratamiento")
	@JoinColumn(name = "tratamiento", nullable = false)
	@NotNull(message = "Tienes que poner el id del tratamiento")
	@ManyToOne(fetch = FetchType.EAGER)
	private Tratamiento id_tratamiento;
	
	public Sesion() {
		
	}
	
	public Sesion(String tipo_sesion, Tratamiento id_tratamiento) {
		this.tipo_sesion = tipo_sesion;
		this.id_tratamiento = id_tratamiento;
	}

	public int getId_sesion() {
		return id_sesion;
	}

	public void setId_sesion(int id_sesion) {
		this.id_sesion = id_sesion;
	}

	public String getTipo_sesion() {
		return tipo_sesion;
	}

	public void setTipo_sesion(String tipo_sesion) {
		this.tipo_sesion = tipo_sesion;
	}

	public Tratamiento getId_tratamiento() {
		return id_tratamiento;
	}

	public void setId_tratamiento(Tratamiento id_tratamiento) {
		this.id_tratamiento = id_tratamiento;
	}
	
}
