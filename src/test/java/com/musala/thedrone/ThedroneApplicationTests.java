package com.musala.thedrone;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.musala.thedrone.domain.Drone;
import com.musala.thedrone.service.DroneServiceImpl;

@SpringBootTest
class ThedroneApplicationTests {

	@Autowired
	private DroneServiceImpl droneService;

	@Test
	public void testGetAvailableDrones() {
		ResponseEntity<List<Drone>> response = droneService.getAvailableDrones();
		List<Drone> drones = response.getBody();
		assertNotNull(drones);
	}

}
