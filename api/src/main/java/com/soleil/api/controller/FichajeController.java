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

import com.soleil.api.dto.EmpleadoDTO;
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
	    return servicio.obtenerTodos().stream().map(f -> {
	        FichajeDTO dto = new FichajeDTO();
	        dto.setId_fichaje(f.getId_fichaje());
	        dto.setFecha(f.getFecha());
	        dto.setHora_entrada(f.getHora_entrada());
	        dto.setHora_salida(f.getHora_salida());
	        dto.setDni_empleado(f.getEmpleado().getDni());
	        return dto;
	    }).toList();
	}


    @GetMapping("/{id}")
    public Optional<Fichaje> obtenerFichaje(@PathVariable int id) {
        return servicio.obtenerPorId(id);
    }

    @PostMapping
    public Fichaje crearFichaje(@RequestBody @Valid EmpleadoDTO dto) {
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
