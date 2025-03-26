package com.soleil.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuario")
public class Usuario {
	
	@Id
	@NotNull(message = "Tienes que poner el nombre del usuario")
	private String usuario;
	
	@Column(name = "contrasena")
	@NotNull(message = "Tienes que poner una contraseña")
	@Size(min = 8, max = 20)
	private String contrasena;
	
	@Column(name = "rol")
	@NotNull(message = "Tienes que poner el rol que tiene el usuario")
	private String rol;
	
	public Usuario() {
		
	}

	public Usuario(String usuario, String contrasena, String rol) {
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.rol = rol;
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
