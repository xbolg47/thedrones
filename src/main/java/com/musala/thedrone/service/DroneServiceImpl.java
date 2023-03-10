package com.musala.thedrone.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.musala.thedrone.domain.Drone;
import com.musala.thedrone.domain.DroneRepository;
import com.musala.thedrone.domain.DroneState;
import com.musala.thedrone.domain.Medication;
import com.musala.thedrone.domain.MedicationRepository;

@Service
public class DroneServiceImpl implements DroneService {

  @Autowired
  private DroneRepository droneRepository;

  @Autowired
  MedicationRepository medicationRepository;

  @Override
  public void registerDrone(Drone drone) {
    droneRepository.save(drone);
  }

  @Override
  public void loadDrone(String serialNumber, String medicationCode) {
    Optional<Drone> optionalDrone = droneRepository.findBySerialNumber(serialNumber);
    Optional<Medication> optionalMedication = medicationRepository.findByCode(medicationCode);

    if (optionalDrone.isPresent()) {
      if (optionalMedication.isPresent()) {
        Drone drone = optionalDrone.get();
        Medication medication = optionalMedication.get();
        double currentWeight = drone.getLoadedMedication().stream().mapToDouble(Medication::getWeight).sum();
        double newWeight = medication.getWeight();
        double weightLimit = drone.getWeightLimit();
        if (drone.getState() != DroneState.IDLE) {
          throw new IllegalStateException("Drone is not in IDLE state.");
        }
        if (drone.getBatteryCapacity() < 25) {
          throw new IllegalStateException("Drone battery level is below 25%.");
        }
        List<Medication> loadedMedications = drone.getLoadedMedication();
        if (loadedMedications.contains(medication)) {
          throw new IllegalStateException("Medication is already loaded onto the drone.");
        }
        if (currentWeight + newWeight <= weightLimit) {
          loadedMedications.add(medication);

          drone.setState(DroneState.LOADING);
          drone.setLoadedMedication(loadedMedications);
          droneRepository.save(drone);

          // update drone battery level
          int newBatteryCapacity = drone.getBatteryCapacity() - 5;
          drone.setBatteryCapacity(newBatteryCapacity);
          droneRepository.save(drone);

          drone.setState(DroneState.IDLE);
          droneRepository.save(drone);
        } else {
          throw new IllegalStateException("Drone cannot be loaded with more weight than its weight limit.");
        }
      } else {
        throw new ResourceNotFoundException("Medication with with code " + medicationCode + " not found.");
      }
    } else {
      throw new ResourceNotFoundException("Drone with serial number " + serialNumber + " not found.");
    }
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
  public ResponseEntity<List<Drone>> getAllDrones() {
    List<Drone> drones = droneRepository.findAll();
    // List<Drone> availableDrones = new ArrayList<>();

    // return drones;
    return new ResponseEntity<List<Drone>>(drones, HttpStatus.OK);
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

  @Override
  public List<Drone> findByState(DroneState state) {
    return droneRepository.findByState(state);

  }

  @Override
  public List<Drone> getDronesBySerialNumber(String serialNumber) {
    Optional<Drone> droneOptional = droneRepository.findBySerialNumber(serialNumber);
    List<Drone> droneList = droneOptional.map(Collections::singletonList).orElse(Collections.emptyList());
    return droneList;
  }

  public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
      super(message);
    }
  }
}
