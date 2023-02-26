package com.musala.thedrone.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.musala.thedrone.domain.Drone;
import com.musala.thedrone.domain.DroneState;
import com.musala.thedrone.domain.Medication;

interface DroneService {

  void registerDrone(Drone drone);

  void loadDrone(String serialNumber, String medicationCode);

  List<Medication> getLoadedMedications(String serialNumber);

  ResponseEntity<List<Drone>> getAvailableDrones();

  List<Drone> getDronesBySerialNumber(String serialNumber);

  List<Drone> getAllDrones();

  int getDroneBatteryLevel(String serialNumber);

  List<Drone> findByState(DroneState state);

}
