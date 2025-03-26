package com.soleil.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.soleil.api.model.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
	Page<Paciente> findAll(Pageable pageable);
}