package com.soleil.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.soleil.api.model.Servicio;
import com.soleil.api.repository.ServicioRepository;

@Service
public class ServicioService {
	
	@Autowired
    private ServicioRepository repositorio;
	
	public List<Servicio> obtenerTodos() {
		return repositorio.findAll();
	}
	
	public Optional<Servicio> obtenerPorId(int id) {
		return repositorio.findById(id);
	}
	
	public List<Servicio> buscarServiciosPorFiltroEmpleado(String filtro) {
	    return repositorio.buscarPorFiltroEmpleado(filtro);
	}
	
	public Servicio guardarServicio(Servicio servicio) {
		return repositorio.save(servicio);
	}
	
	public void eliminarServicio(int id) {
		repositorio.deleteById(id);
	}
	
	public Servicio actualizarServicio(int id, Servicio servicoActualizada) {
        return repositorio.findById(id).map(servicio -> {
        	servicio.setFecha_cita(servicoActualizada.getFecha_cita());
        	servicio.setDni_empleado(servicoActualizada.getDni_empleado());
        	servicio.setDni_paciente(servicoActualizada.getDni_paciente());
        	servicio.setId_tratamiento(servicoActualizada.getId_tratamiento());
        	servicio.setModo_pago(servicoActualizada.getModo_pago());
        	servicio.setTarifa(servicoActualizada.getTarifa());
        	servicio.setConcepto(servicoActualizada.getConcepto());
        	servicio.setNum_sesiones(servicoActualizada.getNum_sesiones());
            return repositorio.save(servicio);
        }).orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
	}
	
	public double calcularTotalIngresos() {
        return repositorio.findAll().stream()
            .mapToDouble(s -> s.getTarifa() * s.getNum_sesiones())
            .sum();
    }
	
	public Page<Servicio> listarEmpleadosPaginados(int page, int size) {
	    Pageable pageable = PageRequest.of(page, size, Sort.by("dni_empleado").descending());
	    return repositorio.findAll(pageable);
	}
	
}
