package com.soleil.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soleil.api.model.Empleado;
import com.soleil.api.model.Fichaje;
import com.soleil.api.model.Paciente;
import com.soleil.api.model.Servicio;
import com.soleil.api.repository.EmpleadoRepository;
import com.soleil.api.repository.FichajeRepository;
import com.soleil.api.repository.PacienteRepository;
import com.soleil.api.repository.ServicioRepository;

@Service
public class EmpleadoService {
	
	@Autowired
    private EmpleadoRepository repositorio;
	
	@Autowired
    private FichajeRepository fichajeRepository;
	
	@Autowired
    private PacienteRepository pacienteRepository;
	
	@Autowired
    private ServicioRepository servicioRepository;
	
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
	
	@Transactional
	public Empleado actualizarDni(String dni, String nuevoDni) {
	    if (obtenerPorDni(nuevoDni).isPresent()) {
	        throw new RuntimeException("El nuevo DNI ya esta registrado");
	    }

	    Empleado empleadoViejo = repositorio.findById(dni)
	        .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

	    Empleado empleadoNuevo = new Empleado(
	        nuevoDni,
	        empleadoViejo.getNombre(),
	        empleadoViejo.getApellidos(),
	        empleadoViejo.getCorreo(),
	        empleadoViejo.getUsuario(),
	        empleadoViejo.getContrasena(),
	        empleadoViejo.getRol()
	    );

	    empleadoNuevo = repositorio.save(empleadoNuevo);

	    List<Fichaje> nuevosFichajes = empleadoViejo.getFichaje();
	    for (Fichaje f : nuevosFichajes) {
	        f.setEmpleado(empleadoNuevo);
	    }
	    fichajeRepository.saveAll(nuevosFichajes);

	    List<Paciente> nuevosPacientes = empleadoViejo.getPaciente();
	    for (Paciente p : nuevosPacientes) {
	        p.setDni_empleado(empleadoNuevo);
	    }
	    pacienteRepository.saveAll(nuevosPacientes);

	    List<Servicio> nuevosServicios = empleadoViejo.getServicio();
	    for (Servicio s : nuevosServicios) {
	        s.setDni_empleado(empleadoNuevo);
	    }
	    servicioRepository.saveAll(nuevosServicios);

	    eliminarEmpleado(dni);

	    return empleadoNuevo;
	}
	
	public Page<Empleado> listarEmpleadosPaginados(int page, int size) {
	    Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").descending());
	    return repositorio.findAll(pageable);
	}
	
}