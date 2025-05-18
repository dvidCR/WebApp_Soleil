package com.soleil.api.controller;

import java.time.LocalDate;
import java.time.LocalTime;
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

import com.soleil.api.dto.EmpleadoDniDTO;
import com.soleil.api.dto.FichajeDTO;
import com.soleil.api.model.Empleado;
import com.soleil.api.model.Fichaje;
import com.soleil.api.service.FichajeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/fichaje")
public class FichajeController {
	
	@Autowired
	private FichajeService servicio;
	
	@GetMapping
	public List<FichajeDTO> listarFichaje() {
	    return servicio.obtenerTodos().stream().map(fichaje -> {
	        FichajeDTO dto = new FichajeDTO();
	        dto.setId_fichaje(fichaje.getId_fichaje());
	        dto.setFecha(fichaje.getFecha());
	        dto.setHora_entrada(fichaje.getHora_entrada());
	        dto.setHora_salida(fichaje.getHora_salida());
	        dto.setDni_empleado(fichaje.getDni_empleado().getDni());
	        return dto;
	    }).toList();
	}


    @GetMapping("/{id}")
    public List<FichajeDTO> obtenerFichaje(@PathVariable int id) {
        return servicio.obtenerPorId(id).stream().map(fichaje -> {
	        FichajeDTO dto = new FichajeDTO();
	        dto.setId_fichaje(fichaje.getId_fichaje());
	        dto.setFecha(fichaje.getFecha());
	        dto.setHora_entrada(fichaje.getHora_entrada());
	        dto.setHora_salida(fichaje.getHora_salida());
	        dto.setDni_empleado(fichaje.getDni_empleado().getDni());
	        return dto;
	    }).toList();
    }

    @PostMapping
    public Fichaje crearFichaje(@RequestBody @Valid EmpleadoDniDTO dto) {
        Empleado empleado = new Empleado(dto.getDni());
        return servicio.guardarFichaje(new Fichaje(LocalDate.now(), LocalTime.now(), empleado));
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
