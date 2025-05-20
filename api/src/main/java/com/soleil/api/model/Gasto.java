package com.soleil.api.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "gasto")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Gasto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_gasto;
    
    @Column(name = "cantidad")
    @NotNull(message = "Tienes que poner cuanto has gastado")
    private double cantidad;
    
    @Column(name = "motivo")
    @NotNull(message = "Tienes que poner el porque de este gasto")
    private String motivo;
    
    @Column(name = "proveedor")
    @NotNull(message = "Tienes que poner de donde viene ese gasto")
    private String proveedor;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha")
    @NotNull(message = "Tienes que poner la fecha en la que se hizo la compra")
    private LocalDate fecha;
    
    public Gasto() {}

    public Gasto(double cantidad, String motivo, String proveedor, LocalDate fecha) {
        this.cantidad = cantidad;
        this.motivo = motivo;
        this.proveedor = proveedor;
        this.fecha = fecha;
    }

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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}