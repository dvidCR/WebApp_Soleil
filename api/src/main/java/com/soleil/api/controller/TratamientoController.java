package com.soleil.api.controller;

import java.util.List;
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

import com.soleil.api.model.Gasto;
import com.soleil.api.model.Tratamiento;
import com.soleil.api.service.TratamientoService;

@RestController
@RequestMapping("/tratamiento")
public class TratamientoController {
	
	@Autowired
	private TratamientoService servicio;
	
	@GetMapping
    public List<Tratamiento> listarTratamiento() {
        return servicio.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<Tratamiento> obtenerTratamiento(@PathVariable int id) {
        return servicio.obtenerPorId(id);
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
    	servicio.actualizarGasto(id, tratamiento);
    }
	
}
