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

import com.soleil.api.dto.TratamientoDTO;
import com.soleil.api.model.Paciente;
import com.soleil.api.model.Tratamiento;
import com.soleil.api.repository.PacienteRepository;
import com.soleil.api.repository.TratamientoRepository;

@Service
public class PacienteService {
	
	@Autowired
    private PacienteRepository repositorio;
	
	@Autowired
	private TratamientoRepository tratamientoRepository;
	
	public List<Paciente> obtenerTodos() {
		return repositorio.findAll();
	}
	
	public Optional<Paciente> obtenerPorDni(String dni) {
		return repositorio.findById(dni);
	}
	
	public Paciente guardarPaciente(Paciente paciente) {
		return repositorio.save(paciente);
	}
	
	public void eliminarPaciente(String dni) {
		repositorio.deleteById(dni);
	}
	
	public Paciente actualizarPaciente(String dni, Paciente pacienteActualizada) {
        return repositorio.findById(dni).map(paciente -> {
        	paciente.setNombre(pacienteActualizada.getNombre());
        	paciente.setApellidos(pacienteActualizada.getApellidos());
            return repositorio.save(paciente);
        }).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
	}
	
	@Transactional
	public Paciente actualizarDni(String dniAntiguo, String nuevoDni) {
	    if (repositorio.existsById(nuevoDni)) {
	        throw new RuntimeException("El nuevo DNI ya esta en uso por otro paciente");
	    }

	    Paciente pacienteViejo = repositorio.findById(dniAntiguo)
	            .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

	    Paciente pacienteNuevo = new Paciente(
	        nuevoDni,
	        pacienteViejo.getNombre(),
	        pacienteViejo.getApellidos(),
	        pacienteViejo.getDni_empleado()
	    );
	    repositorio.save(pacienteNuevo);
	    repositorio.flush();

	    List<Tratamiento> nuevoTratamiento = pacienteViejo.getTratamiento();
	    for (Tratamiento tratamiento : nuevoTratamiento) {
	        tratamiento.setDni_paciente(pacienteNuevo);
	    }
	    tratamientoRepository.saveAll(nuevoTratamiento);

	    eliminarPaciente(dniAntiguo);

	    return pacienteNuevo;
	}

	
	public List<TratamientoDTO> verTratamiento(String dni) {
	    return tratamientoRepository.buscarTratamientosPorDniPaciente(dni).stream()
	        .map(t -> new TratamientoDTO(t.getTipo_tratamiento(), t.getDescripcion()))
	        .toList();
	}

	
	public Page<Paciente> listarEmpleadosPaginados(int page, int size) {
	    Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").descending());
	    return repositorio.findAll(pageable);
	}
	
}
