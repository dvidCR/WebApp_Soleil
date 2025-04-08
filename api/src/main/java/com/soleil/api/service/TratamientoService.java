package com.soleil.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soleil.api.model.Tratamiento;
import com.soleil.api.repository.TratamientoRepository;

@Service
public class TratamientoService {
	
	@Autowired
    private TratamientoRepository repositorio;
	
	public List<Tratamiento> obtenerTodos() {
		return repositorio.findAll();
	}
	
	public Optional<Tratamiento> obtenerPorId(int id) {
		return repositorio.findById(id);
	}
	
	public Tratamiento guardarTratamiento(Tratamiento tratamiento) {
		return repositorio.save(tratamiento);
	}
	
	public void eliminarTratamiento(int id) {
		repositorio.deleteById(id);
	}
	
	public Tratamiento actualizarTratmiento(int id, Tratamiento tratamientoActualizada) {
        return repositorio.findById(id).map(tratamiento -> {
        	tratamiento.setTipo_tratamiento(tratamientoActualizada.getTipo_tratamiento());
        	tratamiento.setDni_paciente(tratamientoActualizada.getDni_paciente());
            return repositorio.save(tratamiento);
        }).orElseThrow(() -> new RuntimeException("Tratamiento no encontrado"));
	}
	
}
