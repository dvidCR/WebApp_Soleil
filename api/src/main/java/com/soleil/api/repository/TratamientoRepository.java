package com.soleil.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soleil.api.model.Tratamiento;

@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Integer> {
	Page<Tratamiento> findAll(Pageable pageable);
	
	@Query("SELECT t.tipo_tratamiento, COUNT(t) FROM Tratamiento t WHERE t.dni_paciente.dni = :dni GROUP BY t.tipo_tratamiento")
	List<Object[]> verTratamiento(@Param("dni") String dni);
	
	@Query("SELECT t FROM Tratamiento t WHERE t.dni_paciente.dni = :dni")
	List<Tratamiento> buscarTratamientosPorDniPaciente(@Param("dni") String dni);
	
}