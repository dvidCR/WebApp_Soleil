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

import com.soleil.api.dto.PacienteDTO;
import com.soleil.api.dto.PacienteDniDTO;
import com.soleil.api.dto.TratamientoDTO;
import com.soleil.api.model.Paciente;
import com.soleil.api.service.PacienteService;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
	
	@Autowired
	private PacienteService servicio;
	
	@GetMapping
    public List<PacienteDTO> listarPaciente() {
		return servicio.obtenerTodos().stream().map(paciente -> {
	        PacienteDTO dto = new PacienteDTO();
	        dto.setDni(paciente.getDni());
	        dto.setNombre(paciente.getNombre());
	        dto.setApellidos(paciente.getApellidos());
	        if (paciente.getDni_empleado() != null) {
	            dto.setDni_empleado(paciente.getDni_empleado().getDni());
	        } else {
	            dto.setDni_empleado(null);
	        }
	        return dto;
	    }).toList();
    }

    @GetMapping("/{dni}")
    public List<PacienteDTO> obtenerPaciente(@PathVariable String dni) {
        return servicio.obtenerPorDni(dni).stream().map(paciente -> {
	        PacienteDTO dto = new PacienteDTO();
	        dto.setDni(paciente.getDni());
	        dto.setNombre(paciente.getNombre());
	        dto.setApellidos(paciente.getApellidos());
	        if (paciente.getDni_empleado() != null) {
	            dto.setDni_empleado(paciente.getDni_empleado().getDni());
	        } else {
	            dto.setDni_empleado(null);
	        }
	        return dto;
	    }).toList();
    }

    @PostMapping
    public Paciente crearPaciente(@RequestBody Paciente paciente) {
        return servicio.guardarPaciente(paciente);
    }

    @DeleteMapping("/{dni}")
    public void eliminarPaciente(@PathVariable String dni) {
        servicio.eliminarPaciente(dni);
    }
    
    @PutMapping("/{dni}")
    public void actualizarPaciente(@PathVariable String dni, @RequestBody Paciente paciente) {
    	servicio.actualizarPaciente(dni, paciente);
    }
    
    @PutMapping("/dni/{dni}")
    public void actualizarDni(@PathVariable String dni, @RequestBody PacienteDniDTO nuevoDni) {
    	Paciente pacienteActualizado = new Paciente();
        pacienteActualizado.setDni(nuevoDni.getDni());
    	servicio.actualizarDni(dni, nuevoDni.getDni());
    }
    
    @GetMapping("/{dni}/verTratamiento")
    public List<TratamientoDTO> verTratamiento(@PathVariable String dni) {
        return servicio.verTratamiento(dni);
    }

	
}
