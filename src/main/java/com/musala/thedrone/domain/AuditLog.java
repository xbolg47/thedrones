package com.musala.thedrone.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "audit_log")
public class AuditLog {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "timestamp")
  private Date timestamp;

  @Column(name = "drone_serial_number")
  private String droneSerialNumber;

  @Column(name = "message")
  private String message;

  public AuditLog() {

  }

  public AuditLog(Date timestamp, String droneSerialNumber, String message) {
    this.timestamp = timestamp;
    this.droneSerialNumber = droneSerialNumber;
    this.message = message;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getDroneSerialNumber() {
    return droneSerialNumber;
  }

  public String getMessage() {
    return message;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public void setDroneSerialNumber(String droneSerialNumber) {
    this.droneSerialNumber = droneSerialNumber;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
