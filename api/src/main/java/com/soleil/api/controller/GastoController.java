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

import com.soleil.api.dto.GastoDTO;
import com.soleil.api.model.Gasto;
import com.soleil.api.service.GastoService;

@RestController
@RequestMapping("/gasto")
public class GastoController {
	
	@Autowired
	private GastoService servicio;
	
	@GetMapping
    public List<GastoDTO> listarGasto() {
        return servicio.obtenerTodos().stream().map(gasto -> {
	        GastoDTO dto = new GastoDTO();
	        dto.setId_gasto(gasto.getId_gasto());
	        dto.setCantidad(gasto.getCantidad());
	        dto.setMotivo(gasto.getMotivo());
	        dto.setProveedor(gasto.getProveedor());
	        return dto;
	    }).toList();
    }

    @GetMapping("/{id}")
    public List<GastoDTO> obtenerGasto(@PathVariable int id) {
        return servicio.obtenerPorId(id).stream().map(gasto -> {
	        GastoDTO dto = new GastoDTO();
	        dto.setId_gasto(gasto.getId_gasto());
	        dto.setCantidad(gasto.getCantidad());
	        dto.setMotivo(gasto.getMotivo());
	        dto.setProveedor(gasto.getProveedor());
	        return dto;
	    }).toList();
    }

    @PostMapping
    public Gasto crearGasto(@RequestBody Gasto gasto) {
        return servicio.guardarGasto(gasto);
    }

    @DeleteMapping("/{id}")
    public void eliminarGasto(@PathVariable int id) {
        servicio.eliminarGasto(id);
    }
    
    @PutMapping("/{id}")
    public void actualizarGasto(@PathVariable int id, @RequestBody Gasto gasto) {
    	servicio.actualizarGasto(id, gasto);
    }
	
}
