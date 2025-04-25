package com.soleil.api.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.soleil.api.model.Paciente;
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
	
	public Map<String, Integer> verTratamiento(String dni) {
        List<Object[]> resultados = tratamientoRepository.verTratamiento(dni);
        Map<String, Integer> tratamientoPorDni = new LinkedHashMap<>();
        for (Object[] resultado : resultados) {
        	tratamientoPorDni.put(resultado[0].toString(), ((Number) resultado[1]).intValue());
        }
        return tratamientoPorDni;
    }
	
	public Page<Paciente> listarEmpleadosPaginados(int page, int size) {
	    Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").descending());
	    return repositorio.findAll(pageable);
	}
	
}
