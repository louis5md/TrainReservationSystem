package com.louis.train;

import com.louis.train.model.Seat;
import com.louis.train.model.Status;
import com.louis.train.model.Train;
import com.louis.train.repository.BookingRepository;
import com.louis.train.repository.SeatRepository;
import com.louis.train.repository.TrainRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootApplication
public class TrainApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(TrainRepository trainRepository, SeatRepository seatRepository) {
		return args -> {
			Train train = new Train();
			train.setSrc("BDG");
			train.setDest("MLG");
			LocalDate date = LocalDate.parse("2023-12-05");
			train.setDate(Date.valueOf(date));
			LocalTime time = LocalTime.parse("15:30");
			train.setTime(Time.valueOf(time));

			trainRepository.save(train);

			Seat seat1 = new Seat();
			seat1.setTrainId(train);
			seat1.setSeatNumber("A1");
			seat1.setStatus(Status.AVAILABLE);
			seatRepository.save(seat1);

			Seat seat2 = new Seat();
			seat2.setTrainId(train);
			seat2.setSeatNumber("A2");
			seat2.setStatus(Status.AVAILABLE);
			seatRepository.save(seat2);

			Seat seat3 = new Seat();
			seat3.setTrainId(train);
			seat3.setSeatNumber("A3");
			seat3.setStatus(Status.AVAILABLE);
			seatRepository.save(seat3);

			Seat seat4 = new Seat();
			seat4.setTrainId(train);
			seat4.setSeatNumber("B1");
			seat4.setStatus(Status.HOLD);
			seatRepository.save(seat4);
		};
	}
}
