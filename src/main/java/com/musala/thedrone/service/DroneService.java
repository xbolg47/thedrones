package com.musala.thedrone.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.musala.thedrone.domain.Drone;
import com.musala.thedrone.domain.Medication;

interface DroneService {

  void registerDrone(Drone drone);

  void loadDrone(String serialNumber, String medicationCode);

  List<Medication> getLoadedMedications(String serialNumber);

  ResponseEntity<List<Drone>> getAvailableDrones();

  int getDroneBatteryLevel(String serialNumber);

}
