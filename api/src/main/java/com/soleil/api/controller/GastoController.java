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
import com.soleil.api.service.GastoService;

@RestController
@RequestMapping("/gasto")
public class GastoController {
	
	@Autowired
	private GastoService servicio;
	
	@GetMapping
    public List<Gasto> listarGasto() {
        return servicio.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<Gasto> obtenerGasto(@PathVariable int id) {
        return servicio.obtenerPorId(id);
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
