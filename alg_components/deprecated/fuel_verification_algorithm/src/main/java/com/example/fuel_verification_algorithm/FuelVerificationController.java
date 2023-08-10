package com.example.fuel_verification_algorithm;

import java.io.*;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class FuelVerificationController {

	private static final String template = "Im the FuelVerificator";
	private static String ID = "1";
	private int counter = 0;
	MultiValueMap<String, String> configuration;

	@GetMapping("/")
	public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return template;
	}

	@GetMapping("/status")
	public String get_status() {
		return "ready";
	}

	public void update_status(Status status) {

		final String url = "http://localhost:8080/ui_status";
		RestTemplate restTemplate = new RestTemplate();
		// restTemplate.postForObject(url, "test", String.class);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("id", ID);
		map.add("result", status.toString());

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		} catch(Exception e) {
			System.out.println("Status update failed. UI-Service unreachable.");
		}
	}

	@PostMapping("/fuel/optional_equipment")
	public String executeAlgorithm(
			@RequestParam("configItem1") String configItem1,
			@RequestParam("configItem2") String configItem2,
			@RequestParam("configItem3") String configItem3,
			@RequestParam("configItem4") String configItem4) throws InterruptedException {

		this.configuration = new LinkedMultiValueMap<>();
		this.configuration.add("configItem1", configItem1);
		this.configuration.add("configItem2", configItem2);
		this.configuration.add("configItem3", configItem3);
		this.configuration.add("configItem4", configItem4);
		
		update_status(Status.RUNNING);
		System.out.println("Started working..");

		counter += 1;
		if (counter % 3 == 0) {
			TimeUnit.SECONDS.sleep(2);
			invoke_next_algorithm();
			update_status(Status.FAILED);
			return "Validation error.";
		} else {
			TimeUnit.SECONDS.sleep(2);
			invoke_next_algorithm();
			update_status(Status.SUCCESS);
			return "Fuel equipment validated.";
		}
	}

	@GetMapping("/test/anal")
	public void invoke_next_algorithm() {
		/*
		 * Calls an external endpoint and returns its response.
		 */
		final String url = "http://localhost:8090/configuration/test";
		RestTemplate restTemplate = new RestTemplate();
		// restTemplate.postForObject(url, "test", String.class);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		this.configuration = new LinkedMultiValueMap<>();
		this.configuration.add("configItem1", "test");
		this.configuration.add("configItem2", "test");
		this.configuration.add("configItem3", "test");
		this.configuration.add("configItem4", "test");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(this.configuration, headers);
		try {
			restTemplate.postForEntity(url, request, String.class);
		} catch(Exception e) {
			System.out.println("Invokation failed. Service unreachable.");
		}
	}

	@GetMapping("/fuel/equipment")
	public void test_analyzer_endpoint() {
		/*
		 * Calls an external endpoint and returns its response.
		 */
		final String url = "http://localhost:8082/gearbox/optional_equipment";
		RestTemplate restTemplate = new RestTemplate();
		// restTemplate.postForObject(url, "test", String.class);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(this.configuration, headers);
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		} catch(Exception e) {
			System.out.println("Invokation failed. Service unreachable.");
		}
	}
}