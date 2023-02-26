package com.musala.thedrone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.musala.thedrone.domain.AuditLogRepository;
import com.musala.thedrone.service.BatteryCheckService;
import com.musala.thedrone.service.DroneServiceImpl;

@SpringBootApplication
@EnableScheduling // enable scheduling support
@ComponentScan(basePackages = { "com.musala.thedrone", "com.musala.thedrone.data" })
public class ThedroneApplication {

	private final DroneServiceImpl droneServiceImpl;
	private final AuditLogRepository auditLogRepository;

	public ThedroneApplication(DroneServiceImpl droneServiceImpl, AuditLogRepository auditLogRepository) {
		this.droneServiceImpl = droneServiceImpl;
		this.auditLogRepository = auditLogRepository;
	}

	@Bean
	public BatteryCheckService batteryCheckService() {
		return new BatteryCheckService(droneServiceImpl, auditLogRepository);
	}

	public static void main(String[] args) {
		SpringApplication.run(ThedroneApplication.class, args);
	}

}
