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

import com.soleil.api.dto.EmpleadoDTO;
import com.soleil.api.dto.EmpleadoDniDTO;
import com.soleil.api.model.Empleado;
import com.soleil.api.service.EmpleadoService;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {
	
	@Autowired
	private EmpleadoService servicio;
	
	@GetMapping
    public List<EmpleadoDTO> listarEmpleados() {
        return servicio.obtenerTodos().stream().map(empleado -> {
	        EmpleadoDTO dto = new EmpleadoDTO();
	        dto.setDni(empleado.getDni());
	        dto.setNombre(empleado.getNombre());
	        dto.setApellidos(empleado.getApellidos());
	        dto.setCorreo(empleado.getCorreo());
	        dto.setUsuario(empleado.getUsuario());
	        dto.setContrasena(empleado.getContrasena());
	        dto.setRol(empleado.getRol());
	        return dto;
	    }).toList();
	}

    @GetMapping("/{dni}")
    public List<EmpleadoDTO> obtenerEmpleado(@PathVariable String dni) {
        return servicio.obtenerPorDni(dni).stream().map(empleado -> {
	        EmpleadoDTO dto = new EmpleadoDTO();
	        dto.setDni(empleado.getDni());
	        dto.setNombre(empleado.getNombre());
	        dto.setApellidos(empleado.getApellidos());
	        dto.setCorreo(empleado.getCorreo());
	        dto.setUsuario(empleado.getUsuario());
	        dto.setContrasena(empleado.getContrasena());
	        dto.setRol(empleado.getRol());
	        return dto;
	    }).toList();
    }

    @PostMapping
    public Empleado crearEmpleado(@RequestBody Empleado empleado) {
        return servicio.guardarEmpleado(empleado);
    }

    @DeleteMapping("/{dni}")
    public void eliminarEmpleado(@PathVariable String dni) {
        servicio.eliminarEmpleado(dni);
    }
    
    @PutMapping("/{dni}")
    public void actualizarEmpleado(@PathVariable String dni, @RequestBody Empleado empleado) {
    	servicio.actualizarEmpleado(dni, empleado);
    }
    
    @PutMapping("/dni/{dni}")
    public void actualizarDni(@PathVariable String dni, @RequestBody EmpleadoDniDTO nuevoDni) {
    	Empleado empleadoActualizado = new Empleado();
        empleadoActualizado.setDni(nuevoDni.getDni());
    	servicio.actualizarDni(dni, nuevoDni.getDni());
    }
	
}