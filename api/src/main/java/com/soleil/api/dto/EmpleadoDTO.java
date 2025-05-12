package com.soleil.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EmpleadoDTO {
	
	@Size(min = 9, max = 9)
	@NotNull(message = "Tienes que poner el dni del empleado")
	private String dni;
	
	@NotNull(message = "Tienes que ponerle un nombre al empleado")
	private String nombre;
	
	@NotNull(message = "Tienes que ponerle los apellidos al empleado")
	private String apellidos;
	
	@NotNull(message = "Tienes que asignarle un correo al usuairo")
	private String correo;
	
	@NotNull(message = "Tienes que asignarle un usuario")
	private String usuario;
	
	@NotNull(message = "Tienes que poner una contraseña")
	@Size(min = 8, max = 20, message = "Este error sale de la DTO")
	private String contrasena;
	
	@NotNull(message = "Tienes que poner el rol que tiene el usuario")
	private String rol;

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

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
}
