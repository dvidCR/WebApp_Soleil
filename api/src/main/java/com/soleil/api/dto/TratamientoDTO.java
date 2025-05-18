package com.soleil.api.dto;

import jakarta.validation.constraints.NotNull;

public class TratamientoDTO {

	private Integer id_tratamiento;
	
	@NotNull(message = "Tienes que poner el tipo de tratamiento")
	private String tipo_tratamiento;
	
	@NotNull(message = "El tratamiento tiene que tener una descripcion de lo que porporciona")
	private String descripcion;
	
	private String dni_paciente;
	
	public TratamientoDTO() {
		
	}
	
	public TratamientoDTO(String tipo_tratamiento, String descripcion) {
        this.tipo_tratamiento = tipo_tratamiento;
        this.descripcion = descripcion;
    }

	public TratamientoDTO(Integer id_tratamiento, String tipo_tratamiento, String descripcion, String dni_paciente) {
		this.id_tratamiento = id_tratamiento;
		this.tipo_tratamiento = tipo_tratamiento;
		this.descripcion = descripcion;
		this.dni_paciente = dni_paciente;
	}

	public Integer getId_tratamiento() {
		return id_tratamiento;
	}

	public void setId_tratamiento(Integer id_tratamiento) {
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

	public String getDni_paciente() {
		return dni_paciente;
	}

	public void setDni_paciente(String dni_paciente) {
		this.dni_paciente = dni_paciente;
	}
	
}
