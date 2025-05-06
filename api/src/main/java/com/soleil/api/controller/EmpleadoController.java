package com.soleil.api.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soleil.api.dto.EmpleadoDTO;
import com.soleil.api.model.Empleado;
import com.soleil.api.service.EmpleadoService;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {
	
	@Autowired
	private EmpleadoService servicio;
	
	@GetMapping
    public List<Empleado> listarEmpleados() {
        return servicio.obtenerTodos();
    }

    @GetMapping("/{dni}")
    public Optional<Empleado> obtenerEmpleado(@PathVariable String dni) {
        return servicio.obtenerPorDni(dni);
    }
    
    @PostMapping("/login")
	public ResponseEntity<List<Empleado>> obtenerUsuario(@RequestBody Map<String, String> datos) {
		String usuario = datos.get("usuario");
		String contrasena = datos.get("contrasena");
		List<Empleado> empleados = servicio.obtenerUsuario(usuario, contrasena);

		if (empleados.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} else {
			return ResponseEntity.ok(empleados);
		}
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
    public void actualizarDni(@PathVariable String dni, @RequestBody EmpleadoDTO nuevoDni) {
    	Empleado empleadoActualizado = new Empleado();
        empleadoActualizado.setDni(nuevoDni.getDni());
    	servicio.actualizarDni(dni, nuevoDni.getDni());
    }
	
}
