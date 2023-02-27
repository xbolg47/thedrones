# theDrone 
A service via REST API that allows clients to communicate with the drones (i.e. dispatch controller).

# Requirements
 - Java 11
 - Spring Boot 2.x
 - Java IDE : VScode (or Eclipse, IntelliJ, Netbeans)
 - Database: H2 Memory(All data will be preloaded on run)
 - Postman(For testing)

# The Services include:
 - registering a drone;
 - loading a drone with medication items;
 - checking loaded medication items for a given drone;
 - checking available drones for loading;
 - check drone battery level for a given drone;

# Functional requirements
 - There is no need for UI;
 - Prevent the drone from being loaded with more weight that it can carry;
 - Prevent the drone from being in LOADING state if the battery level is below 25%;
 - Introduce a periodic task to check drones battery levels and create history/audit event log for this.

# Some assumptions for the design approach:
 - Medication can be loaded on multiple drones provided the total weight of all the medications does not exceed the weight limit of the drone
 - Preloaded 10 drones and 5 medications

# Run
 - Clone the repository into an IDE
 - Build the project using Maven and run
 - The application will startup on port 8080 {url: http://localhost:8080}
 - The is a periodic task which checks the drones battery level and save history/audit log in the audit_log table within an interval of 1 minutes

# API Endpoints testing
 - Register a new drone: POST -> http://localhost:8080/drones
  * Input JSON:
```json
{
  "serialNumber": "0000000015",
  "model": "Lightweight",
  "weightLimit": 100,
  "batteryCapacity": 38,
  "state": "IDLE"
}
```
  * Output JSON:
```json
{
  "serialNumber": "0000000015",
  "model": "Lightweight",
  "weightLimit": 100.0,
  "batteryCapacity": 38,
  "state": "IDLE",
  "loadedMedication": []
}
```
 - Load a medication onto a drone by serial number: POST -> http://localhost:8080/drones/{serialNumber}/{medicationCode}
  * OutputJSON:
```json
  {
    "serialNumber": "0000000010",
    "model": "Middleweight",
    "weightLimit": 200.0,
    "batteryCapacity": 90,
    "state": "IDLE",
    "loadedMedication": [
      {
        "name": "Med 01",
        "weight": 100.0,
        "code": "MED_001",
        "image64": "iVBORw0KGgoAAAANS..."
      }
    ]
  }
```

 - Get all loaded medications of a drone by serial number: GET -> http://localhost:8080/drones/{serialNumber}/medications
```json
  {
    "name": "Med 02",
    "weight": 90.0,
    "code": "MED_002",
    "image64": "iVBORw0KGgoAAAANSUhEUgAAA...."
  }
```

 - Get all drones that are currently available for loading: GET -> http://localhost:8080/drones/available
  * Output JSON:
```json
[
  {
    "serialNumber": "0000000004",
    "model": "Heavyweight",
    "weightLimit": 500.0,
    "batteryCapacity": 26,
    "state": "IDLE",
    "loadedMedication": []
  },
  {
    "serialNumber": "0000000005",
    "model": "Lightweight",
    "weightLimit": 100.0,
    "batteryCapacity": 50,
    "state": "IDLE",
    "loadedMedication": []
  },
  {
    "serialNumber": "0000000008",
    "model": "Heavyweight",
    "weightLimit": 500.0,
    "batteryCapacity": 35,
    "state": "IDLE",
    "loadedMedication": []
  },
  {
    "serialNumber": "0000000009",
    "model": "Lightweight",
    "weightLimit": 100.0,
    "batteryCapacity": 45,
    "state": "IDLE",
    "loadedMedication": []
  },
  {
    "serialNumber": "0000000010",
    "model": "Middleweight",
    "weightLimit": 200.0,
    "batteryCapacity": 95,
    "state": "IDLE",
    "loadedMedication": []
  }
]
```

 - Get the battery level of a drone by serial number: GET -> http://localhost:8080/drones/{serialNumber}/battery
  * Output JSON:
```json
{
  "serialNumber": "0000000010",
  "batteryLevel": "95"
}
```













