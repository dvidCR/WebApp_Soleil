package com.soleil.api.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class FichajeDTO {
	
    private Integer id_fichaje;
    private LocalDate fecha;
    private LocalTime hora_entrada;
    private LocalTime hora_salida;
    private String dni_empleado;
    
    public Integer getId_fichaje() {
		return id_fichaje;
	}
    
	public void setId_fichaje(Integer id_fichaje) {
		this.id_fichaje = id_fichaje;
	}

	public LocalDate getFecha() {
		return fecha;
	}
	
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	public LocalTime getHora_entrada() {
		return hora_entrada;
	}
	
	public void setHora_entrada(LocalTime hora_entrada) {
		this.hora_entrada = hora_entrada;
	}
	
	public LocalTime getHora_salida() {
		return hora_salida;
	}
	
	public void setHora_salida(LocalTime hora_salida) {
		this.hora_salida = hora_salida;
	}
	
	public String getDni_empleado() {
		return dni_empleado;
	}
	
	public void setDni_empleado(String dni_empleado) {
		this.dni_empleado = dni_empleado;
	}

}
