package com.soleil.api.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;

public class GastoDTO {
	
	private Integer id_gasto;
	
	@NotNull(message = "Tienes que poner cuanto has gastado")
	private double cantidad;
	
	@NotNull(message = "Tienes que poner el porque de este gasto")
	private String motivo;
	
	@NotNull(message = "Tienes que poner de donde viene ese gasto")
	private String proveedor;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Tienes que poner la fecha en la que se hizo la compra")
	private Date fecha;

	public Integer getId_gasto() {
		return id_gasto;
	}

	public void setId_gasto(Integer id_gasto) {
		this.id_gasto = id_gasto;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
	
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
}
