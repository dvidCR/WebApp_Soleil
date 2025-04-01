package com.soleil.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.soleil.api.model.Tratamiento;

public interface TratamientoRepository extends JpaRepository<Tratamiento, Integer> {
	Page<Tratamiento> findAll(Pageable pageable);
}
