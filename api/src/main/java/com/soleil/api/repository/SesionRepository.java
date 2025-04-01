package com.soleil.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.soleil.api.model.Sesion;

public interface SesionRepository extends JpaRepository<Sesion, Integer> {
	Page<Sesion> findAll(Pageable pageable);
}
