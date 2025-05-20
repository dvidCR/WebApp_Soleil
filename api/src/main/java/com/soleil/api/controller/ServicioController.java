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

import com.soleil.api.dto.ServicioDTO;
import com.soleil.api.model.Servicio;
import com.soleil.api.service.ServicioService;

@RestController
@RequestMapping("/servicio")
public class ServicioController {
	
	@Autowired
	private ServicioService servicio;
	
	@GetMapping
    public List<ServicioDTO> listarServicio() {
		return servicio.obtenerTodos().stream().map(servicio -> {
	        ServicioDTO dto = new ServicioDTO();
	        dto.setId_servicio(servicio.getId_servicio());
	        dto.setFecha_cita(servicio.getFecha_cita());
	        if (servicio.getDni_empleado() != null) {
	            dto.setDni_empleado(servicio.getDni_empleado().getDni());
	        } else {
	            dto.setDni_empleado(null);
	        }
	        if (servicio.getDni_paciente() != null) {
	            dto.setDni_paciente(servicio.getDni_paciente().getDni());
	        } else {
	            dto.setDni_paciente(null);
	        }
	        dto.setId_tratamiento(servicio.getId_tratamiento().getId_tratamiento());
	        dto.setModo_pago(servicio.getModo_pago());
	        dto.setTarifa(servicio.getTarifa());
	        dto.setConcepto(servicio.getConcepto());
	        dto.setNum_sesiones(servicio.getNum_sesiones());
	        return dto;
	    }).toList();
    }

    @GetMapping("/{id}")
    public List<ServicioDTO> obtenerServicio(@PathVariable int id) {
        return servicio.obtenerPorId(id).stream().map(servicio -> {
	        ServicioDTO dto = new ServicioDTO();
	        dto.setId_servicio(servicio.getId_servicio());
	        dto.setFecha_cita(servicio.getFecha_cita());
	        if (servicio.getDni_empleado() != null) {
	            dto.setDni_empleado(servicio.getDni_empleado().getDni());
	        } else {
	            dto.setDni_empleado(null);
	        }
	        if (servicio.getDni_paciente() != null) {
	            dto.setDni_paciente(servicio.getDni_paciente().getDni());
	        } else {
	            dto.setDni_paciente(null);
	        }
	        dto.setId_tratamiento(servicio.getId_tratamiento().getId_tratamiento());
	        dto.setModo_pago(servicio.getModo_pago());
	        dto.setTarifa(servicio.getTarifa());
	        dto.setConcepto(servicio.getConcepto());
	        dto.setNum_sesiones(servicio.getNum_sesiones());
	        return dto;
	    }).toList();
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
