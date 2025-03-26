package com.soleil.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "empleado")
public class Empleado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "nombre")
	@NotNull(message = "Tienes que ponerle un nombre al empleado")
	private String nombre;
	
	@Column(name = "apellidos")
	@NotNull(message = "Tienes que ponerle los apellidos al empleado")
	private String apellidos;
	
	@Column(name = "usuario")
	@NotNull(message = "Tienes que asignarle un usuario")
	private String usuario;
	
	@Column(name = "dni")
	@Size(min = 9, max = 9)
	@NotNull(message = "Tienes que poner el dni del empleado")
	private String dni;
	
	
	public Empleado() {
		
	}


	public Empleado(String nombre, String apellidos, String usuario, String dni) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.usuario = usuario;
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


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getDni() {
		return dni;
	}


	public void setDni(String dni) {
		this.dni = dni;
	}
	
}
