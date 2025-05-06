package com.soleil.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.soleil.api.model.Empleado;
import com.soleil.api.model.Fichaje;
import com.soleil.api.model.Paciente;
import com.soleil.api.model.Servicio;
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
	
	public Empleado actualizarDni(String dni, String nuevoDni) {
        if (obtenerPorDni(nuevoDni).isPresent()) {
            throw new RuntimeException("El nuevo DNI ya esta registrado en otro empleado");
        }
        
        Optional<Empleado> empleadoViejoOpt = repositorio.findById(dni);
        Empleado empleadoViejo = empleadoViejoOpt.get();
        
        Empleado empleadoNuevo = new Empleado(
        			nuevoDni,
        			empleadoViejo.getNombre(),
                    empleadoViejo.getApellidos(),
                    empleadoViejo.getCorreo(),
                    empleadoViejo.getContrasena(),
                    empleadoViejo.getUsuario(),
                    empleadoViejo.getRol()
        		);
        
        actualizarRelaciones(empleadoViejo, empleadoNuevo);
        eliminarEmpleado(dni);
        
        return repositorio.save(empleadoNuevo);
	}
	
	private void actualizarRelaciones(Empleado empleadoViejo, Empleado empleadoNuevo) {
	    for (Fichaje fichaje : empleadoViejo.getFichaje()) {
	        fichaje.setEmpleado(empleadoNuevo);
	    }

	    for (Paciente paciente : empleadoViejo.getPaciente()) {
	        paciente.setDni_empleado(empleadoNuevo);
	    }

	    for (Servicio servicio : empleadoViejo.getServicio()) {
	        servicio.setDni_empleado(empleadoNuevo);
	    }
	}
	
	public Page<Empleado> listarEmpleadosPaginados(int page, int size) {
	    Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").descending());
	    return repositorio.findAll(pageable);
	}
	
}