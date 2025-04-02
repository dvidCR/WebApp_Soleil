package com.soleil.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soleil.api.model.Fichaje;
import com.soleil.api.repository.FichajeRepository;

@Service
public class FichajeService {
	
	@Autowired
    private FichajeRepository repositorio;
	
	public List<Fichaje> obtenerTodos() {
		return repositorio.findAll();
	}
	
	public Optional<Fichaje> obtenerPorId(int id) {
		return repositorio.findById(id);
	}
	
	public Fichaje guardarFichaje(Fichaje fichaje) {
		return repositorio.save(fichaje);
	}
	
	public void eliminarFichaje(int id) {
		repositorio.deleteById(id);
	}
	
	public Fichaje actualizarHoraEntrada(int id, Fichaje fichajeActualizada) {
        return repositorio.findById(id).map(fichaje -> {
        	fichaje.setHora_entrada(fichajeActualizada.getHora_entrada());
            return repositorio.save(fichaje);
        }).orElseThrow(() -> new RuntimeException("Dia no encontrado"));
	}
	
	public Fichaje actualizarHoraSalida(int id, Fichaje fichajeActualizada) {
        return repositorio.findById(id).map(fichaje -> {
        	fichaje.setHora_salida(fichajeActualizada.getHora_salida());
            return repositorio.save(fichaje);
        }).orElseThrow(() -> new RuntimeException("Dia no encontrado"));
	}
	
}
