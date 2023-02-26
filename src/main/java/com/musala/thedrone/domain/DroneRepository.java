package com.musala.thedrone.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DroneRepository extends JpaRepository<Drone, Long> {

  // Find drones by its serail number
  Optional<Drone> findBySerialNumber(String serialNumber);

  // Find drones by its state
  List<Drone> findByState(DroneState state);

  // Find all drones with a given state and battery capacity greater than a
  // specific value
  List<Drone> findByStateAndBatteryCapacityGreaterThan(String state, int batteryCapacity);

  // Find battrey level for a given drone
  @Query("SELECT d.batteryCapacity FROM Drone d WHERE d.serialNumber = :serialNumber")
  Optional<Integer> findBatteryCapacityBySerialNumber(@Param("serialNumber") String serialNumber);

  // Find weight for a given drone
  @Query("SELECT d.weightLimit FROM Drone d WHERE d.serialNumber = :serialNumber")
  Optional<Integer> findWeightLimitBySerialNumber(@Param("serialNumber") String serialNumber);

}
