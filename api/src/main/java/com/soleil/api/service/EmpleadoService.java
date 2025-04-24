package com.soleil.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soleil.api.model.Empleado;
import com.soleil.api.repository.EmpleadoRepository;

@Service
public class EmpleadoService {
	
	@Autowired
    private EmpleadoRepository repositorio;
	
	public List<Empleado> obtenerTodos() {
		return repositorio.findAll();
	}
	
	public Optional<Empleado> obtenerPorDni(String dni) {
		return repositorio.findById(dni);
	}
	
	public List<Empleado> obtenerUsuario(String usuario, String contrasena) {
		return repositorio.findByUsuarioAndContrasena(usuario, contrasena);
	}
	
	public Empleado guardarEmpleado(Empleado empleado) {
		return repositorio.save(empleado);
	}
	
	public void eliminarEmpleado(String dni) {
		repositorio.deleteById(dni);
	}
	
	public Empleado actualizarEmpleado(String dni, Empleado empleadoActualizada) {
        return repositorio.findById(dni).map(empleado -> {
        	empleado.setNombre(empleadoActualizada.getNombre());
        	empleado.setApellidos(empleadoActualizada.getApellidos());
        	empleado.setCorreo(empleadoActualizada.getCorreo());
        	empleado.setUsuario(empleadoActualizada.getUsuario());
        	empleado.setContrasena(empleadoActualizada.getContrasena());
        	empleado.setRol(empleadoActualizada.getRol());
            return repositorio.save(empleado);
        }).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
	}
	
}
