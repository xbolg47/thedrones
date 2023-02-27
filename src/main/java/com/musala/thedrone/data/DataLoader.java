package com.musala.thedrone.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.musala.thedrone.domain.Drone;
import com.musala.thedrone.domain.DroneModel;
import com.musala.thedrone.domain.DroneRepository;
import com.musala.thedrone.domain.DroneState;
import com.musala.thedrone.domain.Medication;
import com.musala.thedrone.domain.MedicationRepository;

@Component
public class DataLoader implements CommandLineRunner {
  private final DroneRepository droneRepository;
  private final MedicationRepository medicationRepository;

  public DataLoader(DroneRepository droneRepository, MedicationRepository medicationRepository) {
    this.droneRepository = droneRepository;
    this.medicationRepository = medicationRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    // Preload the database with some demo drones
    Drone drone1 = new Drone("0000000001", DroneModel.Lightweight, 100, 24, DroneState.LOADING);
    Drone drone2 = new Drone("0000000002", DroneModel.Middleweight, 200, 20, DroneState.LOADING);
    Drone drone3 = new Drone("0000000003", DroneModel.Cruiserweight, 350, 65, DroneState.DELIVERING);
    Drone drone4 = new Drone("0000000004", DroneModel.Heavyweight, 500, 26, DroneState.IDLE);
    Drone drone5 = new Drone("0000000005", DroneModel.Lightweight, 100, 50, DroneState.IDLE);
    Drone drone6 = new Drone("0000000006", DroneModel.Middleweight, 200, 80, DroneState.LOADING);
    Drone drone7 = new Drone("0000000007", DroneModel.Cruiserweight, 350, 45, DroneState.RETURNING);
    Drone drone8 = new Drone("0000000008", DroneModel.Heavyweight, 500, 35, DroneState.IDLE);
    Drone drone9 = new Drone("0000000009", DroneModel.Lightweight, 100, 45, DroneState.IDLE);
    Drone drone10 = new Drone("0000000010", DroneModel.Middleweight, 200, 95, DroneState.IDLE);

    // Save the drones to the databasex
    droneRepository
        .saveAll(Arrays.asList(drone1, drone2, drone3, drone4, drone5, drone6, drone7, drone8, drone9, drone10));
    System.out.println("Drone data Successfully Loaded");

    // Preload the database with some demo medications
    File image1 = new ClassPathResource("static/images/image1.png").getFile();
    Medication medication1 = new Medication("Med 01", 100, "MED_001", encodeImageToBase64String(image1));

    File image2 = new ClassPathResource("static/images/image2.png").getFile();
    Medication medication2 = new Medication("Med 02", 90, "MED_002", encodeImageToBase64String(image2));

    File image3 = new ClassPathResource("static/images/image3.jpg").getFile();
    Medication medication3 = new Medication("Med 11", 60, "MED_011", encodeImageToBase64String(image3));

    File image4 = new ClassPathResource("static/images/image4.png").getFile();
    Medication medication4 = new Medication("Med 05", 70, "MED_005", encodeImageToBase64String(image4));

    File image5 = new ClassPathResource("static/images/image5.jpg").getFile();
    Medication medication5 = new Medication("Med 03", 40, "MED_003", encodeImageToBase64String(image5));

    // Save the medications to the database -- ,
    medicationRepository.saveAll(Arrays.asList(medication1, medication2, medication3, medication4, medication5));
    System.out.println("Medications data Successfully Loaded");

    // load medication onto drone
    List<Medication> loadedMed1 = new ArrayList<>();
    loadedMed1.add(medication2);

    List<Medication> loadedMed2 = new ArrayList<>();
    loadedMed2.add(medication3);

    drone1.setLoadedMedication(loadedMed1);
    drone2.setLoadedMedication(loadedMed2);
    droneRepository.save(drone1);
    droneRepository.save(drone2);
    System.out.println("Medications loaded to drone Successfully");

  }

  private static String encodeImageToBase64String(File file) throws IOException {
    FileInputStream imageInFile = new FileInputStream(file);
    byte imageData[] = new byte[(int) file.length()];
    imageInFile.read(imageData);

    return Base64.getEncoder().encodeToString(imageData);

  }

}
