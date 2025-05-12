package com.soleil.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PacienteDniDTO {
	
	@NotNull(message = "El DNI no puede ser nulo")
    @Size(min = 9, max = 9)
    private String dni;
    
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
	
}
