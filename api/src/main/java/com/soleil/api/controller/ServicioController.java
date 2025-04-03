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

import com.soleil.api.model.Servicio;
import com.soleil.api.service.ServicioService;

@RestController
@RequestMapping("/servicio")
public class ServicioController {
	
	@Autowired
	private ServicioService servicio;
	
	@GetMapping
    public List<Servicio> listarServicio() {
        return servicio.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<Servicio> obtenerServicio(@PathVariable int id) {
        return servicio.obtenerPorId(id);
    }

    @PostMapping
    public Servicio crearServicio(@RequestBody Servicio service) {
        return servicio.guardarServicio(service);
    }

    @DeleteMapping("/{id}")
    public void eliminarServicio(@PathVariable int id) {
        servicio.eliminarServicio(id);
    }
    
    @PutMapping("/{id}")
    public void actualizarServicio(@PathVariable int id, @RequestBody Servicio service) {
    	servicio.actualizarServicio(id, service);
    }
	
}
