package com.soleil.api.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PacienteDTO {
	
	@NotNull(message = "El paciente debe de tener un dni")
	@Size(min = 9, max = 9)
	private String dni;
	
	@NotNull(message = "El paciente debe tener un nombre")
	private String nombre;
	
	@NotNull(message = "El paciente debe tener apellidos")
	private String apellidos;
	
	private String dni_empleado;

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

	public String getDni_empleado() {
		return dni_empleado;
	}

	public void setDni_empleado(String dni_empleado) {
		this.dni_empleado = dni_empleado;
	}
	
	
	
}
