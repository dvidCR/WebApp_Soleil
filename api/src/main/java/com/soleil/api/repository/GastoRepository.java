package com.soleil.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.soleil.api.model.Gasto;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Integer> {
	Page<Gasto> findAll(Pageable pageable);
	
	@Query("SELECT g FROM Gasto g WHERE LOWER(g.proveedor) LIKE LOWER(CONCAT('%', :proveedor, '%'))")
	List<Gasto> buscarPorProveedor(@Param("proveedor") String proveedor);
	
	@Query("SELECT DISTINCT g.proveedor FROM Gasto g WHERE g.proveedor IS NOT NULL")
	List<String> findProveedoresUnicos();

}