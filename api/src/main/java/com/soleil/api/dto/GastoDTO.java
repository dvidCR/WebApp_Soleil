package com.soleil.api.dto;

import jakarta.validation.constraints.NotNull;

public class GastoDTO {
	
	private int id_gasto;
	
	@NotNull(message = "Tienes que poner cuanto has gastado")
	private int cantidad;
	
	@NotNull(message = "Tienes que poner el porque de este gasto")
	private String motivo;
	
	@NotNull(message = "Tienes que poner de donde viene ese gasto")
	private String proveedor;

	public int getId_gasto() {
		return id_gasto;
	}

	public void setId_gasto(int id_gasto) {
		this.id_gasto = id_gasto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
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
	
}
