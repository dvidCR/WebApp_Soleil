package com.soleil.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soleil.api.dto.TratamientoDTO;
import com.soleil.api.model.Tratamiento;
import com.soleil.api.service.TratamientoService;

@RestController
@RequestMapping("/tratamiento")
public class TratamientoController {
	
	@Autowired
	private TratamientoService servicio;
	
	@GetMapping
    public List<TratamientoDTO> listarTratamiento() {
		return servicio.obtenerTodos().stream().map(tratamiento -> {
	        TratamientoDTO dto = new TratamientoDTO();
	        dto.setId_tratamiento(tratamiento.getId_tratamiento());
	        dto.setTipo_tratamiento(tratamiento.getTipo_tratamiento());
	        dto.setDescripcion(tratamiento.getDescripcion());
	        if (tratamiento.getDni_paciente() != null) {
	            dto.setDni_paciente(tratamiento.getDni_paciente().getDni());
	        } else {
	            dto.setDni_paciente(null);
	        }
	        return dto;
	    }).toList();
    }

    @GetMapping("/{id}")
    public List<TratamientoDTO> obtenerTratamiento(@PathVariable int id) {
        return servicio.obtenerPorId(id).stream().map(tratamiento -> {
	        TratamientoDTO dto = new TratamientoDTO();
	        dto.setId_tratamiento(tratamiento.getId_tratamiento());
	        dto.setTipo_tratamiento(tratamiento.getTipo_tratamiento());
	        dto.setDescripcion(tratamiento.getDescripcion());
	        if (tratamiento.getDni_paciente() != null) {
	            dto.setDni_paciente(tratamiento.getDni_paciente().getDni());
	        } else {
	            dto.setDni_paciente(null);
	        }
	        return dto;
	    }).toList();
    }

    @PostMapping
    public Tratamiento crearTratamiento(@RequestBody Tratamiento tratamiento) {
        return servicio.guardarTratamiento(tratamiento);
    }

    @DeleteMapping("/{id}")
    public void eliminarTratamiento(@PathVariable int id) {
        servicio.eliminarTratamiento(id);
    }
    
    @PutMapping("/{id}")
    public void actualizarTratamiento(@PathVariable int id, @RequestBody Tratamiento tratamiento) {
    	servicio.actualizarTratamiento(id, tratamiento);
    }
	
}
