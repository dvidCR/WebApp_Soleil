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

import com.soleil.api.model.Fichaje;
import com.soleil.api.service.FichajeService;

@RestController
@RequestMapping("/fichaje")
public class FichajeController {
	
	@Autowired
	private FichajeService servicio;
	
	@GetMapping
    public List<Fichaje> listarFichaje() {
        return servicio.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<Fichaje> obtenerFichaje(@PathVariable int id) {
        return servicio.obtenerPorId(id);
    }

    @PostMapping
    public Fichaje crearFichaje(@RequestBody Fichaje fichaje) {
        return servicio.guardarFichaje(fichaje);
    }

    @DeleteMapping("/{id}")
    public void eliminarFichaje(@PathVariable int id) {
        servicio.eliminarFichaje(id);
    }
    
    @PutMapping("/{id}/entrada")
    public void actualizarHoraEntrada(@PathVariable int id, @RequestBody Fichaje fichaje) {
    	servicio.actualizarHoraEntrada(id, fichaje);
    }
    
    @PutMapping("/{id}/salida")
    public void actualizarHoraSalida(@PathVariable int id, @RequestBody Fichaje fichaje) {
    	servicio.actualizarHoraSalida(id, fichaje);
    }
	
}
