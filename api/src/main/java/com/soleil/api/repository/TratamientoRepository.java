package com.soleil.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.soleil.api.model.Tratamiento;

public interface TratamientoRepository extends JpaRepository<Tratamiento, Integer> {
	Page<Tratamiento> findAll(Pageable pageable);
	
	@Query("SELECT t.nombre, COUNT(t) FROM Tratamiento t WHERE t.paciente.dni = :dni GROUP BY t.nombre")
	List<Object[]> verTratamiento(@org.springframework.data.repository.query.Param("dni") String dni);

}
