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

import com.musala.thedrone.domain.Drone;
import com.musala.thedrone.domain.Medication;
import com.musala.thedrone.service.DroneServiceImpl;

@RestController
@RequestMapping("/drones")
public class DroneController {

  @Autowired
  private DroneServiceImpl droneService;

  @PostMapping
  public ResponseEntity<Void> registerDrone(@RequestBody Drone drone) {
    droneService.registerDrone(drone);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/{serialNumber}/{medicationCode}")
  public ResponseEntity<Void> loadDrone(@PathVariable String serialNumber, @PathVariable String medicationCode) {
    droneService.loadDrone(serialNumber, medicationCode);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @RequestMapping("/available")
  public ResponseEntity<List<Drone>> getAvailableDrones() {
    ResponseEntity<List<Drone>> availableDrones = droneService.getAvailableDrones();
    return availableDrones;
  }

  @GetMapping("/{serialNumber}/medications")
  public ResponseEntity<List<Medication>> getLoadedMedications(@PathVariable String serialNumber) {
    List<Medication> loadedMedications = droneService.getLoadedMedications(serialNumber);
    return ResponseEntity.ok(loadedMedications);
  }

  @GetMapping("/{serialNumber}/battery")
  public ResponseEntity<Integer> getDroneBatteryLevel(@PathVariable String serialNumber) {
    int batteryLevel = droneService.getDroneBatteryLevel(serialNumber);
    return ResponseEntity.ok(batteryLevel);
  }

}
