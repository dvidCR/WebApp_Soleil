package com.soleil.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.soleil.api.model.Fichaje;

@Repository
public interface FichajeRepository extends JpaRepository<Fichaje, Integer> {
	Page<Fichaje> findAll(Pageable pageable);
}