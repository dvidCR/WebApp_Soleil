package com.soleil.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.soleil.api.model.Servicio;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
	Page<Servicio> findAll(Pageable pageable);
	
	@Query("""
		    SELECT s FROM Servicio s 
		    WHERE 
		        LOWER(s.dni_empleado.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR 
		        LOWER(s.dni_empleado.apellidos) LIKE LOWER(CONCAT('%', :filtro, '%')) OR 
		        LOWER(s.dni_empleado.dni) LIKE LOWER(CONCAT('%', :filtro, '%'))
		""")
		List<Servicio> buscarPorFiltroEmpleado(@Param("filtro") String filtro);

}