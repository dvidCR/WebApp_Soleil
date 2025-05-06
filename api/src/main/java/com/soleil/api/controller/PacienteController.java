package com.soleil.api.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soleil.api.model.Paciente;
import com.soleil.api.service.PacienteService;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
	
	@Autowired
	private PacienteService servicio;
	
	@GetMapping
    public List<Paciente> listarPaciente() {
        return servicio.obtenerTodos();
    }

    @GetMapping("/{dni}")
    public Optional<Paciente> obtenerPaciente(@PathVariable String dni) {
        return servicio.obtenerPorDni(dni);
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
    public void actualizarDni(@PathVariable String dni, @RequestBody Paciente paciente) {
    	servicio.actualizarDni(dni, paciente);
    }
    
    @GetMapping("/{dni}/verTratamiento")
    public Map<String, Integer> verTratamiento(@PathVariable String dni) {
    	return servicio.verTratamiento(dni);
    }
	
}
