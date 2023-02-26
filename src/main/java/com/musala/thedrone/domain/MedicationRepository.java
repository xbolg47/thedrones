package com.musala.thedrone.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
  // Find medication by code
  Optional<Medication> findByCode(String code);
}
