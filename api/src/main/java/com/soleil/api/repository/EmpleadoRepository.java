package com.soleil.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.soleil.api.model.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, String> {
	Page<Empleado> findAll(Pageable pageable);
	
	List<Empleado> findByUsuarioAndContrasena(String usuario, String contrasena);
	
	@Query("SELECT e FROM Empleado e WHERE e.usuario = :usuario")
    Optional<Empleado> findByUsuario(@Param("usuario") String usuario);
}
