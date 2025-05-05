package com.soleil.api.controller;

import java.time.LocalDate;
import java.time.LocalTime;
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

import com.soleil.api.model.Empleado;
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
    public Fichaje crearFichaje(@RequestBody Empleado dni_empleado) {
        return servicio.guardarFichaje(new Fichaje(LocalDate.now(), LocalTime.now(), dni_empleado));
    }

    @DeleteMapping("/{id}")
    public void eliminarFichaje(@PathVariable int id) {
        servicio.eliminarFichaje(id);
    }
    
//    @PutMapping("/{id}/entrada")
//    public void actualizarHoraEntrada(@PathVariable int id, @RequestBody Fichaje fichaje) {
//    	servicio.actualizarHoraEntrada(id, fichaje);
//    }
    
    @PutMapping("/{id}")
    public void actualizarHoraSalida(@PathVariable int id) {
    	servicio.actualizarHoraSalida(id, new Fichaje(LocalTime.now()));
    }
	
}
