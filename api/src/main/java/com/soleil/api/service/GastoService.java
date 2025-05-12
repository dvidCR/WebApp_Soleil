package com.soleil.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.soleil.api.model.Gasto;
import com.soleil.api.repository.GastoRepository;

@Service
public class GastoService {
	
	@Autowired
    private GastoRepository repositorio;
	
	public List<Gasto> obtenerTodos() {
		return repositorio.findAll();
	}
	
	public Optional<Gasto> obtenerPorId(int id) {
		return repositorio.findById(id);
	}
	
	public Gasto guardarGasto(Gasto gasto) {
		return repositorio.save(gasto);
	}
	
	public void eliminarGasto(int id) {
		repositorio.deleteById(id);
	}
	
	public Gasto actualizarGasto(int id, Gasto gastoActualizada) {
        return repositorio.findById(id).map(gasto -> {
        	gasto.setCantidad(gastoActualizada.getCantidad());
        	gasto.setMotivo(gastoActualizada.getMotivo());
        	gasto.setProveedor(gastoActualizada.getProveedor());
            return repositorio.save(gasto);
        }).orElseThrow(() -> new RuntimeException("Gasto no encontrado"));
	}

    public double calcularTotalGastos() {
        return repositorio.findAll().stream()
            .mapToDouble(Gasto::getCantidad)
            .sum();
    }
	
	public Page<Gasto> listarEmpleadosPaginados(int page, int size) {
	    Pageable pageable = PageRequest.of(page, size, Sort.by("proveedor").descending());
	    return repositorio.findAll(pageable);
	}
	
}
