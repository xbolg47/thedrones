package com.musala.thedrone.domain;

import java.sql.Clob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Pattern;

@Entity
public class Medication {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @Pattern(regexp = "^[a-zA-Z0-9_-]*$", message = "Name should allowed only letters, numbers, ‘-‘, ‘_’")
  private String name;

  @Column(nullable = false)
  private double weight;

  @Column(nullable = false)
  @Pattern(regexp = "^[A-Z_0-9]*$", message = "Code should allowed only upper case letters, underscore and numbers")
  private String code;

  @Column(nullable = false)
  @Lob
  private String image64;

  public Medication() {

  }

  public Medication(String name, double weight, String code, String image64) {
    this.name = name;
    this.weight = weight;
    this.code = code;
    this.image64 = image64;
  }

  // getters and setters
  public String getName() {
    return name;
  }

  public double getWeight() {
    return weight;
  }

  public String getCode() {
    return code;
  }

  public String getImage64() {
    return image64;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setImage64(String image64) {
    this.image64 = image64;
  }

}
