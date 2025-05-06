package com.soleil.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EmpleadoDTO {

    @NotNull(message = "El DNI no puede ser nulo")
    @Size(min = 9, max = 9, message = "El DNI debe tener exactamente 9 caracteres")
    private String dni;
    
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
