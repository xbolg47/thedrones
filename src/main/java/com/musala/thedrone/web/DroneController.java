package com.musala.thedrone.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musala.thedrone.domain.Drone;
import com.musala.thedrone.domain.Medication;
import com.musala.thedrone.domain.MedicationRepository;
import com.musala.thedrone.service.DroneServiceImpl;

@RestController
@RequestMapping("/drones")
public class DroneController {

  @Autowired
  private DroneServiceImpl droneService;

  private ObjectMapper objectMapper = new ObjectMapper();

  @PostMapping
  public ResponseEntity<String> registerDrone(@RequestBody Drone drone) {
    try {
      droneService.registerDrone(drone);
      String jsonString = objectMapper.writeValueAsString(drone);
      return ResponseEntity.ok(jsonString);
    } catch (DroneNotFoundException ex) {
      return ResponseEntity.notFound().build();
    } catch (JsonProcessingException ex) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping("/{serialNumber}/{medicationCode}")
  public ResponseEntity<String> loadDrone(@PathVariable String serialNumber, @PathVariable String medicationCode) {
    try {
      droneService.loadDrone(serialNumber, medicationCode);
      List<Medication> loadedMedications = droneService.getLoadedMedications(serialNumber);
      String jsonString = objectMapper.writeValueAsString(loadedMedications);
      String jsonOutput = "{\"serialNumber\":\"" + serialNumber + "\",\"loadedMedication\":" + jsonString + "}";
      return ResponseEntity.ok(jsonOutput);
    } catch (DroneNotFoundException ex) {
      return ResponseEntity.notFound().build();
    } catch (JsonProcessingException ex) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @RequestMapping("/available")
  public ResponseEntity<String> getAvailableDrones() {
    try {
      ResponseEntity<List<Drone>> availableDrones = droneService.getAvailableDrones();
      String jsonString = objectMapper.writeValueAsString(availableDrones);
      return ResponseEntity.ok(jsonString);
    } catch (DroneNotFoundException ex) {
      return ResponseEntity.notFound().build();
    } catch (JsonProcessingException ex) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @RequestMapping("/all")
  public ResponseEntity<String> getAllDrones() {
    try {
      ResponseEntity<List<Drone>> allDrones = droneService.getAllDrones();
      String jsonString = objectMapper.writeValueAsString(allDrones);
      return ResponseEntity.ok(jsonString);
    } catch (DroneNotFoundException ex) {
      return ResponseEntity.notFound().build();
    } catch (JsonProcessingException ex) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

  }

  @GetMapping("/{serialNumber}/medications")
  public ResponseEntity<String> getLoadedMedications(@PathVariable String serialNumber) {
    try {
      List<Medication> loadedMedications = droneService.getLoadedMedications(serialNumber);
      String jsonString = objectMapper.writeValueAsString(loadedMedications);
      String jsonOutput = "{\"serialNumber\":\"" + serialNumber + "\",\"loadedMedication\":" + jsonString + "}";
      return ResponseEntity.ok(jsonOutput);
    } catch (DroneNotFoundException ex) {
      return ResponseEntity.notFound().build();
    } catch (JsonProcessingException ex) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/{serialNumber}/battery")
  public ResponseEntity<String> getDroneBatteryLevel(@PathVariable String serialNumber) {
    try {
      int batteryLevel = droneService.getDroneBatteryLevel(serialNumber);
      String batterS = Integer.toString(batteryLevel);
      String jsonString = objectMapper.writeValueAsString(batterS);
      String jsonOutput = "{\"serialNumber\":\"" + serialNumber + "\",\"batteryLevel\":" + jsonString + "}";
      return ResponseEntity.ok(jsonOutput);
    } catch (DroneNotFoundException ex) {
      return ResponseEntity.notFound().build();
    } catch (JsonProcessingException ex) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  public class DroneNotFoundException extends RuntimeException {
    public DroneNotFoundException(String message) {
      super(message);
    }
  }

}
