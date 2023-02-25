package com.musala.thedrone.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

@Entity
@Table(name = "drones")
public class Drone {

  @Id
  @Column(name = "serial_number", length = 100)
  private String serialNumber;

  @Enumerated(EnumType.STRING)
  @Column(name = "model")
  private DroneModel model;

  @Column(name = "weight_limit")
  @Max(value = 500, message = "Weight limit cannot exceed 500 grams")
  private double weightLimit;

  @Column(name = "battery_capacity")
  private int batteryCapacity;

  @Enumerated(EnumType.STRING)
  @Column(name = "state")
  private DroneState state;

  public Drone() {

  }

  public Drone(String serialNumber, DroneModel model,
      @Max(value = 500, message = "Weight limit cannot exceed 500 grams") double weightLimit, int batteryCapacity,
      DroneState state) {
    this.serialNumber = serialNumber;
    this.model = model;
    this.weightLimit = weightLimit;
    this.batteryCapacity = batteryCapacity;
    this.state = state;
  }

  // getters and setters

  public String getSerialNumber() {
    return serialNumber;
  }

  public DroneModel getModel() {
    return model;
  }

  public double getWeightLimit() {
    return weightLimit;
  }

  public int getBatteryCapacity() {
    return batteryCapacity;
  }

  public DroneState getState() {
    return state;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public void setModel(DroneModel model) {
    this.model = model;
  }

  public void setWeightLimit(double weightLimit) {
    this.weightLimit = weightLimit;
  }

  public void setBatteryCapacity(int batteryCapacity) {
    this.batteryCapacity = batteryCapacity;
  }

  public void setState(DroneState state) {
    this.state = state;
  }

}
