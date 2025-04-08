package com.soleil.api.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "paciente")
public class Paciente {
	
	@Id	
	@Column(name = "dni")
	@Size(min = 9, max = 9)
	@NotNull(message = "El paciente debe de tener un dni")
	private String dni;
	
	@Column(name = "nombre")
	@NotNull(message = "El paciente debe tener un nombre")
	private String nombre;
	
	@Column(name = "apellidos")
	@NotNull(message = "El paciente debe tener apellidos")
	private String apellidos;
	
	@OneToMany(mappedBy = "dni_paciente", cascade = CascadeType.ALL)
	private List<Tratamiento> tratamiento;
	
	public Paciente() {
		
	}
	
	public Paciente(String dni) {
		this.dni = dni;
	}

	public Paciente(String dni, String nombre, String apellidos) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public List<Tratamiento> getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(List<Tratamiento> tratamiento) {
		this.tratamiento = tratamiento;
	}
	
}
