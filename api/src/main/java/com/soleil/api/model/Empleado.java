package com.soleil.api.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "empleado")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Empleado implements UserDetails {
	
	@Id
	@Column(name = "dni")
	@Size(min = 9, max = 9)
	@NotNull(message = "Tienes que poner el dni del empleado")
	private String dni;
	
	@Column(name = "nombre")
	@NotNull(message = "Tienes que ponerle un nombre al empleado")
	private String nombre;
	
	@Column(name = "apellidos")
	@NotNull(message = "Tienes que ponerle los apellidos al empleado")
	private String apellidos;
	
	@Column(name = "correo")
	@NotNull(message = "Tienes que asignarle un correo al usuairo")
	private String correo;
	
	@Column(name = "usuario")
	@NotNull(message = "Tienes que asignarle un usuario")
	private String usuario;
	
	@Column(name = "contrasena")
	@NotNull(message = "Tienes que poner una contraseña")
	private String contrasena;
	
	@Column(name = "rol")
	@NotNull(message = "Tienes que poner el rol que tiene el usuario")
	private String rol;
	
	@OneToMany(mappedBy = "dni_empleado", cascade = CascadeType.MERGE)
	@JsonManagedReference(value = "empleado-fichaje")
	private List<Fichaje> fichaje;

	@OneToMany(mappedBy = "dni_empleado", cascade = CascadeType.MERGE)
	@JsonManagedReference(value = "empleado-paciente")
	private List<Paciente> paciente;

	@OneToMany(mappedBy = "dni_empleado", cascade = CascadeType.MERGE)
	@JsonManagedReference(value = "empleado-servicio")
	private List<Servicio> servicio;
	
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.rol.toUpperCase()));
    }
    @Override public String getPassword() { return this.contrasena; }
    @Override public String getUsername() { return this.usuario; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
	
	public Empleado() {
		
	}

	public Empleado(String dni) {
		this.dni = dni;
	}
	
	public Empleado(String dni, String nombre, String apellidos, String correo, String usuario, String contrasena, String rol) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.correo = correo;
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.rol = rol;
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


	public String getDni() {
		return dni;
	}


	public void setDni(String dni) {
		this.dni = dni;
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


	public List<Fichaje> getFichaje() {
		return fichaje;
	}
	
	public void setFichaje(List<Fichaje> fichaje) {
		this.fichaje = fichaje;
	}

	public List<Paciente> getPaciente() {
		return paciente;
	}

	public void setPaciente(List<Paciente> paciente) {
		this.paciente = paciente;
	}
	
	public List<Servicio> getServicio() {
		return servicio;
	}

	public void setServicio(List<Servicio> servicio) {
		this.servicio = servicio;
	}
	
}
