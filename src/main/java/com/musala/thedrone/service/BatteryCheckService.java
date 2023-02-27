package com.musala.thedrone.service;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.musala.thedrone.domain.AuditLog;
import com.musala.thedrone.domain.AuditLogRepository;
import com.musala.thedrone.domain.Drone;

@Service
public class BatteryCheckService {

  private final DroneServiceImpl droneServiceImpl;
  private final AuditLogRepository auditLogRepository;

  public BatteryCheckService(DroneServiceImpl droneServiceImpl, AuditLogRepository auditLogRepository) {
    this.droneServiceImpl = droneServiceImpl;
    this.auditLogRepository = auditLogRepository;
  }

  @PostConstruct
  @Scheduled(fixedRate = 100000) // One minuites
  public void checkBatteryCapacity() {
    // Run the battery check logic here
    ResponseEntity<List<Drone>> drones = droneServiceImpl.getAllDrones();
    List<Drone> droneList = drones.getBody();
    for (Drone drone : droneList) {
      if (drone.getBatteryCapacity() < 25) {
        String message = "Drone with serial number " + drone.getSerialNumber() + " has battery level below 25%";
        saveAuditLogEntry(drone.getSerialNumber(), message);
      }
    }
  }

  private void saveAuditLogEntry(String droneSerialNumber, String message) {
    AuditLog auditLog = new AuditLog();
    auditLog.setTimestamp(new Date());
    auditLog.setDroneSerialNumber(droneSerialNumber);
    auditLog.setMessage(message);
    auditLogRepository.save(auditLog);
  }
}
