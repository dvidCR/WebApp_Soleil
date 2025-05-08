package com.soleil.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "paciente")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dni_empleado", nullable = false)
	@JsonBackReference(value = "empleado-paciente")
	private Empleado dni_empleado;

	@OneToMany(mappedBy = "dni_paciente", cascade = CascadeType.MERGE)
	@JsonManagedReference(value = "paciente-tratamiento")
	private List<Tratamiento> tratamiento;


	@OneToMany(mappedBy = "dni_paciente", cascade = CascadeType.MERGE)
	@JsonManagedReference(value = "paciente-servicio")
	private List<Servicio> servicio;

	
	public Paciente() {
		
	}
	
	public Paciente(String dni) {
		this.dni = dni;
	}

	public Paciente(String dni, String nombre, String apellidos, Empleado dni_empleado) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni_empleado = dni_empleado;
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

	public Empleado getDni_empleado() {
		return dni_empleado;
	}

	public void setDni_empleado(Empleado dni_empleado) {
		this.dni_empleado = dni_empleado;
	}

	public List<Tratamiento> getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(List<Tratamiento> tratamiento) {
		this.tratamiento = tratamiento;
	}

	public List<Servicio> getServicio() {
		return servicio;
	}

	public void setServicio(List<Servicio> servicio) {
		this.servicio = servicio;
	}
	
}
