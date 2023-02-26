package com.musala.thedrone.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.musala.thedrone.domain.Drone;
import com.musala.thedrone.domain.DroneRepository;
import com.musala.thedrone.domain.DroneState;
import com.musala.thedrone.domain.Medication;

@Service
public class DroneServiceImpl implements DroneService {

  @Autowired
  private DroneRepository droneRepository;

  @Override
  public void registerDrone(Drone drone) {
    droneRepository.save(drone);
  }

  @Override
  public void loadDrone(String serialNumber, Medication medication) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'loadDrone'");
  }

  @Override
  public List<Medication> getLoadedMedications(String serialNumber) {
    Optional<Drone> optionalDrone = droneRepository.findBySerialNumber(serialNumber);
    if (optionalDrone.isPresent()) {
      Drone drone = optionalDrone.get();
      return drone.getLoadedMedication();
    } else {
      throw new ResourceNotFoundException("Drone not found with serial number: " + serialNumber);
    }
  }

  @Override
  public ResponseEntity<List<Drone>> getAvailableDrones() {
    List<Drone> drones = droneRepository.findAll();
    List<Drone> availableDrones = new ArrayList<>();

    for (Drone drone : drones) {
      if (drone.getState() == DroneState.IDLE && drone.getBatteryCapacity() > 25) {
        availableDrones.add(drone);
      }
    }
    return ResponseEntity.ok(availableDrones);
  }

  @Override
  public int getDroneBatteryLevel(String serialNumber) {
    Optional<Drone> drone = droneRepository.findBySerialNumber(serialNumber);
    if (drone.isPresent()) {
      return drone.get().getBatteryCapacity();
    } else {
      throw new ResourceNotFoundException("Drone not found with serial number: " + serialNumber);
    }
  }

  public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
      super(message);
    }
  }
}
