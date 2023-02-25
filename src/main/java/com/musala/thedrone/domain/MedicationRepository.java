package com.musala.thedrone.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
  // Find medication by code
  Medication findByCode(String code);
}
