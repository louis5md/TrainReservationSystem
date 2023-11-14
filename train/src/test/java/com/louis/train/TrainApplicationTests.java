package com.louis.train;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.louis.train.dto.TrainBookRequest;
import com.louis.train.repository.BookingRepository;
import com.louis.train.repository.SeatRepository;
import com.louis.train.repository.TrainRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;


//can not run yet
@SpringBootTest
@Testcontainers
class TrainApplicationTests {


	@Autowired
	private ObjectMapper objectMapper;
	static final MySQLContainer MY_SQL_CONTAINER;

	static {
		MY_SQL_CONTAINER = new MySQLContainer("mysql:latest");
		MY_SQL_CONTAINER.start();
	}
	@DynamicPropertySource
	static void configureTestProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", () -> MY_SQL_CONTAINER.getJdbcUrl());
		registry.add("spring.datasource.username", () -> MY_SQL_CONTAINER.getUsername());
		registry.add("spring.datasource.password", () -> MY_SQL_CONTAINER.getPassword());
		registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
	}

	@Autowired
	private TrainRepository trainRepository;

	@Autowired
	private SeatRepository seatRepository;

	@Autowired
	private BookingRepository bookingRepository;

	private final TestRestTemplate restTemplate = new TestRestTemplate();
	private AtomicInteger successfulBookings = new AtomicInteger(0);

	@Test
	public void testConcurrentSeatBooking() throws JsonProcessingException {
		// Replace with your endpoint and API call for seat booking
		String bookingEndpoint = "http://localhost:" + 8080 + "/api/train/book";

		TrainBookRequest trainBookRequest = getTrainBookRequest();
		String trainRequestString = objectMapper.writeValueAsString(trainBookRequest);
		// Simulate multiple concurrent booking requests for the same seat
		Runnable concurrentBooking = () -> {
			ResponseEntity<String> response = restTemplate.postForEntity(bookingEndpoint, trainRequestString, String.class);
			if (response.getStatusCode().equals(HttpStatus.CREATED)) {
				successfulBookings.incrementAndGet();
			}else{
			}
		};

		// Simulate multiple concurrent requests (adjust the number as needed)
		int numberOfConcurrentRequests = 5;
		Thread[] threads = new Thread[numberOfConcurrentRequests];

		for (int i = 0; i < numberOfConcurrentRequests; i++) {
			threads[i] = new Thread(concurrentBooking);
			threads[i].start();
		}

		// Wait for all threads to complete
		for (int i = 0; i < numberOfConcurrentRequests; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		assertEquals(1, successfulBookings.get());
	}

	private TrainBookRequest getTrainBookRequest() {
		return new TrainBookRequest(1L, 2L,"louis");
	}

}
