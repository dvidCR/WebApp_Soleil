package com.soleil.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "gasto")
public class Gasto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_gasto;
	
	@Column(name = "cantidad")
	@NotNull(message = "Tienes que poner cuanto has gastado")
	private int cantidad;
	
	@Column(name = "motivo")
	@NotNull(message = "Tienes que poner el porque de este gasto")
	private String motivo;
	
	@Column(name = "proveedor")
	@NotNull(message = "Tienes que poner de donde viene ese gasto")
	private String proveedor;
	
	public Gasto() {
		
	}

	public Gasto(int cantidad, String motivo, String proveedor) {
		this.cantidad = cantidad;
		this.motivo = motivo;
		this.proveedor = proveedor;
	}

	public int getId_gasto() {
		return id_gasto;
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
